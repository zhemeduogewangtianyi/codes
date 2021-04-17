package com.wty.intermediate.design15.exec.controller;

import com.wty.intermediate.design15.exec.Master;
import com.wty.intermediate.design15.exec.Worker;
import com.wty.intermediate.design15.exec.entity.RequestParam;
import com.wty.intermediate.design15.exec.service.BaseService;
import com.wty.intermediate.design15.exec.service.impl.ErrorServiceImpl;
import com.wty.intermediate.design15.exec.service.impl.ProjectInfoServiceImpl;
import com.wty.intermediate.design15.exec.service.impl.ProjectIntergrationServiceImpl;
import com.wty.intermediate.design15.exec.service.impl.ProjectLocationInfoServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProjectInfoController {

    private static List<BaseService> list = Arrays.asList(new BaseService[]{
            new ProjectInfoServiceImpl(),new ProjectLocationInfoServiceImpl(),new ProjectIntergrationServiceImpl(),new ErrorServiceImpl()
    });

    public Map queryInfo(RequestParam param){

        Master master = new Master(new Worker(),5,param);

        for(BaseService service : list){
            master.submit(service);
        }
        master.execute();

        Map result = null;
        while(true){
            if(master.isComplete()){
                result = master.getResult();
                break;
            }
        }
        return result;
    }

}
