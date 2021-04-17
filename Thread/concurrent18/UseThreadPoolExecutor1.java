package com.wty.senior.concurrent18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseThreadPoolExecutor1 {

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,2,60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                new MyRejected()
        );

        MyTask mt1 = new MyTask(1,"任务1");
        MyTask mt2 = new MyTask(2,"任务2");
        MyTask mt3 = new MyTask(3,"任务3");
        MyTask mt4 = new MyTask(4,"任务4");
        MyTask mt5 = new MyTask(5,"任务5");
        MyTask mt6 = new MyTask(6,"任务6");

        pool.execute(mt1);
        pool.execute(mt2);
        pool.execute(mt3);
        pool.execute(mt4);
        pool.execute(mt5);
        pool.execute(mt6);

        pool.shutdown();

    }

}
