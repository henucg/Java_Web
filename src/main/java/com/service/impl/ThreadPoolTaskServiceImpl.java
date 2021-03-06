package com.service.impl;

import com.service.ThreadPoolTaskService;
import com.singleton.DateSingleton;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
	public String testRunThread(int size) throws Exception{
		long beginTime = System.currentTimeMillis();

		/**
		 * 每次新建一个定长线程池
		 */
		ExecutorService executor = Executors.newFixedThreadPool(5) ;
		/**
		 * 原子类，用来计数，同时记录正确执行与错误执行的数量
		 * 然后后面可以针对做相应处理，例如数据回滚之类的
		 * 也可以将所有的Future返回值保存在一个List当中
		 * 等线程执行完毕，便利List，如果有异常，回滚当前操作
		 */
		AtomicInteger successNum = new AtomicInteger(0) ;
		AtomicInteger errorNum = new AtomicInteger(0) ;
		/**
		 * 记录执行的线程数
		 */
		CountDownLatch count = new CountDownLatch(size) ;

		/**
		 * 使用Callable创建线程，又返回值，且可以捕获异常
		 */
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				try {
					Thread.sleep(10);

					if (count.getCount() == 500) {
						throw new Exception("=================抛出异常==================");
					}

					successNum.getAndIncrement() ;

					return 0 ;
				} catch (Exception e) {
					errorNum.getAndIncrement() ;
					throw e ;
				} finally {
					// 线程每执行完一个，数量-1
					count.countDown();
				}
			}
		};

		// 创建1000个任务提交到线程池
		for(int i=0;i<size;i++) {
			try {
				//executor.submit(callable);
				Future<Integer> future = executor.submit(callable);
				/**
				 * get()方法会阻塞主程序，只有结果返回才会继续执行后续操作
				 * 所以不是多线程同时运行，线程池失去意义
				 * 所以不在此处获取返回值，而是后期处理
				 */
//				Integer result = future.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 如果还有线程没有执行完，主线程等待
		count.await();

		// 线程全部执行完关闭线程池
		executor.shutdown();

		return getString(beginTime, successNum, errorNum);
	}

	@Override
	public String testRunThread2(int size) throws Exception {
		long beginTime = System.currentTimeMillis() ;

		// 成功个数
		AtomicInteger successNum = new AtomicInteger(0) ;
		// 失败个数
		AtomicInteger errorNum = new AtomicInteger(0) ;
		// 保证子线程完成才执行主线程
		CountDownLatch countDownLatch = new CountDownLatch(size) ;

		ExecutorService threadPool = Executors.newFixedThreadPool(5) ;

		/**
		 * CompletionService用来保存线程池中每个线程执行的结果
		 * 获取结果用CompletionService.take().get()
		 * 这个方法不是同步的，而是异步的，那个线程先执行完就先获取哪个
		 * 最终结果都保存在CompletionService中
		 * 可根据结果回滚操作
		 */
		CompletionService service = new ExecutorCompletionService(threadPool) ;

		Callable callable = new Callable() {
			@Override
			public Object call() throws Exception {

				try {
					Thread.sleep(10);

					if (countDownLatch.getCount() == 500) {
						throw new Exception("=================抛出异常==================");
					}

					successNum.getAndIncrement() ;
				}catch (Exception e){
					errorNum.getAndIncrement() ;
					throw e ;
				}finally {
					countDownLatch.countDown();
				}

				return 1;
			}
		} ;

		// 创建任务提交线程
		for (int i=0;i<size;i++){
			service.submit(callable);
		}

		countDownLatch.await();

		// 关闭线程池
		threadPool.shutdown();

		// 获取所有的返回结果
		try{
			for (int i=0;i<size;i++){
				System.out.println(service.take().get()) ;
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return getString(beginTime, successNum, errorNum);
	}

	/**
	 * 输出
	 * @param beginTime
	 * @param successNum
	 * @param errorNum
	 * @return
	 */
	private String getString(long beginTime, AtomicInteger successNum, AtomicInteger errorNum) {
		long endTime = System.currentTimeMillis() ;

		StringBuilder sb = new StringBuilder("") ;
		sb.append("开始时间：").append(DateSingleton.getDateformat().format(beginTime)).append("</br>")
				.append("结束时间：").append(DateSingleton.getDateformat().format(endTime)).append("</br>")
				.append("总耗时：").append((endTime - beginTime)/1000).append("秒</br>")
				.append("成功：").append( successNum.get()).append("，失败：" + errorNum.get());
		return sb.toString() ;
	}
}
