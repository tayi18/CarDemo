package com.suixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

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
@TableName("reply")
@ApiModel(value = "SxReply对象", description = "")
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "reply_id", type = IdType.AUTO)
    private Integer replyId;

    private Integer commId;

    private Integer userId;

    private String replyContent;

    private LocalDateTime replyTime;

    private String backup;

    private String backupPlus;

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }
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
    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
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
        return "SxReply{" +
            "replyId=" + replyId +
            ", commId=" + commId +
            ", userId=" + userId +
            ", replyContent=" + replyContent +
            ", replyTime=" + replyTime +
            ", backup=" + backup +
            ", backupPlus=" + backupPlus +
        "}";
    }
}
