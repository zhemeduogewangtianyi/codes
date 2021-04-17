package com.wty.intermediate.design15;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable{

    private ConcurrentLinkedQueue<Task> workQueue;
    private ConcurrentHashMap<String,Object> resultMap;

    public void setConcurrentLinkedQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while(true){
            Task poll = workQueue.poll();
            if(poll == null){
                break;
            }
            Object res = handler(poll);
            this.resultMap.put(poll.getId().toString(),res);
        }
    }

    public Object handler(Task task){
        Object res = null;
        try {
            Thread.sleep(500);
            res = task.getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

}
