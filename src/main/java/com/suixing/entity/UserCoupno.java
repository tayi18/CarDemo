package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
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
@TableName("user_coupno")
@Data
@ApiModel(value = "SxUserCoupno对象", description = "")
public class UserCoupno implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户优惠劵表id")
    @TableId(value = "user_cou_id", type = IdType.AUTO)
    private Integer userCouId;

    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("优惠卷")
    private Integer couId;
    @ApiModelProperty("优惠劵编号")
    private String userCouNum;
    @ApiModelProperty("领取时间")
    private Date userCouTime;
    @ApiModelProperty("有效开始")
    private Date userCouStart;
    @ApiModelProperty("失效时间")
    private Date userCouEnd;
    @ApiModelProperty("使用状态")
    private String userCouState;
    private String backup;
    private String backupPlus;

}
