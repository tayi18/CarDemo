package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
@TableName("myorder")
@ApiModel(value = "SxOrder对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @TableId(value = "ord_id", type = IdType.AUTO)
    private Integer ordId;

    @ApiModelProperty("订单编号")
    private Long ordNumber;

    @ApiModelProperty("车辆id")
    private Integer carId;

    @ApiModelProperty("订单状态")
    private String ordSatus;

    @ApiModelProperty("订单价格")
    private Float ordPrice;

    @ApiModelProperty("修改订单时间")
    private LocalDateTime ordUpdateTime;

    @ApiModelProperty("创建订单时间")
    private Date ordCreateTime;

    @ApiModelProperty("下单时间")
    private LocalDateTime ordStarttime;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("取车门店")
    private String ordPickup;

    @ApiModelProperty("还车门店")
    private String ordDropoff;


//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("取车时间")
    private String ordPicTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("还车时间")
    private String ordDroTime;

    @ApiModelProperty("取车地址")
    private String ordDroAddress;

    @ApiModelProperty("还车地址")
    private String ordPicAddress;

    @ApiModelProperty("租期")
    private Long ordLease;

    @ApiModelProperty("取车人电话")
    private Long ordPicTelno;

    @ApiModelProperty("取车人身份证")
    private String ordPicIdcard;

    @ApiModelProperty("取车人驾驶证照片")
    private String ordPicCard;

    @ApiModelProperty("手续费")
    private Float ordFees;

    @ApiModelProperty("其他服务小费")
    private Float ordServiceTip;

    @ApiModelProperty("优惠卷id")
    private Integer couId;

    @ApiModelProperty("优惠金额")
    private Float ordCouMoney;

    private String backup;

    private String backupPlus;

    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        this.ordId = ordId;
    }
    public Long getOrdNumber() {
        return ordNumber;
    }

    public void setOrdNumber(Long ordNumber) {
        this.ordNumber = ordNumber;
    }
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    public String getOrdSatus() {
        return ordSatus;
    }

    public void setOrdSatus(String ordSatus) {
        this.ordSatus = ordSatus;
    }
    public Float getOrdPrice() {
        return ordPrice;
    }

    public void setOrdPrice(Float ordPrice) {
        this.ordPrice = ordPrice;
    }
    public LocalDateTime getOrdUpdateTime() {
        return ordUpdateTime;
    }

    public void setOrdUpdateTime(LocalDateTime ordUpdateTime) {
        this.ordUpdateTime = ordUpdateTime;
    }
    public Date getOrdCreateTime() {
        return ordCreateTime;
    }

    public void setOrdCreateTime(Date ordCreateTime) {
        this.ordCreateTime = ordCreateTime;
    }
    public LocalDateTime getOrdStarttime() {
        return ordStarttime;
    }

    public void setOrdStarttime(LocalDateTime ordStarttime) {
        this.ordStarttime = ordStarttime;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getOrdPickup() {
        return ordPickup;
    }

    public void setOrdPickup(String ordPickup) {
        this.ordPickup = ordPickup;
    }
    public String getOrdDropoff() {
        return ordDropoff;
    }

    public void setOrdDropoff(String ordDropoff) {
        this.ordDropoff = ordDropoff;
    }
    public String getOrdPicTime() {
        return ordPicTime;
    }

    public void setOrdPicTime(String ordPicTime) {
        this.ordPicTime = ordPicTime;
    }
    public String getOrdDroTime() {
        return ordDroTime;
    }

    public void setOrdDroTime(String ordDroTime) {
        this.ordDroTime = ordDroTime;
    }
    public String getOrdDroAddress() {
        return ordDroAddress;
    }

    public void setOrdDroAddress(String ordDroAddress) {
        this.ordDroAddress = ordDroAddress;
    }
    public String getOrdPicAddress() {
        return ordPicAddress;
    }

    public void setOrdPicAddress(String ordPicAddress) {
        this.ordPicAddress = ordPicAddress;
    }
    public Long getOrdLease() {
        return ordLease;
    }

    public void setOrdLease(Long ordLease) {
        this.ordLease = ordLease;
    }
    public Long getOrdPicTelno() {
        return ordPicTelno;
    }

    public void setOrdPicTelno(Long ordPicTelno) {
        this.ordPicTelno = ordPicTelno;
    }
    public String getOrdPicIdcard() {
        return ordPicIdcard;
    }

    public void setOrdPicIdcard(String ordPicIdcard) {
        this.ordPicIdcard = ordPicIdcard;
    }
    public String getOrdPicCard() {
        return ordPicCard;
    }

    public void setOrdPicCard(String ordPicCard) {
        this.ordPicCard = ordPicCard;
    }
    public Float getOrdFees() {
        return ordFees;
    }

    public void setOrdFees(Float ordFees) {
        this.ordFees = ordFees;
    }
    public Float getOrdServiceTip() {
        return ordServiceTip;
    }

    public void setOrdServiceTip(Float ordServiceTip) {
        this.ordServiceTip = ordServiceTip;
    }
    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
        this.couId = couId;
    }
    public Float getOrdCouMoney() {
        return ordCouMoney;
    }

    public void setOrdCouMoney(Float ordCouMoney) {
        this.ordCouMoney = ordCouMoney;
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
        return "SxOrder{" +
            "ordId=" + ordId +
            ", ordNumber=" + ordNumber +
            ", carId=" + carId +
            ", ordSatus=" + ordSatus +
            ", ordPrice=" + ordPrice +
            ", ordUpdateTime=" + ordUpdateTime +
            ", ordCreateTime=" + ordCreateTime +
            ", ordStarttime=" + ordStarttime +
            ", userId=" + userId +
            ", ordPickup=" + ordPickup +
            ", ordDropoff=" + ordDropoff +
            ", ordPicTime=" + ordPicTime +
            ", ordDroTime=" + ordDroTime +
            ", ordDroAddress=" + ordDroAddress +
            ", ordPicAddress=" + ordPicAddress +
            ", ordLease=" + ordLease +
            ", ordPicTelno=" + ordPicTelno +
            ", ordPicIdcard=" + ordPicIdcard +
            ", ordPicCard=" + ordPicCard +
            ", ordFees=" + ordFees +
            ", ordServiceTip=" + ordServiceTip +
            ", couId=" + couId +
            ", ordCouMoney=" + ordCouMoney +
            ", backup=" + backup +
            ", backupPlus=" + backupPlus +
        "}";
    }
}
