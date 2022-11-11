package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Reply;
import com.suixing.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface IReplyService extends IService<Reply> {

    public ServerResponse getReplyByCommId(Integer carId);

    public User getUserByReplyId(Integer carId);

    public ServerResponse saveReply(Reply reply);
}
