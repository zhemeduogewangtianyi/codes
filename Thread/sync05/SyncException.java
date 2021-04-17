package com.wty.base.sync05;

import java.util.ArrayList;
import java.util.List;

public class SyncException {

    private int i = 0;
    private boolean flag = true;
    private List<Object> list = new ArrayList<>();

    public synchronized void operator(){

        while(flag){
            try{
                i++;
                if(i == 10){
                    shutdown();
                }
                if(i % 2 == 0){
                    int x = 1/0;
                }
                System.out.println("i " + i + "  " + Thread.currentThread().getName());

            }catch(Exception e){
                e.printStackTrace();
                System.err.println("这里可以记录异常数据信息 -> " + i);
                list.add(i);
                continue;
            }
        }

        System.out.println("异常的数据为 -> " + list.toString());
    }

    private void shutdown(){
        this.flag = false;
    }

    public static void main(String[] args){
        SyncException e = new SyncException();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                e.operator();
            }
        },"t1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                e.operator();
            }
        },"t2");
        t2.start();
    }

}
