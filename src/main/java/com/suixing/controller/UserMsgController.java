package com.suixing.controller;

import com.suixing.commons.ServerResponse;
import com.suixing.service.IUserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMsgController {


    @Autowired
    private IUserMsgService userMsgService;
    @GetMapping("getUserMsg")
    public ServerResponse getUserMsg(int userId){
        System.out.println(userMsgService.getUserMsg(userId).getData());
       return  userMsgService.getUserMsg(userId);
    }


    @GetMapping("msgUpdate")
    public ServerResponse msgUpdate(Integer userId,Integer msgId){
        return  userMsgService.msgUpdate(userId,msgId);
    }


}
