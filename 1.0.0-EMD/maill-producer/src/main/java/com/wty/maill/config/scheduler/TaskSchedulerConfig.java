package com.wty.maill.config.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class TaskSchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskScheduling());
    }

    @Bean
    public Executor taskScheduling(){
//        return Executors.newScheduledThreadPool(5)
//                .scheduleAtFixedRate("自己的线程","开机后5秒钟执行 5","每隔3秒执行 3", TimeUnit.SECONDS);
        return Executors.newScheduledThreadPool(5);
    }

}
