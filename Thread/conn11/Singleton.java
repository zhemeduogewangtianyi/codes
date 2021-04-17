package com.wty.base.conn11;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    private static Singleton singleton = new Singleton();

    public static Singleton getInstance(){
        return singleton;
    }

    public static void main(String[] args) {

        List<Thread> list = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Singleton singleton = Singleton.getInstance();
                    System.out.println(Thread.currentThread().getName() + "  " +singleton);
                }
            },"t" + i);
            list.add(t);
        }

        for(Thread t : list){
            t.start();
        }
    }

}
