package com.wty.base.sync006;

public class ChangeLock {

    private String lock = "lock";

    public void method(){
        synchronized (lock){
            System.out.println(Thread.currentThread().getName() + " 开始");
            try {
                lock = "changeLocal";
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 结束");
        }
    }

    public static void main(String[] args) {
        ChangeLock lock = new ChangeLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method();
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method();
            }
        },"t2");

        t1.start();
        t2.start();
    }

}
