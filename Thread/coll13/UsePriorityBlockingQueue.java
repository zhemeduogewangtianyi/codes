package com.wty.intermediate.coll13;

import java.util.concurrent.PriorityBlockingQueue;

public class UsePriorityBlockingQueue {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue<Task> priorityQueue = new PriorityBlockingQueue<>();

        Task t1 = new Task();
        t1.setId(3L);
        t1.setName("ID 为 3");

        Task t2 = new Task();
        t2.setId(4L);
        t2.setName("ID 为 4");

        Task t3 = new Task();
        t3.setId(1L);
        t3.setName("ID 为 1");

        priorityQueue.add(t1);
        priorityQueue.add(t2);
        priorityQueue.add(t3);

        System.err.println("容器 ： " + priorityQueue);
        System.out.println(priorityQueue.take().toString());
        System.out.println(priorityQueue.take().toString());
        System.out.println(priorityQueue.take().toString());

    }

}
