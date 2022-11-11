package com.suixing.entity;

import io.swagger.models.auth.In;

public class LoginCustomer {
    private Integer userId;
    private String userName;

    private Long userTel;

    public LoginCustomer(Integer userId, String userName, Long userTel) {
        this.userId = userId;
        this.userName = userName;
        this.userTel = userTel;
    }

    public LoginCustomer(){
    }


    public Long getUserTel() {
        return userTel;
    }

    public void setUserTel(Long userTel) {
        this.userTel = userTel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "LoginCustomer{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userTel=" + userTel +
                '}';
    }
}
