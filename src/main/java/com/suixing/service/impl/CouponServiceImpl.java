package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Coupon;
import com.suixing.mapper.CouponMapper;
import com.suixing.mapper.UserCoupnoMapper;
import com.suixing.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {
    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public ServerResponse getCouponAll() {
        QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cou_mode","1");
        List<Coupon> allList = couponMapper.selectList(queryWrapper);
        if (allList != null) {
            //加载优惠券，同时加载到rediss数据库中
            List<Coupon> couponList = new ArrayList<>();
            for (Coupon coupon : allList) {
                if (coupon.getCouAmount()>0){
                    String key = "coupon_"+coupon.getCouId();
                    redisTemplate.opsForValue().set(key,coupon,12, TimeUnit.HOURS);
                   // System.out.println(coupon);
                    couponList.add(coupon);
                }
            }
            return ServerResponse.success("查询成功",couponList);
        }
        return ServerResponse.fail("查询失败",null);

    }

}
