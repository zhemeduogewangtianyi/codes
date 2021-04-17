package com.wty.maill.entity.vo;

import java.util.Map;

public class MailDataVO {

    /** 邮件的ID */
    private String userId;

    /** 主题 */
    private String subject;

    /** 收件人 */
    private String from;

    /** 收件人 */
    private String to;

    /** 邮件内容 */
    private String content;

    /** 邮件模板名称 */
    private String templateName;

    /** 参数集 */
    private Map<String,Object> params;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
