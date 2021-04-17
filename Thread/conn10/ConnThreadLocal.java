package com.wty.base.conn10;

public class ConnThreadLocal<T> {

    public ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public void setThreadLocal(T t){
        threadLocal.set(t);
    }

    public T getThreadLocal(){
        return threadLocal.get();
    }

    public void clear(){
        threadLocal.remove();
    }

    public static void main(String[] args) {
        ConnThreadLocal<String> local = new ConnThreadLocal<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                local.setThreadLocal("wty");
                String threadLocal = local.getThreadLocal();
                System.out.println(Thread.currentThread().getName() + " " + threadLocal);
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                local.setThreadLocal("我同意");
                String threadLocal = local.getThreadLocal();
                System.out.println(Thread.currentThread().getName() + " " + threadLocal);
            }
        },"t2");

        t1.start();
        t2.start();
    }

}
