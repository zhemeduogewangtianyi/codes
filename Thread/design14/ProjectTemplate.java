package com.wty.intermediate.design14;

public class ProjectTemplate {

    public String queryInfo(String request){
        try {
            System.out.println("开始查询模板信息-----");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("查询模板信息-----结束");
        return "模板数据";
    }

}
