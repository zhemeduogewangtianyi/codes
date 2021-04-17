package com.wty.intermediate.coll12;

import java.util.*;

public class Tickets {

    public static void main(String[] args){

        final Vector<String> tickets = new Vector<>();

        Map<String, Object> stringObjectMap = Collections.synchronizedMap(new HashMap<String, Object>());

        for(int i = 0 ; i < 1000 ; i++){
            tickets.add("火车票 " + i);
            stringObjectMap.put(i+"","火车票" + i);
        }



        List<Thread> ts = new ArrayList<>();
        for(int i = 0 ; i < 10 ;i++){
            int finalI = i;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String remove = tickets.remove(0);
                    Object remove1 = stringObjectMap.remove(finalI +"");
                    System.out.println(Thread.currentThread().getName() + " vector " + remove);
                    System.out.println(Thread.currentThread().getName() + " map " + remove1);
                }
            },"t" + i);
            ts.add(t);
        }

        for(Thread t : ts){
            t.start();
        }
    }

}
