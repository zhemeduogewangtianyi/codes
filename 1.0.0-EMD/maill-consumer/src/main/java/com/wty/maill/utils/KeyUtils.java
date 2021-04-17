package com.wty.maill.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 系统名称：通用平台
 * 模块名称：通用平台-公共服务
 * 中文类名：KeyUtils
 * 概要说明：逐渐生成策略-工具类
 * */
public class KeyUtils {

    /** 生成主键 */
    public static String generatorUUID(){
        TimeBasedGenerator timeBasedGenerator =
                Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        return timeBasedGenerator.generate().toString();
    }

}
