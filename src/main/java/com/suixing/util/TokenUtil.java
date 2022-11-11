package com.suixing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.suixing.entity.LoginCustomer;
import com.suixing.entity.User;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    //token到期时间60s
    private static final long EXPIRE_TIME = 600 * 1000;
    //密钥盐
    private static final String TOKEN_SECRET = "123456qwertyuiop789";


    /**
     * 创建一个token
     * @param loginCustomer
     * @return 生成的token中不带有过期时间，token的过期时间由redis进行管理
     */
    public static String getToken(LoginCustomer loginCustomer){
    //    System.out.println("sign customer:" + loginCustomer);
        String token=null;
        try {
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("token", JSONObject.toJSONString(loginCustomer))//存放数据
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException| JWTCreationException je) {
            je.printStackTrace();

        }
        return token;
    }
    /**
     * 对token进行验证
     * @param token
     * @return
     */
    public static LoginCustomer parseToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String tokenInfo = decodedJWT.getClaim("token").asString();
        LoginCustomer loginCustomer = JSON.parseObject(tokenInfo, LoginCustomer.class);
        System.out.println("获得的token中的信息是：" + loginCustomer);
        return loginCustomer;
    }

}
