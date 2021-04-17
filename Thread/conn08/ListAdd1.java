package com.wty.base.conn08;

import java.util.ArrayList;
import java.util.List;

public class ListAdd1 {

    private static volatile List<String> list = new ArrayList<>();

    public void add(){
        list.add("wty");
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {

        ListAdd1 add1 = new ListAdd1();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0 ; i < 10 ; i++){
                        Thread.sleep(500);
                        add1.add();
                        System.out.println(Thread.currentThread().getName() + " 添加了一个元素 " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(add1.size() == 5){
                        System.out.println(Thread.currentThread().getName() + " 接收到 size = 5");
                        throw new RuntimeException();
                    }
                }
            }
        },"t2");

        t1.start();
        t2.start();

    }

}
