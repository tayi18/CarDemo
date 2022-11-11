package com.suixing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.suixing.commons.ServerResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;



public class AppCodeDriver {

    public static String img_base64(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("图片不能为空！");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (!".jpg".equals(fileType) && ".png".equals(fileType)) {
//            throw new BizException("图片格式仅支持JPG、PNG！");
            System.out.println("图片格式仅支持JPG、PNG！");
        }
        byte[] data = null;
        try {
            InputStream in = file.getInputStream();
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static ServerResponse driverCard(MultipartFile multipartFile) throws Exception {
        String host = "https://dm-52.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_driver_license.json";
        String appcode = "dfda842606074884aa01a750dc3958de";
        String method = "POST";

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String, String> querys = new HashMap<String, String>();
        // 对图像进行base64编码
        String imgBase64 = img_base64(multipartFile);

        //configure配置
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");

        String config_str = configObj.toString();

        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        requestObj.put("image", imgBase64);
        if (configObj.size() > 0) {
            requestObj.put("configure", config_str);
        }
        String bodys = requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if (stat != 200) {
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
//                AppCodeIdCardServlet.getParam()
                return null;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            ServerResponse serverResponse = ServerResponse.success("ok",res_obj);

            System.out.println(res_obj.toJSONString());
            return serverResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
