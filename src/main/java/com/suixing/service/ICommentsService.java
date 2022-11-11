package com.suixing.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Comments;
import com.suixing.entity.Order;
import com.suixing.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smith
 * @since 2022-10-31
 */
public interface ICommentsService extends IService<Comments> {
    // 根据汽车id  分页查询评论 用户信息 和 评论信息 和 回复者信息及回复的内容
     ServerResponse getCommentsByCarId(Integer carId);

     ServerResponse getCommentsByCarForPage(Integer pageNum);
     public User getUserByCommId(Integer carId);
    //点赞数量的获取
//    public ServerResponse Up

    public ServerResponse saveComments(Comments comments,Order order);
    public List<Map<String,Object>> getCommentReplyByCarId(Integer carId,Integer userId);

    public Order getOrderId(Long ordNumber);
}
