package com.wty.intermediate.coll13;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Wangmin implements Delayed {

    /** 姓名 */
    private String name;

    /** 身份证 */
    private String id;

    /** 结束时间 */
    private Long endTime;

    /** 定义时间工具类 */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public Wangmin(String name, String id, Long endTime) {
        this.name = name;
        this.id = id;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     * 用来判断是否到了下机时间
     * */
    @Override
    public long getDelay(TimeUnit unit) {
//        return unit.convert(endTime,TimeUnit.MILLISECONDS) - unit.convert(System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        return endTime - System.currentTimeMillis();
    }

    /**
     * 相互比较排序
     * */
    @Override
    public int compareTo(Delayed o) {
        Wangmin w = (Wangmin) o;
        return this.getDelay(this.timeUnit) - w.getDelay(this.timeUnit) > 0 ? 1 : 0;
    }
}
