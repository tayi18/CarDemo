package com.suixing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suixing.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {
    //登录

}
