package com.wty.base.sync07;



import sun.misc.Launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicUse {

    private static AtomicInteger ai = new AtomicInteger(0);

    private static AtomicInteger count = new AtomicInteger(0);

    private ReentrantLock lock = new ReentrantLock();

    public int countAdd(){
        try {
            lock.lock();
            Thread.sleep(100);
            count.addAndGet(1);
            count.addAndGet(2);
            count.addAndGet(3);
            count.addAndGet(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return count.get();
    }

    public synchronized int multiAdd(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ai.addAndGet(1);
        ai.addAndGet(2);
        ai.addAndGet(3);
        ai.addAndGet(4);
        return ai.get();
    }

    public static void main(String[] args) {
        final AtomicUse use = new AtomicUse();
        List<Thread> ts = new ArrayList<>();
        for(int i = 0 ; i < 100 ; i++){
            ts.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    int i1 = use.multiAdd();
                    int i2 = use.countAdd();
                    System.out.println(i1 + "    " + i2);
                }
            },i+""));
        }

        for(Thread t : ts){
            t.start();
        }
    }

}
