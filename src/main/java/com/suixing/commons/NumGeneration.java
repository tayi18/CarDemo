package com.suixing.commons;

import lombok.Data;
import net.bytebuddy.asm.Advice;

import java.util.UUID;

@Data
public class NumGeneration {
    //订单号生成
    public static Integer creatOrderNum(){
        String orderNum = "10015"+ UUID.randomUUID().toString().replace("-","").replaceAll("[a-zA-Z]","" );
        return Integer.parseInt(orderNum);
    }
    //优惠卷编号生成
    public static String creatCoupnoNum(){
        String coupnoNum = "20015"+ UUID.randomUUID().toString().replace("-","").replaceAll("[a-zA-Z]","" );
        return coupnoNum;
    }


}
