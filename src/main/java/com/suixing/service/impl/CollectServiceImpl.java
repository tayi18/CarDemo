package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.entity.Collect;
import com.suixing.mapper.CarMapper;
import com.suixing.mapper.CollectMapper;
import com.suixing.service.ICollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private CarMapper carMapper;


    //查询所有收藏的商品
    @Override
    public ServerResponse getAllByUserId(Integer userId) {
        List<Map<String,Object>> list = new ArrayList<>();
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id",userId);
        collectQueryWrapper.eq("collect_status",0);
        collectQueryWrapper.select("collect_id","car_id");
        List<Collect> collects =  collectMapper.selectList(collectQueryWrapper);
        for (Collect collect:collects){
            QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
            carQueryWrapper.eq("car_id",collect.getCarId());
            Car car = carMapper.selectOne(carQueryWrapper);
            Map<String,Object> map = new HashMap<>();
            map.put("collect",collect);
            map.put("car",car);
            list.add(map);
        }
        return ServerResponse.success("ok",list);
    }
    //取消收藏
    @Override
    public ServerResponse updateCollect(Integer collectId) {
        Collect collect = collectMapper.selectById(collectId);
        collect.setCollectStatus(1);
        int row = collectMapper.updateById(collect);
        if (row !=0){
            return ServerResponse.success("ok",collect);
        }
        return ServerResponse.fail("fail",null);
    }

    @Override
    public ServerResponse saveCollect(Integer carId, Integer userId) {
        Collect collect = new Collect();
        collect.setCarId(carId);
        collect.setUserId(userId);
        collect.setCollectStatus(0);
        int row = collectMapper.insert(collect);
        if (row != 0){
            return ServerResponse.success("ok",collect);
        }
        return ServerResponse.fail("ok",null);
    }

    @Override
    public ServerResponse getCollect(Integer carId, Integer userId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",carId);
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("collect_status",0);
        Collect collect = collectMapper.selectOne(queryWrapper);
        if (collect != null)
            return ServerResponse.success("ok",collect);
        return null;
    }
}
