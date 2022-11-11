package com.suixing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.mapper.CarMapper;
import com.suixing.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
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
@Component
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public ServerResponse getPageCarModelCarNameCarPrice(int page, String carModel, String carBrand, String carPrice) {
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        String redisKey = page+carModel+carBrand+carPrice;
//        System.out.println("当前查询的页数："+page);
        Object object =  getRedis(redisKey);      //先查询redis数据库中的数据
        if (object != null){
            return ServerResponse.success("查询成功",object);
        }
        if (!carModel.equals("no")){
            queryWrapper.eq("car_model",carModel);
        }
        if (!carBrand.equals("no")){
            String [] brandArray  = carBrand.split(" ");
            queryWrapper.and(wrapper -> {
                    for (int i = 0; i < brandArray.length; i++) {
                       wrapper.or().eq("car_brand",brandArray[i].trim());
                 }
            });
        }
        if (!carPrice.equals("no")){

            System.out.println(carPrice.length());
            if (carPrice.length()==5){
                int max = Integer.parseInt(carPrice.substring(carPrice.length()-3));
                queryWrapper.lt("car_price",max);
            }else if (carPrice.length() == 3){
                int min = Integer.parseInt(carPrice.substring(0,3));
                queryWrapper.gt("car_price",min);
            }else {
                int min = Integer.parseInt(carPrice.substring(0,3));
                int max = Integer.parseInt(carPrice.substring(carPrice.length()-3));
                queryWrapper.between("car_price",min,max);
                System.out.println("min:"+min);
                System.out.println("max"+max);
            }
        }


        Page<Car> curret = new Page<>(page,4);
        Page<Car> pageInfo = carMapper.selectPage(curret,queryWrapper);
        System.out.println("pageInfo:"+pageInfo);
//        List<Car> list = pageInfo.getRecords();
        System.out.println(pageInfo.getRecords());
        if (pageInfo.getRecords() != null) {
            setRedis(redisKey,pageInfo);   //加入redis数据库
            return ServerResponse.success("查询成功", pageInfo);
        }else
            return ServerResponse.fail("查询失败",null);

    }
    @Override
    public ServerResponse updateCarImg(Car car) {
        if (carMapper.updateById(car) > 0)
            return ServerResponse.success("插入成功", car);
        return ServerResponse.fail("插入失败",null);
    }
    @Override
    public ServerResponse getCarFilter(String carName) {
        if (carName == null)
           return ServerResponse.success("查询成功",carMapper.selectList(null));
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("car_name",carName);
        queryWrapper.select("car_name","car_price","car_model","car_disp","car_seat","car_img");

        List<Car> carList =   carMapper.selectList(queryWrapper);
        if (carList!=null)
            return ServerResponse.success("查询成功",carList);
        return ServerResponse.fail("查询失败",null);
    }

    @Override
    public ServerResponse getById(int carId) {
        return null;
    }
    public void setRedis(String key ,Object object){
        //存入redis
        //设置过期时间
        Random random = new Random();
        int time = 360 + random.nextInt(360);
        redisTemplate.opsForValue().set(key,object,time, TimeUnit.MINUTES);

    }


    //redis读
    public Object getRedis(String key){
        Object object = redisTemplate.opsForValue().get(key);
        System.out.println("查询的是reids中的数据");
        return object;
    }


}
