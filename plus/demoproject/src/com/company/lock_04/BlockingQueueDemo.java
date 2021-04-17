package com.company.lock_04;

import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    public static void main(String[] args) {
//        throwExceptionQueue();

//        spValue();

//        blocking();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        try {
            System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void blocking() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.put("a");
            blockingQueue.put("a");
            blockingQueue.put("a");
            System.out.println("======================");
//            blockingQueue.put("a");

            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
//            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void spValue() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    private static void throwExceptionQueue() {
        //        List list = new ArrayList();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        Collections.addAll(blockingQueue,"a","b","c");

        //java.lang.IllegalStateException: Queue full
//        blockingQueue.add("1");

        String element = blockingQueue.element();
        System.out.println(element);

        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
    }

}
