package com.wty.intermediate.coll12;

import java.util.ArrayList;
import java.util.List;

public class CMETest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            list.add(i);
        }

        for(Integer i : list){
            if(i == 2){
                list.remove(i);
            }
        }
    }

}
