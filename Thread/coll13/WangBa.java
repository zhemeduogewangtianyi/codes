package com.wty.intermediate.coll13;

import java.util.concurrent.DelayQueue;

public class WangBa implements Runnable{

    private DelayQueue<Wangmin> queue = new DelayQueue<>();

    private boolean yingye = true;

    /** 上网时间比率，单位毫秒 1000 = 1s */
//    private long ratio = 1000;
    private long ratio = 1000 * 60 * 30;

    /** 获得上网时间 */
    public Long getEndTime(double money){
        double v = ratio * money + System.currentTimeMillis();
        long floor = (long)Math.floor(v);
        long time = floor - System.currentTimeMillis();
        long day = time / (24 * 60 * 60 * 1000);
        long hour = (time - day * 24 * 60 * 60 * 1000) / (60 * 60 * 1000);
        long minute = (time - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000)) / (60 * 1000);
        long second = (time - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000) - (minute * 60 * 1000)) / 1000;
        System.err.println(day + "天" + hour + "小时" + minute + "分钟" + second + "秒");
        return floor;
    }

    private void shangji(String name, String id, double money) {

        Wangmin wangmin = new Wangmin(name,id,getEndTime(money));
        System.out.println("网名：" + wangmin.getName() + "身份证：" + wangmin.getId() + "交钱：" + money + "块钱，开始上机");
        this.queue.add(wangmin);
    }

    public void xiaji(Wangmin wangmin){
        System.out.println("网名：" + wangmin.getName() + "身份证：" + wangmin.getId() + "时间到下机。。");
    }

    @Override
    public void run() {
        while(yingye){
            try {
                Wangmin take = queue.take();
                xiaji(take);
//                if(queue.size() == 0){
//                    yingye = false;
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){
        try{
            System.out.println("网吧开始营业了");
            WangBa ty = new WangBa();
            Thread shangwang = new Thread(ty);
            shangwang.start();

            ty.shangji("路人甲","123",0.01);
            ty.shangji("路人乙","234",0.01);
            ty.shangji("路人丙","345",0.01);


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
