package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Collect;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface ICollectService extends IService<Collect> {
    //查询所有收藏的商品
    ServerResponse getAllByUserId(Integer userId);
    //取消收藏
    ServerResponse updateCollect(Integer collectId);
    //添加收藏
    ServerResponse saveCollect(Integer carId,Integer userId);

    //检查是否收藏
    ServerResponse getCollect(Integer carId,Integer userId);


}
