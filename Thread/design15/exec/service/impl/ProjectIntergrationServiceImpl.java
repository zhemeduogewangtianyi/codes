package com.wty.intermediate.design15.exec.service.impl;

import com.wty.intermediate.design15.exec.entity.RequestParam;
import com.wty.intermediate.design15.exec.service.BaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectIntergrationServiceImpl implements BaseService<RequestParam, List<String>> {
    @Override
    public boolean validation(Object param) throws Exception {
        if(param instanceof RequestParam){
            boolean b = ((RequestParam) param).getId() == null;
            return !b;
        }
        return false;
    }

    @Override
    public List<String> execute(RequestParam param) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("ProjectIntergrationServiceImpl 【项目集成信息】 接收到参数 -> " + param.toString());
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        List<String> res = new ArrayList<>();
        Collections.addAll(res,"集成方案名称","集成人","集成时间");
        System.out.println("【项目集成信息】 耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return res;
    }
}
