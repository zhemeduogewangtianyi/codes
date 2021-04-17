package com.wty.base.sync04;

import java.util.concurrent.CountDownLatch;

public class DirtyRead {

    private String username;
    private String password;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public synchronized void setValue(String username,String password){
        this.username = username;
        this.password = password;
        System.err.println("set方法 ->>>> " + this.username + "  " + password + " " + Thread.currentThread().getName());
        countDownLatch.countDown();
    }

    public void getValue(){
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(this.username + "  " + password + " " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        DirtyRead dirtyRead = new DirtyRead();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                dirtyRead.setValue("wty","123123");
            }
        },"t1");

        t1.start();

        for(int i = 0 ; i < 30 ; i++){
            dirtyRead.getValue();
        }
    }

}
