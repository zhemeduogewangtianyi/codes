package com.wty.base.sync07;

public class RunThread extends Thread {

    private volatile boolean isOk = true;

    public void setIsOk(boolean isok){
        this.isOk = isok;
    }

    @Override
    public void run(){
        System.out.println("进入run方法");
        while(isOk){
//            System.out.println(isOk);
            boolean a = isOk;
        }
        System.out.println("线程停止");
    }

    public static void main(String[] args) throws InterruptedException {
        RunThread t = new RunThread();
        t.start();
        Thread.sleep(3000);
        t.setIsOk(false);
        System.out.println("isOk 被 设置成了 false");
        Thread.sleep(1000);
        System.out.println(t.isOk);
    }

}
