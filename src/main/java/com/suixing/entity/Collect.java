package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@TableName("collect")
@ApiModel(value = "SxCollect对象", description = "")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收藏id")
    @TableId(value = "collect_id", type = IdType.AUTO)
    private Integer collectId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("车辆id")
    private Integer carId;

    @ApiModelProperty("收藏状态")
    private Integer collectStatus;

    private String backup;

    private String backPlus;

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
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
    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }
    public String getBackPlus() {
        return backPlus;
    }

    public void setBackPlus(String backPlus) {
        this.backPlus = backPlus;
    }

    @Override
    public String toString() {
        return "SxCollect{" +
            "collectId=" + collectId +
            ", userId=" + userId +
            ", carId=" + carId +
            ", collectStatus=" + collectStatus +
            ", backup=" + backup +
            ", backPlus=" + backPlus +
        "}";
    }
}
