package com.wty.intermediate.design15;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Master master = new Master(new Worker(),20);

        for(int i = 0 ; i < 100 ; i++){
            Task task = new Task();
            task.setId(i);
            task.setPrice(i);
            master.submit(task);
        }
        master.execute();

        while(true){
            if(master.isComplete()){
                int result = master.getResult();
                System.out.println(result);
                break;
            }
        }
        System.out.println((System.currentTimeMillis() - startTime) / 1000);
    }

}
