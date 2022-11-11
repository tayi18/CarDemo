package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author smith
 * @since 2022-10-31
 */
@ApiModel(value = "Comments对象", description = "")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    @TableId(value = "comm_id", type = IdType.AUTO)
    private Integer commId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("车辆id")
    private Integer carId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("评论时间")
    private LocalDateTime commTime;

    @ApiModelProperty("评论内容")
    private String commContent;

    @ApiModelProperty("点赞数量")
    private Integer commFabulous;

    private String backup;

    private String backupPlus;

    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public LocalDateTime getCommTime() {
        return commTime;
    }

    public void setCommTime(LocalDateTime commTime) {
        this.commTime = commTime;
    }
    public String getCommContent() {
        return commContent;
    }

    public void setCommContent(String commContent) {
        this.commContent = commContent;
    }
    public Integer getCommFabulous() {
        return commFabulous;
    }

    public void setCommFabulous(Integer commFabulous) {
        this.commFabulous = commFabulous;
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
        return "Comments{" +
            "commId=" + commId +
            ", userId=" + userId +
            ", carId=" + carId +
            ", orderId=" + orderId +
            ", commTime=" + commTime +
            ", commContent=" + commContent +
            ", commFabulous=" + commFabulous +
            ", backup=" + backup +
            ", backupPlus=" + backupPlus +
        "}";
    }
}
