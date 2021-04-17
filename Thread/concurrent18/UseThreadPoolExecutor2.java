package com.wty.senior.concurrent18;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UseThreadPoolExecutor2 implements Runnable {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        int i = count.incrementAndGet();
        System.out.println("任务" + i );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        BlockingQueue queue = new LinkedBlockingDeque();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,
                10,
                120L,
                TimeUnit.SECONDS,
                queue
        );

        for(int i = 0 ; i < 20 ; i++){
            pool.execute(new UseThreadPoolExecutor2());
        }

        System.out.println("队列大小 ： " + queue.size());

        pool.shutdown();

    }
}
