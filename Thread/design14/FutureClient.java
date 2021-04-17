package com.wty.intermediate.design14;

public class FutureClient{

    private BaseProject baseProject = new BaseProject();

    private ProjectTemplate template = new ProjectTemplate();

    public FutureData queryProjectInfo(final String request){

        FutureData data = new FutureData();

        Thread projectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = baseProject.queryInfo(request);
                data.setRealData(response);
            }
        },"projectThread");

        projectThread.start();

        Thread templateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = template.queryInfo(request);
                data.setRealData(response);
            }
        },"templateThread");

        templateThread.start();
        return data;
    }

}
