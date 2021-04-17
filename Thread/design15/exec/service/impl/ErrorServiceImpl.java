package com.wty.intermediate.design15.exec.service.impl;

import com.wty.intermediate.design15.exec.service.BaseService;

public class ErrorServiceImpl implements BaseService<String,String> {
    @Override
    public boolean validation(Object t) {
        if(t instanceof String){
            return true;
        }
        return false;
    }

    @Override
    public String execute(String s) {
        System.err.println("执行了没有");
        return null;
    }
}
