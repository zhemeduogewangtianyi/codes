package com.company.lock_04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1 {

    volatile int n = 0 ;

    public void add(){
        n++;
    }

    public static void main(String[] args) {

        //默认非公平锁，构造方法 boolean fair true公平锁 false非公平锁
        Lock lock = new ReentrantLock();

    }

}
