package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.service.ICollectService;
import com.suixing.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Controller
@RequestMapping("/sx-collect")
public class CollectController {
    @Autowired
    private ICollectService collectService;
    //查询所有收藏的商品
    @GetMapping("collectAll")
    @ResponseBody
    private ServerResponse getAllCollectByUserId(HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();

        return collectService.getAllByUserId(userId);
    }

    //取消收藏
    @PostMapping("collectEsc")
    @ResponseBody
    private ServerResponse updateCollect(Integer collectId){

        return ServerResponse.success("ok",collectService.updateCollect(collectId));
    }
    //添加收藏
    @PostMapping("collectSave")
    @ResponseBody
    private ServerResponse saveCollect(Integer carId,HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();

        return collectService.saveCollect(carId,userId);
    }

    //查询当前车辆是否收藏
    @GetMapping("collect")
    @ResponseBody
    private ServerResponse getCollect(Integer carId,HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();

        return collectService.getCollect(carId,userId);
    }



}
