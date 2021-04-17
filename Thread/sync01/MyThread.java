package com.wty.base.sync01;

public class MyThread implements Runnable{

    private int count = 5;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count " + count);
    }

    public static void main(String[] args) {
        MyThread mythread = new MyThread();
        Thread t1 = new Thread(mythread,"t1");
        Thread t2 = new Thread(mythread,"t2");
        Thread t3 = new Thread(mythread,"t3");
        Thread t4 = new Thread(mythread,"t4");
        Thread t5 = new Thread(mythread,"t5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
