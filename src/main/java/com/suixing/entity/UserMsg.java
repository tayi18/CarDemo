package com.suixing.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserMsg implements Serializable {
    private int userMsgId;
    private int userId;
    private String userMsgContent;
    private String userMsgType;
    private String userMsgStatus;
    private Date userMsgTime;
}
