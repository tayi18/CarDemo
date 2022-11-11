package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.entity.UserMsg;

public interface IUserMsgService extends IService<UserMsg> {
    ServerResponse  getUserMsg (int userId);
    ServerResponse  msgUpdate (int userId,int msgId);
}
