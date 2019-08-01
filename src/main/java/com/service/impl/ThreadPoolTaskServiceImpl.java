package com.service.impl;

import com.service.ThreadPoolTaskService;
import com.singleton.DateSingleton;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * 线程池测试类
 */
@Service
public class ThreadPoolTaskServiceImpl implements ThreadPoolTaskService {

	/**
	 * 多线程执行任务
	 */
	@Async("taskExecutor")
	@Override
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

	@Override
	public String testRunThread() throws Exception{
		/**
		 * 每次新建一个定长线程池
		 */
		ExecutorService executor = Executors.newFixedThreadPool(5) ;

		StringBuilder sb = new StringBuilder("") ;

		CountDownLatch count = new CountDownLatch(1000) ;

		for(int i=0;i<1000;i++){
			try {
				executor.execute(new Runnable(){
					@Override
					public void run() {
						sb.append("a");
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				count.countDown();
			}
		}

		count.await();

		//线程全部执行完关闭线程池
		executor.shutdown();


		return sb.length()+"" ;
	}
}
