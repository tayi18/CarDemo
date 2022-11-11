package com.suixing.commons;


import lombok.Data;

@Data
public class ServerResponse {
    private int resultcode;
    private String reason;
    private Object data;
    public ServerResponse(){}
    public ServerResponse(int resultcode, String reason, Object data) {
        this.resultcode = resultcode;
        this.reason = reason;
        this.data = data;
    }
    public  static ServerResponse success(String reason ,Object data){
        return new ServerResponse(200,reason,data);
    }
    public  static ServerResponse fail(String reason ,Object data){
        return new ServerResponse(201,reason,data);
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "resultcode=" + resultcode +
                ", reason='" + reason + '\'' +
                ", data=" + data +
                '}';
    }
}
