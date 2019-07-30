package com.service.impl;

import com.service.ThreadPoolTaskService;
import com.singleton.DateSingleton;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 线程池测试类
 */
@Service
public class ThreadPoolTaskServiceImpl implements ThreadPoolTaskService {

	/**
	 * 多线程执行任务
	 */
	@Async("taskExecutor")
	public void testThreadPool(int i) throws Exception {

		System.out.println(DateSingleton.getDateformat().format(new Date(System.currentTimeMillis())) + " : "
				+ Thread.currentThread().getName() + "开始执行任务" + i);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(DateSingleton.getDateformat().format(new Date(System.currentTimeMillis())) + " : "
				+ Thread.currentThread().getName() + "执行结束任务" + i);
	}
}
