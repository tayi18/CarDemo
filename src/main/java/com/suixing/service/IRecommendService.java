package com.suixing.service;


import com.suixing.entity.Car;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-11-04
 */
public interface IRecommendService {

    /*基于用户的商品推荐*/
    public List<Car> getRecommentCarByUser(Integer userId,Integer howMany);

    /*基于内容的商品推荐*/
    public List<Car> getRecommentCarByCar(Integer userId,Integer carId,Integer howMany);


}
