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
@TableName("bussiness")
@ApiModel(value = "SxBussiness对象", description = "")
public class Bussiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("营业网点")
    @TableId(value = "bus_id", type = IdType.AUTO)
    private Integer busId;

    @ApiModelProperty("营业网点名")
    private String busName;

    @ApiModelProperty("网点地址")
    private String busAddress;

    @ApiModelProperty("网点联系方式")
    private Long busTel;

    @ApiModelProperty("营业时间")
    private String busTime;

    @ApiModelProperty("经纬度")
    private String busLal;

    private String backup;

    private String backupPlus;

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }
    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
    public String getBusAddress() {
        return busAddress;
    }

    public void setBusAddress(String busAddress) {
        this.busAddress = busAddress;
    }
    public Long getBusTel() {
        return busTel;
    }

    public void setBusTel(Long busTel) {
        this.busTel = busTel;
    }
    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }
    public String getBusLal() {
        return busLal;
    }

    public void setBusLal(String busLal) {
        this.busLal = busLal;
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
        return "SxBussiness{" +
            "busId=" + busId +
            ", busName=" + busName +
            ", busAddress=" + busAddress +
            ", busTel=" + busTel +
            ", busTime=" + busTime +
            ", busLal=" + busLal +
            ", backup=" + backup +
            ", backupPlus=" + backupPlus +
        "}";
    }
}
