package com.wty.intermediate.design15.exec.service.impl;

import com.wty.intermediate.design15.exec.entity.RequestParam;
import com.wty.intermediate.design15.exec.service.BaseService;

public class ProjectLocationInfoServiceImpl implements BaseService<RequestParam,String> {
    @Override
    public boolean validation(Object param) throws Exception {
        if(param instanceof RequestParam){
            boolean b = ((RequestParam) param).getId() == null;
            return !b;
        }
        return false;
    }

    @Override
    public String execute(RequestParam param) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("ProjectLocationInfoServiceImpl 【项目地理位置】 接收到参数 -> " + param.toString());
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("【项目地理位置】 耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return "浙江省,杭州市";
    }
}
