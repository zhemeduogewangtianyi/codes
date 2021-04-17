package com.wty.intermediate.design15.exec;

import com.wty.intermediate.design15.exec.service.BaseService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

    /** 任务容器 */
    private ConcurrentLinkedQueue<BaseService> workerQueue = new ConcurrentLinkedQueue<>();

    /** 线程容器 */
    private HashMap<String,Thread> workers = new HashMap<>();

    /** 结果集 */
    private ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<>();

    /** 任务计数器 */
    private int countdown;

    /** 入参 */
    private Object param;

    /** 存在异常任务，countdown - 1 */
    public void erroCount(){
        countdown--;
    }

    /** 构造任务 */
    public Master(Worker worker,int workCount,Object param){
        worker.setWorkerQueue(workerQueue);
        worker.setResultMap(resultMap);
        this.param = param;
        worker.setParam(this.param);
        worker.setMaster(this);

        for(int i = 0 ; i < workCount ; i ++){
            workers.put(i+"",new Thread(worker));
        }
    }

    /** 任务提交 */
    public void submit(BaseService baseService){
        try{
            if(baseService.validation(param)){
                this.workerQueue.add(baseService);
                countdown++;
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

    }

    /** 开启任务 */
    public void execute(){
        for(Map.Entry<String,Thread> me : workers.entrySet()){
            me.getValue().start();
        }
    }

    /** 判断任务是否执行完毕 */
    public boolean isComplete(){
        boolean isComplete = false;
        for(Map.Entry<String,Thread> me : workers.entrySet()){
            if(me.getValue().getState() == Thread.State.TERMINATED && countdown == resultMap.size()){
                isComplete = true;
            }
        }
        return isComplete;
    }

    /** 获取结果集 */
    public Map getResult(){
        return this.resultMap;
    }

}
