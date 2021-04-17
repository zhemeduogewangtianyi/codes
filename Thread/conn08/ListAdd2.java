package com.wty.base.conn08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ListAdd2 {

    private List<String> list = new ArrayList<>();

    private List<Integer> errorLog = new ArrayList<>();

    public void setErrorLog(Integer index){
        errorLog.add(index);
    }

    public void add(){
        list.add("wty");
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {

        ListAdd2 listAdd2 = new ListAdd2();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Object o1 = new Object();
        Object o2 = new Object();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o1){
                    try{
                        for(int i = 0 ; i < 10 ; i++){
                            try {
                                Thread.sleep(500);
                                listAdd2.add();
                                System.err.println(Thread.currentThread().getName() + " 添加了一个元素" + i);
                                if(listAdd2.size() == 5){
                                    synchronized (o2){
                                        o2.notify();
                                    }
                                    o1.wait();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        countDownLatch.countDown();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o2){
                    try{
                        o2.wait();
                        System.out.println(Thread.currentThread().getName() + " 接收到 list size = " + listAdd2.size());
                        throw new RuntimeException();
                    }catch(Exception e){
                        e.printStackTrace();
                        listAdd2.setErrorLog(listAdd2.size() - 1);
                    }finally {
                        synchronized (o1){
                            o1.notify();
                        }
                    }
                }
            }

        },"t2");

        t1.start();
        t2.start();
        countDownLatch.await();
        System.out.println(listAdd2.size());
        System.out.println(listAdd2.errorLog.size());
    }

}
