package com.wty.intermediate.design14;

public class BaseProject{


    public String queryInfo(String request){
        try {
            System.out.println("开始查询项目详情。。。很耗时。。");
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("查询项目详情。。。完毕。。");
        return "项目数据";
    }


}
