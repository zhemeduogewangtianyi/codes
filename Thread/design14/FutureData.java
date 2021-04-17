package com.wty.intermediate.design14;

import java.util.HashMap;
import java.util.Map;

public class FutureData {

    private int count;

    private Map response = new HashMap();

    private boolean isReady = false;

    public synchronized void setRealData(String response){

        while(isReady){
            isReady = !isReady;
        }
        this.response.put(response,response);
        isReady = true;
        count++;
        if(count == 2){
            this.notify();
        }

    }

    public synchronized Map getProjectInfo() {
        while (!isReady){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
