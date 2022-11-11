package com.suixing.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.Product;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.suixing.config.AlipayConfig;
import com.suixing.entity.Flow;
import com.suixing.service.IFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@Controller
public class FlowController {
    @Autowired
    private IFlowService flowService;

    @PostMapping("alipay") //调用支付宝接口
    @ResponseBody
    public String aliPay(String carName,Long orderNum,Float orderPrice,HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {

        System.out.println(1);

        //1.初始化AliPayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,
                AlipayConfig.app_id,
                AlipayConfig.merchant_private_key,
                "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);
        //2.设置请求参数，return url
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(AlipayConfig.return_url);
        payRequest.setNotifyUrl(AlipayConfig.notify_url);

        request.setCharacterEncoding("UTF-8");
        /*Long orderNum = Long.valueOf(request.getParameter("orderNum"));
        Float orderPrice = Float.valueOf(request.getParameter("orderPrice"));
        String carName =request.getParameter("carName");*/
        System.out.println(orderNum);
        System.out.println(orderPrice);
        System.out.println(carName);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        Long out_trade_no = orderNum;
        //付款金额，必填
        Float total_amount = orderPrice;
        //订单名称，必填
        String subject = carName;
        //System.out.println(out_trade_no);
        //System.out.println(total_amount);
        //System.out.println(subject);

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "15m";

        payRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //3.请求支付
        String result = alipayClient.pageExecute(payRequest).getBody();
        //System.out.println(result);
        return result;
    }

    //支付宝支付成功后，需要调用的业务处理（1.添加流水，2.修改订单）
    //同步请求
    @RequestMapping("alipayReturnNotice")
    public ModelAndView returnNotice(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        //System.out.println("支付成功后，支付宝返回的所有数据:"+request);
        //System.out.println("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<>();

        Map<String,String[]> requestParams = request.getParameterMap();
        System.out.println(222);

        for (Iterator<String> iterator = requestParams.keySet().iterator(); iterator.hasNext();){
            String name =    iterator.next();
            System.out.println("return notice name:"+name);

            String[] values = (String[])requestParams.get(name);

            String valueStr = "";
            for (int i=0;i<values.length;i++){
                valueStr=(i==values.length-1)?valueStr + values[i] : valueStr + values[i]+",";
            }
            //System.out.println(valueStr);

            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified =  AlipaySignature.rsaCheckV1(params,AlipayConfig.alipay_public_key,AlipayConfig.charset,AlipayConfig.sign_type);//调用SDK验证签名
        //System.out.println(signVerified);
        ModelAndView mv = new ModelAndView("order/order_payment");

        if(signVerified) {
            //商户订单号
            Long out_trade_no = Long.valueOf(new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8"));
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            Float total_amount = Float.valueOf(new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8"));
//            String carName = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");

            // 修改订单状态为支付成功，已付款; 同时新增支付流水
            Map<String,Object> map = flowService.saveFlow(out_trade_no, trade_no, total_amount);

            out_trade_no = (Long) map.get("orderNum");
            trade_no = (String) map.get("flowNum");
            total_amount = (Float) map.get("flowPay");
            String carName = (String) map.get("carName");

            System.out.println(out_trade_no);
            System.out.println(trade_no);
            System.out.println(total_amount);
            System.out.println(carName);

            mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
            mv.addObject("carName",carName);
        }
        return mv;
    }

    //异步请求
    public ModelAndView notifyNotice(){
        return null;
    }



}
