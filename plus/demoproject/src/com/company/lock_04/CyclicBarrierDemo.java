package com.company.lock_04;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@SuppressWarnings("Convert2Lambda")
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "  **************");
            }
        });

        for(int i = 0 ; i < 7 ; i++){
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "  " + temp);
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
