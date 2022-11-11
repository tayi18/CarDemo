package com.suixing.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Comments;
import com.suixing.entity.Order;
import com.suixing.entity.Reply;
import com.suixing.entity.User;
import com.suixing.mapper.CommentsMapper;
import com.suixing.mapper.OrderMapper;
import com.suixing.mapper.ReplyMapper;
import com.suixing.mapper.UserMapper;
import com.suixing.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smith
 * @since 2022-10-31
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LikeService likeService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ServerResponse getCommentsByCarId(Integer carId) {
        QueryWrapper<Comments> queryW = new QueryWrapper<>();
        queryW.eq("car_id",carId);
        List<Comments> list = commentsMapper.selectList(queryW);

        System.out.println("service层的评论"+list);
        if (list!= null)
            return ServerResponse.success("查询评论成功",list);
        return ServerResponse.fail("查询评论失败",null);

    }

    @Override
    public ServerResponse getCommentsByCarForPage(Integer pageNum) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        System.out.println("当前查询的页数"+pageNum);
        Page<Comments> commentsPage = new Page<>(pageNum,5);
        Page<Comments> pageInfo = commentsMapper.selectPage(commentsPage,queryWrapper);
        System.out.println(pageInfo.getRecords());
        if (pageInfo.getRecords() !=null){
            return ServerResponse.success("查询成功",pageInfo);
        }else {
            return ServerResponse.fail("查询失败",null);
        }
    }

    @Override
    public User getUserByCommId(Integer carId) {
        Comments comments = commentsMapper.selectById(carId);
        Integer userId = comments.getUserId();
        return userMapper.selectById(userId);
    }

    @Override
    public ServerResponse saveComments(Comments comments,Order order) {
        System.out.println(order);

        comments.setCommTime(LocalDateTime.now());
        comments.setCarId(order.getCarId());
        comments.setOrderId(order.getOrdId());
        int rows = commentsMapper.insert(comments);
        if (rows > 0){
            return ServerResponse.success("保存评论成功",comments);
        }else {
            return ServerResponse.fail("保存评论失败",null);
        }
    }

    @Override
    public List<Map<String,Object>> getCommentReplyByCarId(Integer carId,Integer userId) {
        List<Map<String,Object>> list = new ArrayList<>();
        QueryWrapper<Comments> commentsQueryWrapper= new QueryWrapper<>();
        commentsQueryWrapper.eq("car_id",carId);
        List<Comments> comments = commentsMapper.selectList(commentsQueryWrapper);


        for (Comments comment:comments){
            Map<String,Object> map = new HashMap<>();
            User commentUser = userMapper.selectById(comment.getUserId());

            //判断点赞状态
            int commentStatus = likeService.getLikeStatus(userId,comment.getCommId());
            Long commentLikeCount = likeService.likeCount(comment.getCommId());




            QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
            replyQueryWrapper.eq("comm_id",comment.getCommId());
            List<Map<String,Object>> replyList = new ArrayList<>();
            List<Reply> replies = replyMapper.selectList(replyQueryWrapper);
            for (Reply reply : replies){
                Map<String,Object> mapReply = new HashMap<>();
                User replyUser = userMapper.selectById(reply.getUserId());
                //判断点赞状态
                int replyStatus = likeService.getLikeStatus(userId,reply.getReplyId());
                Long replyLikeCount = likeService.likeCount(reply.getReplyId());

                mapReply.put("replyUser",replyUser);
                mapReply.put("reply",reply);
                map.put("replyStatus",replyStatus);
                map.put("replyLikeCount",replyLikeCount);
                replyList.add(mapReply);
            }


            map.put("comment",comment);
            map.put("commentUser",commentUser);
            map.put("commentStatus",commentStatus);
            map.put("commentLikeCount",commentLikeCount);
            map.put("reply",replyList);
            list.add(map);
        }

        return list;
    }

    @Override
    public Order getOrderId(Long ordNumber) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("ord_number",ordNumber);
        Order order = orderMapper.selectOne(wrapper);
        System.out.println("查询到的订单"+order);
        if (order != null){
            return order;
        }else {
            return null;
        }

    }


}
