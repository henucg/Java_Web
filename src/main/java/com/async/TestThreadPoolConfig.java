package com.async;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@EnableAsync
public class TestThreadPoolConfig {

	@Bean
	public TaskExecutor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() ;
		// 设置核心线程数
		executor.setCorePoolSize(3);
		// 设置最大线程数
		executor.setMaxPoolSize(10);
		// 设置队列容量
		executor.setQueueCapacity(3);
		// 设置活跃时间（秒）
		executor.setKeepAliveSeconds(5);
		// 设置拒绝策略（丢弃不处理）
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		// 设置线程名称前缀
		executor.setThreadNamePrefix("Thread-");
		// 所有任务结束后关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);

		return executor ;
	}

}
