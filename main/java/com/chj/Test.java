package com.chj;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
//        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(()->{
//            System.out.println(111);
//        }, 3,1, TimeUnit.SECONDS);
//        scheduledExecutorService.shutdown();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            System.out.println(111);
        }, 3, 1, TimeUnit.SECONDS);
//        scheduledExecutorService.shutdown();
    }
}
