package mart.mono.commerce;

import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextScheduledExecutorService;
import io.micrometer.context.ContextSnapshot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

@SpringBootApplication
@EnableAsync
public class CommerceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceAppApplication.class, args);
    }

    @Bean(name = "taskExecutor", destroyMethod = "shutdown")
    ThreadPoolTaskScheduler threadPoolTaskScheduler(TaskScheduler taskScheduler) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler() {
            @Override
            protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
                ExecutorService executorService = super.initializeExecutor(threadFactory, rejectedExecutionHandler);
                return ContextExecutorService.wrap(executorService, ContextSnapshot::captureAll);
            }
            @Override
            public ScheduledExecutorService getScheduledExecutor() throws IllegalStateException {
                return ContextScheduledExecutorService.wrap(super.getScheduledExecutor(), ContextSnapshot::captureAll);
            }
        };
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
