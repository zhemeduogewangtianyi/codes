package com.wty.base.sync02;

public class MultiThread {

    private static int num = 0;

    /** static 变成类锁 */
    public static synchronized void printNum(String tag){

        if(tag.equals("a")){
            num = 100;
            System.out.println(Thread.currentThread().getName() + " " + tag +"赋值成功");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            num = 200;
            System.out.println(Thread.currentThread().getName() + " " + tag +"赋值成功");
        }

        System.out.println(tag + " -> " + num + " -> " + Thread.currentThread().getName());

    }

    public static void main(String[] args) {
        MultiThread m1 = new MultiThread();
        MultiThread m2 = new MultiThread();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m1.printNum("a");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m2.printNum("b");
            }
        });

        t1.start();
        t2.start();
    }
}
