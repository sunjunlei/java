package com.example.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sjl
 */
@Configuration
public class ExecutorsConfig {
    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(100);
        ThreadPoolExecutor threadPoolExecutor=
                new ThreadPoolExecutor(50,
                        100,
                        50,
                        TimeUnit.SECONDS,
                        blockingQueue);
        return threadPoolExecutor;
    }
}
