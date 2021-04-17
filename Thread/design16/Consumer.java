package com.wty.intermediate.design16;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<Data> queue;

    private static Random r =  new Random();

    private volatile boolean isRunning = true;

    public Consumer(BlockingQueue<Data> queue){
        this.queue = queue;
    }

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        while(isRunning){
            try {
                Thread.sleep(r.nextInt(1000));
                Data take = queue.take();
                System.err.println(Thread.currentThread().getName() + " 消费 数据 【" + take.toString() + "】 成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
