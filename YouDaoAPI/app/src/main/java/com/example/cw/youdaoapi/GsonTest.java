package com.example.cw.youdaoapi;

import java.util.List;

/**
 * Created by cw on 2016/9/21.
 */
public class GsonTest {

    /**
     * phonetic : hào,hǎo
     * explains : ["all right","well","good","fine","ok"]
     */

    private BasicBean basic;
    /**
     * translation : ["good"]
     * basic : {"phonetic":"hào,hǎo","explains":["all right","well","good","fine","ok"]}
     * query : 好
     * errorCode : 0
     * web : [{"value":["Good","Well","fine"],"key":"好"},{"value":["very good","Fine","very well"],"key":"很好"},{"value":["Ready","Be prepared","to be ready for"],"key":"准备好"}]
     */

    private String query;
    private int errorCode;
    private List<String> translation;
    /**
     * value : ["Good","Well","fine"]
     * key : 好
     */

    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        private String phonetic;
        private List<String> explains;

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
