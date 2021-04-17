package com.wty.senior.concurrent17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UseExecutors {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        ExecutorService service1 = Executors.newSingleThreadExecutor();

    }

}
