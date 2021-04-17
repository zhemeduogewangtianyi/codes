package com.wty.intermediate.design15.exec;

import com.wty.intermediate.design15.exec.service.BaseService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable{

    private ConcurrentLinkedQueue<BaseService> workerQueue;

    private ConcurrentHashMap<String,Object> resultMap;

    /** 入参 */
    private Object param;

    /** 让Worker 感知到 master */
    private Master master;

    public void setWorkerQueue(ConcurrentLinkedQueue<BaseService> workerQueue) {
        this.workerQueue = workerQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public void setMaster(Master master){
        this.master = master;
    }

    @Override
    public void run() {
        while(true){
            BaseService service = workerQueue.poll();
            if(service == null){
                break;
            }
            Object handler = handler(service);
            if(handler == null){
                this.resultMap.put(service.getClass().getSimpleName(),service.getClass().getSimpleName() + " 查询失败！");
//                master.erroCount();
                continue;
            }
            this.resultMap.put(service.getClass().getSimpleName(),handler);
        }
    }

    public Object handler(BaseService service){
        Object res = null;
        try{
            if(service.validation(param)){
                res = service.execute(param);
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return res;
    }
}
