package com.controller;

import com.service.ThreadPoolTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 线程池测试接口
 */
@Controller
public class ThreadPoolTaskController {

	@Autowired
	private ThreadPoolTaskService threadPoolTaskService ;

	/**
	 * 调用线程池运行
	 * @return
	 */
	@RequestMapping(value="/thread/pool")
	@ResponseBody
	public String runThreadPool(){

		for(int i=1;i<=10;i++) {
			try {
				threadPoolTaskService.testThreadPool(i);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return "线程池运行完成" ;
	}

	/**
	 * 执行多线程
	 * @return
	 */
	@RequestMapping(value="/thread/run")
	@ResponseBody
	public String runThread(){
		String s = "" ;
		try{
			s = threadPoolTaskService.testRunThread(1000) ;
		}catch(Exception e){
			e.printStackTrace();
		}

		return "多线程执行完成:</br>" + s ;
	}
}
