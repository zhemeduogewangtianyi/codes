package com.wty.maill.enumeration;

public enum MailStatusEnum {
    /** 暂存 */
    DRAFT("0"),
    /** 发送到队列 */
    SEND_IN("1"),
    /** 发送成功 */
    SEN_OD("2"),
    /** 发送失败 */
    SEND_ERR("3")
    ;

    private String code;

    MailStatusEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
