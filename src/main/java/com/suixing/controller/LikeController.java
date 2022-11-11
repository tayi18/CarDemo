package com.suixing.controller;

import com.suixing.service.impl.LikeService;
import com.suixing.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;


    @GetMapping("/getLikeStatus")
    @ResponseBody
    public Map<String,Object> getLikeStatus(int carId, HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();
        System.out.println(userId);

        int status = likeService.getLikeStatus(userId,carId);

        Long likeCount = likeService.likeCount(carId);
        Map<String,Object> map = new HashMap<>();
        map.put("likeStatus",status);
        map.put("likeCount",likeCount);

        return map;
    }

    @PostMapping("/like")
    @ResponseBody
    public Long like(Integer commId,HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();
        //在likeKey对应的集合中加入当前用户的id
        long likeCount = likeService.like(userId,commId);

        return likeCount;
    }

    @PostMapping("/dislike")
    @ResponseBody
    public long disLike(Integer commId,HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();
        long likeCount = likeService.disLike(userId,commId);
        if (likeCount <= 0){
            likeCount = 0;
        }
        return likeCount;
    }




}
