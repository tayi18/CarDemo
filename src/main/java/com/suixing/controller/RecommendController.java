package com.suixing.controller;


import com.suixing.entity.Car;
import com.suixing.service.IRecommendService;
import com.suixing.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-11-04
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private IRecommendService recommendService;

    /*基于用户的商品推荐*/
    @GetMapping("recommendByUser")
    @ResponseBody
    public List<Car> getRecommendCarByUser(HttpServletRequest request){
        //从请求头部获得token
        String token = request.getHeader("token");//get token
        Integer userId = TokenUtil.parseToken(token).getUserId();

        return recommendService.getRecommentCarByUser(userId,3);
    }

    /*基于内容的商品推荐*/
    @GetMapping("recommendByCar")
    @ResponseBody
    public List<Car> getRecommendCarByCar(@RequestParam("userId") Integer userId,
                                          @RequestParam("carId") Integer carId,
                                          @RequestParam("num") Integer num){
        return recommendService.getRecommentCarByCar(userId,carId,num);
    }

}
