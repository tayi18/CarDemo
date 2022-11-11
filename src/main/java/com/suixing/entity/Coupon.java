package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("coupon")
@ApiModel(value = "SxCoupon对象", description = "")
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("优惠劵ID")
    @TableId(value = "cou_id", type = IdType.AUTO)
    private Integer couId;
    @ApiModelProperty("条件使用说明")
    private String couExplain;
  @ApiModelProperty("优惠卷类型")
    private String couType;
    @ApiModelProperty("使用周期")
    private String couCycle;
    @ApiModelProperty("活动结束时间")
    private LocalDate couStart;
    @ApiModelProperty("活动结束时间")
    private LocalDate couEnd;
    @ApiModelProperty("库存数量")
    private Integer couAmount;
    private Integer couPrice;
    private Integer couMax;
    private String couMode;
}
