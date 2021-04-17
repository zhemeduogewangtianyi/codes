package com.wty.intermediate.coll13;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UseConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();
        map.put("k1","v1");
        map.put("k2","v2");
        map.put("k3","v3");
        map.putIfAbsent("k3","v4");

        for (Iterator<Map.Entry<String,Object>> car = map.entrySet().iterator() ; car.hasNext() ; ){
            Map.Entry<String, Object> next = car.next();
            String key = next.getKey();
            Object value = next.getValue();
            System.out.println("key " + key + " value " + value);
        }
    }

}
