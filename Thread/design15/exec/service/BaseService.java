package com.wty.intermediate.design15.exec.service;

public interface BaseService<T,R> {

    boolean validation(Object t) throws Exception;

    R execute(T t) throws Exception;

}
