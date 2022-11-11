package com.suixing.service;

import com.suixing.commons.ServerResponse;
import com.suixing.entity.Bussiness;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface IBussinessService {
    ServerResponse getAll();
    //查询网点部分信息
    Bussiness getBussinessWithInfo(int busId);
    ServerResponse getBussiness(int busId);
}
