package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.entity.Flow;
import com.suixing.entity.Order;
import com.suixing.mapper.CarMapper;
import com.suixing.mapper.FlowMapper;
import com.suixing.mapper.OrderMapper;
import com.suixing.service.IFlowService;
import com.suixing.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements IFlowService {
    @Autowired
    private FlowMapper flowMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CarMapper carMapper;

    //添加流水，修改订单
    @Override
    public Map<String,Object> saveFlow(Long out_trade_no, String trade_no, Float total_amount) {
        //获得当前订单
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("ord_number",out_trade_no);
        Order order = orderMapper.selectOne(wrapper);
        System.out.println(order);
        order.setOrdSatus("待取车");
        order.setOrdUpdateTime(LocalDateTime.now());
        int rows1 = orderMapper.updateById(order);
        System.out.println(rows1);
        System.out.println(order);
        //生成流水
        Flow flow = new Flow();
        flow.setOrdId(order.getOrdId());
        flow.setCarId(order.getCarId());
        flow.setFlowDealNum(trade_no);
        flow.setFlowPayment(total_amount);
        //创建流水的时间
        flow.setFlowCreatetime(LocalDateTime.now());
        int row2 = flowMapper.insert(flow);
        System.out.println(row2);
        System.out.println(flow);
        Car car = carMapper.selectByCarId(flow.getCarId());

        Map<String,Object> map = new HashMap<>();
        map.put("orderNum",order.getOrdNumber());
        map.put("flowNum",flow.getFlowDealNum());
        map.put("flowPay",flow.getFlowPayment());
        map.put("carName",car.getCarName());


        return map;
    }
}
