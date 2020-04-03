package com.ipanel.web.app.cv.comfig;

import com.ipanel.web.app.cv.global.MyException;
import com.ipanel.web.app.cv.global.MyException2;
import com.ipanel.web.app.cv.global.response.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhaolei
 * Create: 2018/12/19 09:15
 * Modified By:
 * Description: 线程池配置
 */
@Configuration
public class ExecutorConfig implements AsyncConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);
    private static final int corePoolSize = 2;// 核心线程数
    private static final int maxPoolSize = 5;// 最大线程数
    private static final int queueCapacity = 10;//队列容量
    private static final int awaitTerminationSeconds = 60;//设置设置等待终止秒数
    private static final int keepAliveSeconds = 60;//设置保持活力秒数
    private static final String threadNamePrefix = "hello-";//线程名

    @Bean
    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 设置拒绝策略
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        //jvm中增加一个关闭的钩子,在jvm关闭时开启一个一步线程关闭线程池。
        //  Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            asyncHandlerException(throwable, method);
        }

        private void asyncHandlerException(Throwable throwable, Method method) {
            if (throwable instanceof MyException) {
                logger.error(method + " occurs in async thread :", throwable);
            } else if (throwable instanceof MyException2) {
                logger.error(method + " occurs in async thread :", throwable);
            } else {
                //异步线程未知异常，告知处理
                logger.error(method + " Unknown exception occurs in async thread :", throwable);

            }
        }
    }

}
