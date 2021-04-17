package com.wty.base.sync006;

public class StringLock {

    public void method(){
        synchronized (new String("lock")){
            while(true){
                System.out.println("method " + Thread.currentThread().getName() + " 开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("method " + Thread.currentThread().getName() + " 结束");
            }
        }
    }

    public static void main(String[] args) {
        final StringLock stringLock = new StringLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        }, "t2");

        t1.start();
        t2.start();
    }


}
