package com.wty.base.sync07;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileNoAtomic extends Thread{

    private static volatile int num = 0;
    private static AtomicInteger ai = new AtomicInteger(0);

    public static void addNum(){
        for(int i = 0 ; i < 10000 ; i++){
            num++;
            ai.incrementAndGet();
        }
        System.out.println("num ： " + num + " ai ： " + ai);
    }

    @Override
    public void run(){
        addNum();
    }

    public static void main(String[] args) {
        VolatileNoAtomic[] volatileNoAtomic = new VolatileNoAtomic[10];
        for(int i = 0 ; i < volatileNoAtomic.length ; i++){
            volatileNoAtomic[i] = new VolatileNoAtomic();
        }

        for(VolatileNoAtomic v : volatileNoAtomic){
            v.start();
        }
    }
}
