package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Comments;
import com.suixing.entity.Reply;
import com.suixing.entity.User;
import com.suixing.mapper.CommentsMapper;
import com.suixing.mapper.ReplyMapper;
import com.suixing.mapper.UserMapper;
import com.suixing.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse getReplyByCommId(Integer carId) {




        QueryWrapper<Reply> queryRely = new QueryWrapper<>();
        queryRely.eq("comm_id",carId);
        List<Reply> replieslist = replyMapper.selectList(queryRely);

        System.out.println("service层的回复"+replieslist);
        if (replieslist!= null)
            return ServerResponse.success("查询评论成功",replieslist);
        return ServerResponse.fail("查询评论失败",null);
    }

    @Override
    public User getUserByReplyId(Integer carId) {
        Comments comments = commentsMapper.selectById(carId);
        Integer userId = comments.getUserId();
        return userMapper.selectById(userId);
    }

    @Override
    public ServerResponse saveReply(Reply reply) {

        reply.setReplyTime(LocalDateTime.now());
        int rows = replyMapper.insert(reply);
        if (rows > 0){
            return ServerResponse.success("保存回复成功",reply);
        }
        return ServerResponse.fail("保存评论失败",null);
    }


}
