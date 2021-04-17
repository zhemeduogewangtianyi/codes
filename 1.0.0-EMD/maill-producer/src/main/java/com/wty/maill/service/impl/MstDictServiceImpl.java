package com.wty.maill.service.impl;

import com.wty.maill.annotation.DataSourceCondition;
import com.wty.maill.entity.MstDict;
import com.wty.maill.enumeration.DataSourceType;
import com.wty.maill.mapper.MstDictMapper;
import com.wty.maill.service.MstDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MstDictServiceImpl implements MstDictService {

    @Resource
    private MstDictMapper mstDictMapper;

    @Override
    @DataSourceCondition(DataSourceType.SLAVE)
    public List<MstDict> findByStatus(String status) throws Exception{
        List<MstDict> byStatus = mstDictMapper.findByStatus(status);
        return byStatus;
    }
}
