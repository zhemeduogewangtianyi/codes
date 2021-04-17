package com.wty.intermediate.design15;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

    /** 一个盛放任务的Queue */
    private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<>();

    /** 盛放work的集合 */
    private HashMap<String,Thread> workers = new HashMap<>();

    /** 盛放worker返回的结果集 */
    private ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();

    /** 构造方法 */
    public Master(Worker worker,int count){
        worker.setConcurrentLinkedQueue(workQueue);
        worker.setResultMap(resultMap);

        for(int i = 0 ; i < count ; i++){
            this.workers.put(i+"" , new Thread(worker));
        }
    }

    /** 任务提交 */
    public void submit(Task task){
        this.workQueue.add(task);
    }

    /** 执行方法，启动worker */
    public void execute(){
        for(Map.Entry<String,Thread> me : workers.entrySet()){
            me.getValue().start();
        }
    }

    /** 判断是否运行结束 */
    public boolean isComplete(){
        for(Map.Entry<String,Thread> me : workers.entrySet()){
            if(me.getValue().getState() == Thread.State.TERMINATED){
                return true;
            }
        }
        return false;
    }

    /** 计算结果方法 */
    public int getResult(){
        int res = 0;
        for(Map.Entry<String,Object> me : resultMap.entrySet()){
            res += (Integer)me.getValue();
        }
        return res;
    }

}
