package cn.dozyx.zerofate.java.bean;

import java.util.Map;

public class GsonBean {

    /**
     * data : [{"value":"传统pos","key":"02"},{"value":"mpos","key":"03"},{"value":"智能pos","key":"04"},{"value":"扫码盒子",
     * "key":"06"},{"value":"云闪付终端","key":"07"}]
     * error_code : 0
     */

    private String error_code;
    private Map<String, String> data;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public Map<String, String> getData() {
        return data;
    }
}
