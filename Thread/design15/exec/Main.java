package com.wty.intermediate.design15.exec;

import com.wty.intermediate.design15.exec.controller.ProjectInfoController;
import com.wty.intermediate.design15.exec.entity.RequestParam;
import com.wty.intermediate.design15.exec.service.BaseService;
import com.wty.intermediate.design15.exec.service.impl.ProjectInfoServiceImpl;
import com.wty.intermediate.design15.exec.service.impl.ProjectIntergrationServiceImpl;
import com.wty.intermediate.design15.exec.service.impl.ProjectLocationInfoServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    private static ProjectInfoController controller = new ProjectInfoController();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        RequestParam param = new RequestParam();
        param.setId(1L);
        param.setName("项目名称");

        Map map = controller.queryInfo(param);
        System.out.println(map.toString());
        System.out.println("总耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "秒");

    }

}
