package com.dyuanit.atm.springbootatm.vo;

public class AjaxVO {
    private boolean success;
    private Object data;
    private int code = 1000;
    private String message;

    private AjaxVO() {

    }

    private AjaxVO(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static AjaxVO success() {
        return new AjaxVO(true, null, null);
    }

    public static AjaxVO success(Object data) {
        return new AjaxVO(true, null, data);
    }

    public static AjaxVO failed(String message) {
        return new AjaxVO(false, message, null);
    }


    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
