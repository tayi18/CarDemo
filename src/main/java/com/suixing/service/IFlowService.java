package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Flow;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface IFlowService extends IService<Flow> {

    public Map<String,Object> saveFlow(Long out_trade_no, String trade_no, Float total_amount);

}
