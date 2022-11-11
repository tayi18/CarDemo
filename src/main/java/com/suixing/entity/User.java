package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@TableName("user")
@ApiModel(value = "SxUser对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("手机号")
    private Long userTel;

    @ApiModelProperty("密码")
    private String userPsd;

    @ApiModelProperty("性别(0:男生 1:女生）")
    private String userGender;

    @ApiModelProperty("身份证号")
    private String userIdcard;

    @ApiModelProperty("真实姓名")
    private String userName;

    @ApiModelProperty("电子邮件")
    private String userEmail;

    @ApiModelProperty("出生日期")
    private LocalDate userBir;

    @ApiModelProperty("用户昵称")
    private String userPetname;

    @ApiModelProperty("备用")
    private String backup;

    @ApiModelProperty("备用")
    private String backupPlus;


    public User(Integer userId, Long userTel) {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Long getUserTel() {
        return userTel;
    }

    public void setUserTel(Long userTel) {
        this.userTel = userTel;
    }
    public String getUserPsd() {
        return userPsd;
    }

    public void setUserPsd(String userPsd) {
        this.userPsd = userPsd;
    }
    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public LocalDate getUserBir() {
        return userBir;
    }

    public void setUserBir(LocalDate userBir) {
        this.userBir = userBir;
    }
    public String getUserPetname() {
        return userPetname;
    }

    public void setUserPetname(String userPetname) {
        this.userPetname = userPetname;
    }
    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }
    public String getBackupPlus() {
        return backupPlus;
    }

    public void setBackupPlus(String backupPlus) {
        this.backupPlus = backupPlus;
    }

    @Override
    public String toString() {
        return "SxUser{" +
            "userId=" + userId +
            ", userTel=" + userTel +
            ", userPsd=" + userPsd +
            ", userGender=" + userGender +
            ", userIdcard=" + userIdcard +
            ", userName=" + userName +
            ", userEmail=" + userEmail +
            ", userBir=" + userBir +
            ", userPetname=" + userPetname +
            ", backup=" + backup +
            ", backupPlus=" + backupPlus +
        "}";
    }
}
