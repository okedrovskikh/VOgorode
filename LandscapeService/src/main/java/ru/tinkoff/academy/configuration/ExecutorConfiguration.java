package ru.tinkoff.academy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ExecutorConfiguration {
    @Bean
    public AsyncConfigurer asyncConfigurer() {
        return new LandscapeAsyncConfigurer(Executors.newFixedThreadPool(2));
    }

    private record LandscapeAsyncConfigurer(Executor executor) implements AsyncConfigurer {
        @Override
        public Executor getAsyncExecutor() {
            return executor;
        }
    }
}
