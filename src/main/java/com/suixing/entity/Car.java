package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2022-10-03
 */
//@TableName("car")
@ApiModel(value = "SxCar对象", description = "")
@Document(indexName = "suixingcar")
@Data
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
    @ApiModelProperty("车辆id")
   @TableId(value = "car_id", type = IdType.AUTO)
    private Integer carId;

    @ApiModelProperty("车辆名")
    @Field(type = FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_max_word")  //设置为可分词
    private String carName;

    @Field(type = FieldType.Auto)//手动设置为keyword  但同时也就不能分词
    @ApiModelProperty("品牌名")
    private String carBrand;

    @Field(type = FieldType.Text)
    @ApiModelProperty("日租价格")
    private Float carPrice;

    @ApiModelProperty("营业网点id")
    private Integer busId;

    @ApiModelProperty("车辆状态")
    private String carStatus;

    @ApiModelProperty("车辆图片")
    private String carImg;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("车型")
    private String carModel;

    @Field(type = FieldType.Auto)
    @ApiModelProperty("排量")
    private String carDisp;


    @ApiModelProperty("座位数")
    private String carSeat;


    @ApiModelProperty("变速箱")
    private String carCase;

    @Field(type = FieldType.Auto)
    @ApiModelProperty("进气")
    private String carExhaust;

    @ApiModelProperty("邮箱")
    private String carTank;
    private String backup;
    private String backuPlus;
}
