package com.company.lock_04;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {

        //假设有三个停车位
        Semaphore semaphore = new Semaphore(3);

        //模拟5辆车
        for(int i = 0 ; i < 50 ; i++){
            final int temp = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到车位");
                    Thread.sleep(new Random().nextInt(3000));
                    System.out.println(Thread.currentThread().getName() + " 走了~");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },temp+"").start();
        }

    }

}
