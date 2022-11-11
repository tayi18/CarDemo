package com.suixing.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suixing.entity.Car;
import com.suixing.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}