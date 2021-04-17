package com.wty.intermediate.design15.exec.service.impl;

import com.wty.intermediate.design15.exec.entity.RequestParam;
import com.wty.intermediate.design15.exec.service.BaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectInfoServiceImpl implements BaseService<RequestParam, List<String>> {

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
        System.out.println("ProjectInfoServiceImpl 【项目详情】 接收到参数 -> " + param.toString());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1 / 0;
        List<String> res = new ArrayList<>();
        Collections.addAll(res,"项目名称","项目负责人","项目状态");
        System.out.println("【项目详情】 耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return res;
    }
}
