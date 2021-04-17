package com.wty.base.sync05;

public class SyncDubbo02 {

    static class Main{
        public int i = 10;
        public synchronized void sup(){
            i--;
            System.out.println("Main - > " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Sub extends Main{
        public synchronized void sub(){
            while(i > 0){
                i--;
                System.out.println("Sub - > " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.sup();
            }
        }
    }

    public static void main(String[] args) {
        SyncDubbo02.Sub sub = new Sub();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sub.sub();
            }
        },"t1");

        t1.start();
    }

}
