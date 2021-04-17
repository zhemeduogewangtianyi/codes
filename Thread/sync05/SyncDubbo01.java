package com.wty.base.sync05;

public class SyncDubbo01 {

    public synchronized void method1(){
        System.out.println(Thread.currentThread().getName() + " " + "method1");
        method2();
    }

    public synchronized void method2(){
        System.out.println(Thread.currentThread().getName() + " " + "method2");
        method3();
    }

    public synchronized void method3(){
        System.out.println(Thread.currentThread().getName() + " " + "method3");
    }

    public static void main(String[] args) {
        SyncDubbo01 sync = new SyncDubbo01();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sync.method1();
            }
        },"t1");

        t1.start();

    }

}
