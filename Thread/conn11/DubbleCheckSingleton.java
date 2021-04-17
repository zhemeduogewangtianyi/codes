package com.wty.base.conn11;

import java.util.ArrayList;
import java.util.List;

public class DubbleCheckSingleton {

    private static DubbleCheckSingleton dubbleCheckSingleton = null;

    private DubbleCheckSingleton(){

    }

    public static DubbleCheckSingleton instance(){
        if(dubbleCheckSingleton == null){
            synchronized (DubbleCheckSingleton.class){
                if(dubbleCheckSingleton == null){
                    dubbleCheckSingleton =  new DubbleCheckSingleton();
                }
            }
        }
        return dubbleCheckSingleton;
    }

    public static void main(String[] args) {

        List<Thread> list = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    DubbleCheckSingleton dubbleCheckSingleton = DubbleCheckSingleton.instance();
                    System.out.println(Thread.currentThread().getName() + "  " +dubbleCheckSingleton);
                }
            },"t" + i);
            list.add(t);
        }

        for(Thread t : list){
            t.start();
        }
    }

}
