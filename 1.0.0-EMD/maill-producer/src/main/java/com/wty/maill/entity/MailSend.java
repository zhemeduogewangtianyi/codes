package com.wty.maill.entity;

import java.util.Date;

public class MailSend {
    /** 消息发送ID */
    private String sendId;

    /** 发送给谁的邮箱 */
    private String sendTo;

    /** 邮件主题。。。 */
    private String sendMail;

    /** 发送内容 */
    private String sendContent;

    /** 消息投递优先级，根据 RedisPriorityQueue 判断数据应该去什么队列 */
    private Long sendPriority;

    /** 投递给队列的次数 */
    private Long sendCount;

    /** 当前消息投递状态 */
    private String sendStatus;

    /** 邮件说明 */
    private String remark;

    /** 版本号，乐观更新 */
    private Long version;

    /** 修改人 */
    private String updateBy;

    /** 修改时间 */
    private Date updateTime;

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSendMail() {
        return sendMail;
    }

    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public Long getSendPriority() {
        return sendPriority;
    }

    public void setSendPriority(Long sendPriority) {
        this.sendPriority = sendPriority;
    }

    public Long getSendCount() {
        return sendCount;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}