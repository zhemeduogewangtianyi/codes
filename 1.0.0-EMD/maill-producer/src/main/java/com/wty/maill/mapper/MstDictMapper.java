package com.wty.maill.mapper;

import com.wty.maill.entity.MstDict;

import java.util.List;

public interface MstDictMapper {

    List<MstDict> findByStatus(String status);

    int deleteByPrimaryKey(String id);

    int insert(MstDict record);

    int insertSelective(MstDict record);

    MstDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MstDict record);

    int updateByPrimaryKey(MstDict record);
}