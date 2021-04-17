package com.company.lock_04;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/** 自旋锁 */
@SuppressWarnings("Convert2Lambda")
public class SpinLockDemo03 {

    //原子引用线程
    private volatile AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {

        SpinLockDemo03 lock = new SpinLockDemo03();


        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("a");
                    for(int i = 0 ; i < 5 ; i++){
                        System.out.println(i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                lock.unlock();
                }
            },"AA").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                lock.unlock();
            }
        },"BB").start();

    }

    private void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in lock");
        while(!atomicReference.compareAndSet(null,thread)){
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName() + " unlock");
    }

}

