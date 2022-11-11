package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.entity.Comments;
import com.suixing.entity.Order;
import com.suixing.service.ICommentsService;
import com.suixing.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smith
 * @since 2022-10-31
 */
@RestController
//@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private ICommentsService commentsService;



    @PostMapping("/saveComments")
    public ServerResponse getComments(Comments comments,Long ordNumber){
        System.out.println(ordNumber);
        System.out.println(comments);
        Order order = commentsService.getOrderId(ordNumber);

        ServerResponse commentResponse = commentsService.saveComments(comments,order);
        System.out.println("保存的评论为："+commentResponse);
        if (commentResponse.getResultcode() == 200){
            return ServerResponse.success("评论成功",commentResponse);
        }else {
            return ServerResponse.fail("评论失败",null);
        }

    }
}
