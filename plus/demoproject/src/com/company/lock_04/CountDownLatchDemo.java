package com.company.lock_04;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {

        //countDownLatchExample();

    }

    private static void countDownLatchExample() {
        for(int i = 0 ; i < 6 ; i++){
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第 " + temp + " 位同学 " + Thread.currentThread().getName() + " 执行完毕！");
                }
            },i+"").start();
        }

        System.out.println("我是 " + Thread.currentThread().getName() + " 线程");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for(int i = 0 ; i < 6 ; i++){
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第 " + temp + " 位同学 " + Thread.currentThread().getName() + " 执行完毕！");
                    countDownLatch.countDown();
                }
            },i+" countDownLatch ~").start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我是 " + Thread.currentThread().getName() + " 线程");
    }

}
