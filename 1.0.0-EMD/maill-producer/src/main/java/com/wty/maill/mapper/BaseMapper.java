package com.wty.maill.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础数据库服务
 * */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
