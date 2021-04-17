package com.wty.base.conn09;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyQueue<T> {

    private final int minSize = 0;
    private final int maxSize;

    private AtomicInteger index = new AtomicInteger(0);

    private LinkedList<T> queue = new LinkedList<>();

    public MyQueue(int size){
        this.maxSize = size;
    }

    public synchronized void push(T t){
        if(index.get() == this.maxSize){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        System.err.println("放入元素 -> " + t.toString());
        queue.add(t);
        index.incrementAndGet();

    }

    public synchronized T pop(){
        if(index.get() == this.minSize){
            try{
                this.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        this.notify();
        index.decrementAndGet();
        T t = queue.pollFirst();

        return t;
    }

    public static void main(String[] args) {
        MyQueue<Character> queue = new MyQueue<>(5);
        queue.push((char) 65);
        queue.push((char) 66);
        queue.push((char) 67);
        queue.push((char) 68);
        queue.push((char) 69);

        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run(){
                queue.push((char) 70);
                queue.push((char) 71);
            }
        },"t1");

        t1.start();

        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run(){
                Character pop = queue.pop();
                System.out.println("pop -> " + pop);
                Character pop1 = queue.pop();
                System.out.println("pop1 -> " + pop1);
            }
        },"t2");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("t2 启动了");
        t2.start();

    }

}
