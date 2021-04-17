package com.wty.base.conn09;

public class MyStack {

    private char[] stack = null;

    private int index = 0;

    MyStack(int size){
        stack = new char[size];
    }

    public synchronized void put(char i){
            if(index == stack.length){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.notify();
            stack[index] = i;
            index++;
    }

    public synchronized char pop(){
            if(index == 0){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.notify();
            index--;
            return stack[index];
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack(10);
        for(int i = 65 ; i < 75 ; i++){
            char finalI = (char) i;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    stack.put(finalI);
                }
            },"t"+i);
            t1.start();
        }

        for(int i = 0 ; i < 10 ; i++){
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    char tack = stack.pop();
                    System.out.println(tack);
                }
            },"t"+i);
            t2.start();
        }
    }

}
