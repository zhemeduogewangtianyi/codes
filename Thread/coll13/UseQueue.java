package com.wty.intermediate.coll13;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UseQueue {

    public static void main(String[] args) {
        //高性能、无阻塞、无界队列
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        concurrentLinkedQueue.add("a");
        concurrentLinkedQueue.offer("b");
        concurrentLinkedQueue.add("c");
        concurrentLinkedQueue.offer("d");
        concurrentLinkedQueue.add("e");

        System.out.println(concurrentLinkedQueue.size());
        System.out.println("从头部取元素，并删除元素" + concurrentLinkedQueue.poll());
        System.out.println(concurrentLinkedQueue.size());
        System.out.println("从头部取元素，不删除元素" + concurrentLinkedQueue.peek());
        System.out.println(concurrentLinkedQueue.size());

        System.out.println();
        System.out.println();

        /*****************************************************************************************/

        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(5);
        arrayBlockingQueue.add("a");
        try {
            arrayBlockingQueue.put("b");
            arrayBlockingQueue.put("c");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrayBlockingQueue.add("d");
        arrayBlockingQueue.add("e");
//        arrayBlockingQueue.add("f");

        try {
            System.out.println(arrayBlockingQueue.offer("a",3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        /*****************************************************************************************/


        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        linkedBlockingQueue.offer("a");
        linkedBlockingQueue.offer("b");
        linkedBlockingQueue.add("c");
        linkedBlockingQueue.add("d");
        linkedBlockingQueue.add("e");
        linkedBlockingQueue.add("f");

        System.out.println(linkedBlockingQueue.size());

        for(String data : linkedBlockingQueue){
            System.out.println(data);
        }

        List<String> list = new ArrayList<>();

        int i = linkedBlockingQueue.drainTo(list, 3);
        System.out.println("获取到的可用数据" +  i);

        for(String data : list){
            System.out.println("list 获取到的数据 -> " + data);
        }

        System.out.println();
        System.out.println();



        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String take = synchronousQueue.take();
                    System.out.println(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronousQueue.add("wty");
                synchronousQueue.offer("wty00001");
            }
        },"t2");

        t2.start();

        System.out.println();
        System.out.println();

    }

}
