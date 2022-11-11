package com.suixing.controller;

import com.suixing.websocket.WebSocketProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    /*
    * 注入WebSocketProcess
    * */
    @Autowired
    private WebSocketProcess webSocketProcess;

    @PostMapping(value = "sendMsgById")
    public void sendMsgToClientById(@RequestParam long userId,@RequestParam String text){
        try {
            webSocketProcess.sendMessage(userId,text,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping(value = "sendMsgAll")
    public void sendMsgAllById(@RequestParam String text){
        try {
            webSocketProcess.sendAllMessage(text);
            System.out.println("服务器消息发送成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
