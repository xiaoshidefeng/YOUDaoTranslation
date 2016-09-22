package com.example.cw.youdaoapi;

import android.annotation.TargetApi;
import android.content.Entity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    //要翻译的文本
    private String transContent;

    //api连接
    private String apiUrl = "http://fanyi.youdao.com/openapi.do?keyfrom=translatessss&key=660709134&type=data&doctype=json&version=1.1&q=";

    //输入框
    private EditText editText;

    //翻译按钮
    private Button button;

    //文本显示
    private TextView textView;

    //最后显示的文本
    private String tvMsg=null;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what==0){
                String responses =(String) msg.obj;
                //显示结果
                textView.setText(responses);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();

        //点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.id_transButton){
                    sendHttpURLConnection();

                }
            }
        });

    }

    private void sendHttpURLConnection() {
        //开启子线程访问网络
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                HttpURLConnection connection = null;
                transContent=editText.getText().toString();
                try {
                    URL url = new URL(apiUrl+ URLEncoder.encode(transContent,"utf8"));
                    //URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    //获取输入流
                    InputStream in = connection.getInputStream();

                    //对获取的流进行读取
//                    BufferedReader replyReader = new BufferedReader(
//                            new InputStreamReader(connection.getInputStream(), "utf-8"));//约定输入流的编码
                   // reply = replyReader.readLine();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line=null;
                    while ((line=reader.readLine())!=null){
                        response.append(line);

                    }
                    line=response.toString();

                    //response.substring(response.indexOf("{"),response.lastIndexOf("}")+1);
                    //JSONTokener jsonTokener = new JSONTokener(line);
                   // JSONObject transJSON=(JSONObject)jsonTokener.nextValue();
                   // JSONObject transJSON=new JSONObject(response.toString());
                   // Log.d("Test",response.toString());
                    JSONObject transJSON = new JSONObject(response.toString());


                        String errorCode = transJSON.getString("errorCode");
                        if(errorCode.equals("0")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"正确进入",Toast.LENGTH_SHORT).show();
                                }
                            });
                            //当返回的是有效值
                            String query = transJSON.getString("query");
                            JSONArray translation = transJSON.getJSONArray("translation");
                            JSONObject basic = transJSON.getJSONObject("basic");
                            JSONArray web =transJSON.getJSONArray("web");

                            String phonetic = basic.getString("phonetic");
//                            String ukphonetic = basic.getString("uk-phonetic");
//                            String usphonetic = basic.getString("us-phonetic");
                            JSONArray explains = basic.getJSONArray("explains");
                            tvMsg="原文："+query;
                            tvMsg+="\n翻译结果：";
//                            tvMsg+="\n发音："+phonetic;
////                                    "\n英式发音"+ukphonetic+
////                                    "\n美式发音"+usphonetic;
                            String explainStr="\n\n释意：";
                            for(int j = 1,s=0;s<explains.length();s++,j++){
                                explainStr+="\n"+j+". "+explains.getString(s);
                            }
                            tvMsg+=explainStr;
                        }   //下面是各种错误的返回结果
                        else if(errorCode.equals(20)){
                            Toast.makeText(MainActivity.this,"要翻译的文本过长",Toast.LENGTH_SHORT).show();
                        }else if(errorCode.equals(30)){
                            Toast.makeText(MainActivity.this,"无法进行有效的翻译",Toast.LENGTH_SHORT).show();
                        }else if(errorCode.equals(40)){
                            Toast.makeText(MainActivity.this,"不支持的语言类型",Toast.LENGTH_SHORT).show();
                        }else if(errorCode.equals(50)){
                            Toast.makeText(MainActivity.this,"无效的key",Toast.LENGTH_SHORT).show();
                        }else if(errorCode.equals(60)){
                            Toast.makeText(MainActivity.this,"无词典结果",Toast.LENGTH_SHORT).show();
                        }


                        Message message = new Message();
                        message.what = 0;

                        message.obj=tvMsg.toString();
                        handler.sendMessage(message);


                }   catch (Exception e) {
                    Log.e("errss", e.getMessage());

                }
            }
        }).start();
    }



    //初始化控件
    private void initView() {
        editText=(EditText)findViewById(R.id.id_EditText);
        button=(Button)findViewById(R.id.id_transButton);
        textView=(TextView)findViewById(R.id.id_TextView);
    }
}
