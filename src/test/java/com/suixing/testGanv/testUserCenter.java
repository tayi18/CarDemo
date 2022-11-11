package com.suixing.testGanv;

import com.suixing.commons.ServerResponse;
import com.suixing.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class testUserCenter {
    @Autowired
    private IUserCenterService userCenterService;

    @Autowired
    private IBussinessService bussinessService;

    @Autowired
    private IFlowService flowService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICollectService collectService;
    @Autowired
    private ICommentsService commentsService;


    @Test
    public void getUserById(){
        ServerResponse response = userCenterService.getUserById(6);
        System.out.println(response);
    }

    @Test
    public void getBuseAll(){
        ServerResponse response = bussinessService.getAll();
        System.out.println(response);
    }

    @Test
    public void getCoupByUserId(){
        ServerResponse response = userCenterService.getUserCoupon(6);
        System.out.println(response);
        response.getData().toString();
    }

    @Test
    public void getNum(){
        double v = Math.random()*1000000000;
        System.out.println(Math.round(v));
    }

//    @Test
//    public void getOrder(){
//        ServerResponse response = orderService.getById(5);
//        System.out.println(response.getData());
//
//    }
    @Test
    public void saveFlow(){
        Map<String,Object> map = flowService.saveFlow(953L,"44552211",452.1f);
        System.out.println(map.get("orderNum"));
        System.out.println(map.get("flowNum"));
        System.out.println(map.get("flowPay"));
        System.out.println(map.get("carName"));
    }

    @Test
    public void getAllOrder(){
        ServerResponse response = userCenterService.getUserOrderAll(6);
        System.out.println(response.getData());
    }

    @Test
    public void getAllCollect(){
        ServerResponse response = collectService.getAllByUserId(6);
        List<Map<String,Object>> list = (List<Map<String, Object>>) response.getData();
        list.forEach(System.out::println);
        //System.out.println(response.getData());
    }
    @Test
    public void saveCollect(){
        ServerResponse response = collectService.saveCollect(103,6);
        System.out.println(response.getData());
    }
    @Test
    public void getCollect(){
        ServerResponse response = collectService.getCollect(102,6);
        System.out.println("response::"+response);
    }

    @Test
    public void getComm(){
//        ServerResponse serverResponse = commentsService.getCommentsByCarId(4);
//        System.out.println(serverResponse.getData());

        List<Map<String,Object>> list = commentsService.getCommentReplyByCarId(102,6);
        list.forEach(System.out::println);
        System.out.println(list.get(0).get("commentStatus"));

    }

}
