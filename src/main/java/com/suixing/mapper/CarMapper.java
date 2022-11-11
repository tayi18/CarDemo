package com.suixing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
public interface CarMapper extends BaseMapper<Car> {
    public Car selectByCarId(int carId);
}
