package com.wty.base.sync03;

public class MyObject {

    public synchronized void method1(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method2(){
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        final MyObject o = new MyObject();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                o.method1();
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                o.method2();
            }
        },"t2");

        t1.start();
        t2.start();
    }

}
