package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@TableName("flow")
@ApiModel(value = "SxFlow对象", description = "")
public class Flow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("流水id")
    @TableId(value = "flow_id", type = IdType.AUTO)
    private Integer flowId;

    @ApiModelProperty("支付宝交易号")
    private String flowDealNum;

    @ApiModelProperty("车辆")
    private Integer carId;

    @ApiModelProperty("支付金额")
    private Float flowPayment;

    @ApiModelProperty("订单id")
    private Integer ordId;
    @ApiModelProperty("流水生成时间")
    private LocalDateTime flowCreatetime;

    private String backupPlus;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }
    public String getFlowDealNum() {
        return flowDealNum;
    }

    public void setFlowDealNum(String flowDealNum) {
        this.flowDealNum = flowDealNum;
    }
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    public Float getFlowPayment() {
        return flowPayment;
    }

    public void setFlowPayment(Float flowPayment) {
        this.flowPayment = flowPayment;
    }
    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        this.ordId = ordId;
    }
    public LocalDateTime getFlowCreatetime() {
        return flowCreatetime;
    }

    public void setFlowCreatetime(LocalDateTime flowCreatetime) {
        this.flowCreatetime = flowCreatetime;
    }
    public String getBackupPlus() {
        return backupPlus;
    }

    public void setBackupPlus(String backupPlus) {
        this.backupPlus = backupPlus;
    }

    @Override
    public String toString() {
        return "SxFlow{" +
            "flowId=" + flowId +
            ", flowDealNum=" + flowDealNum +
            ", carId=" + carId +
            ", flowPayment=" + flowPayment +
            ", ordId=" + ordId +
            ", flowCreatetime=" + flowCreatetime +
            ", backupPlus=" + backupPlus +
        "}";
    }
}
