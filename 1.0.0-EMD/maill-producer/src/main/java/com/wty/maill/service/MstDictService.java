package com.wty.maill.service;


import com.wty.maill.entity.MstDict;

import java.util.List;

public interface MstDictService {

    public List<MstDict> findByStatus(String status) throws Exception;

}
