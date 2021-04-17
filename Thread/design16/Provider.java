package com.wty.intermediate.design16;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Provider implements Runnable {

    private BlockingQueue<Data> queue;

    private volatile boolean isRunning = true;

    private static AtomicInteger count = new AtomicInteger(0);

    private static Random r =  new Random();

    Provider(BlockingQueue<Data> queue){
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
                int id = count.incrementAndGet();
                Data data = new Data(id,id + " -> name");
                boolean offer = queue.offer(data, 2, TimeUnit.SECONDS);
                if(!offer){
                    System.out.println("提交缓冲数据失败！");
                }
                System.out.println("当前线程 "+Thread.currentThread().getName() + " 获取了数据 【" + data.toString() + "】 进行装载到公共缓存区中");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
