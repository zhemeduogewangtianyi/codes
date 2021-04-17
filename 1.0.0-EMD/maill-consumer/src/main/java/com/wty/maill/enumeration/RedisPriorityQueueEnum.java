package com.wty.maill.enumeration;
/**
 * Redis优先级的队列枚举
 * 1 2 3 - 不着急
 * 3 4 5 - 正常
 * 7 8 9 -着急
 * */
public enum RedisPriorityQueueEnum {

    /** 7,8,9 隐私、安全、交易
     *  要求快速实时的拉取消息
     * */
    FAST_QUEUE("fast"),

    /** 4,5,6 活动、通知类 */
    NORMAL_QUEUE("normal"),

    /** 1,2,3 汇总、调查
     *  比较耗时，数据要跨库json
     *  慢慢处理。
     * */
    DEFER_QUEUE("defer"),
    ;

    private String code;

    RedisPriorityQueueEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }


}
