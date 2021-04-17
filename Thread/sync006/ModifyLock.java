package com.wty.base.sync006;

public class ModifyLock {

    private String name;
    private int age;

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public synchronized void method(String name,int age){
        System.out.println("当前线程： " + Thread.currentThread().getName() + "开始");
        this.setName(name);
        this.setAge(age);
        System.out.println("修改线程 " + Thread.currentThread().getName() + " 为" + this.getName() + " " + this.getAge());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程： " + Thread.currentThread().getName() + "结束");
    }

    public static void main(String[] args) {
        ModifyLock lock = new ModifyLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method("张三",12);
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.method("李四",20);
            }
        },"t2");

        t1.start();
        t2.start();

    }
}
