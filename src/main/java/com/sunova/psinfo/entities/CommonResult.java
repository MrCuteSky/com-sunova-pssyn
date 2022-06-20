package com.sunova.psinfo.entities;

public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult() {}

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String toString(){
        if (data == null){
            return "代码:" + code + "；" + message;
        }else{
            return "代码:" + code + "；" + message + "；" + data.toString();
        }
    }
}
