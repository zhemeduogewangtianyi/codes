package com.company.lock_04;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousBlockingQueue {

    public static void main(String[] args) {

        //非公平
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + " put 1");
                blockingQueue.put("1111");
                System.out.println(Thread.currentThread().getName() + " put 2");
                blockingQueue.put("2222");
                System.out.println(Thread.currentThread().getName() + " put 3");
                blockingQueue.put("3333");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"aaa").start();

        new Thread(()->{
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " " + blockingQueue.take());

                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " " + blockingQueue.take());

                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " " + blockingQueue.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"bbb").start();



    }

}
