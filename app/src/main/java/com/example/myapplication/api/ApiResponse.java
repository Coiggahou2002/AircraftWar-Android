package com.example.myapplication.api;


/**
 * 服务端响应返回体  实体类
 * @author coiggahou
 */
public class ApiResponse {
    private String msg;
    private Integer code;
    private Object data;

    public ApiResponse(String msg, Integer code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
