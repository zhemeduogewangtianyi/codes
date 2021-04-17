package com.wty.base.sync006;

public class ObjectLock {

    public void method1(){
        synchronized (this){
            System.out.println("method 1");
        }
    }

    public void method2(){
        synchronized (ObjectLock.class){
            System.out.println("method 2");
        }
    }

    private Object o1 = new Object();

    public void method3(){
        synchronized (o1){
            System.out.println("method 3");
        }
    }

    public static void main(String[] args) {
        ObjectLock lock = new ObjectLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method1();
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method2();
            }
        },"t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method3();
            }
        },"t3");

        t1.start();
        t2.start();
        t3.start();
    }

}
