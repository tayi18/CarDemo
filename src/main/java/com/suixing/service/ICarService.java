package com.suixing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface ICarService extends IService<Car> {

    ServerResponse getPageCarModelCarNameCarPrice(int page,String carModel,String carBrand,String carPrice);
    ServerResponse updateCarImg(Car car);

    ServerResponse getCarFilter(String carName);
    ServerResponse getById(int carId);
}
