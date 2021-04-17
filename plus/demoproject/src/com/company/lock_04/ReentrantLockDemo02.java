package com.company.lock_04;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 11	 invoked send SMS
 * 11	 *****invoked send Mail
 * 12	 *****invoked send Mail
 * 13	 invoked send SMS
 * 13	 *****invoked send Mail
 * */
public class ReentrantLockDemo02 {

    public static void main(String[] args) {

        Phone phone = new Phone();

//        new Thread(phone::sendSms,"t1").start();
//
//        new Thread(phone::sendMail,"t2").start();
//
//        new Thread(phone::sendSms,"t3").start();

        Thread t4 = new Thread(phone,"t4");
        Thread t5 = new Thread(phone,"t5");
        t4.start();
        t5.start();

    }

}

class Phone implements Runnable{

    public synchronized void sendSms(){
        System.out.println(Thread.currentThread().getId() +"\t invoked send SMS");
        sendMail();
    }

    public synchronized void sendMail(){
        System.out.println(Thread.currentThread().getId() +"\t *****invoked send Mail");
    }

    /*************************************************/
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        //lock.lock();
        try{
            System.out.println(Thread.currentThread().getId() +"\t invoked get()");
            set();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getId() +"\t ##############invoked set()");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
