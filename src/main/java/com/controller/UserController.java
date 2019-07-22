package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	/**
	 * 定义一个公共成员变量，会出现线程问题
	 * 解决的方案是成员变量保存在ThreadLocal里面
	 */
	private String name = null ;

	/**
	 * 使用类成员变量，会出现线程安全问题
	 * servlet是非线程安全的
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/user",method = RequestMethod.GET)
	@ResponseBody
	public String getCurrentUser(@RequestParam(required = true) String username){
		try {
			long start = System.currentTimeMillis() ;
			name = username ;
			Thread.sleep(5000);
			long end = System.currentTimeMillis() ;
			return "访问时间：" + (end - start)/1000 + "当前用户是：" + name ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "访问失败" ;
		}
	}

	/**
	 * 保存成员变量，确保不会出现线程安全问题
	 */
	private ThreadLocal<String> threadLocal = new ThreadLocal<String>() ;

	/**
	 * 成员变量保存在ThreadLocal中，不会出现线程安全问题
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/user2")
	@ResponseBody
	public String getCurrentUser2(@RequestParam(required = true) String username){
		try {
			long start = System.currentTimeMillis() ;
			threadLocal.set(username) ;
			Thread.sleep(5000);
			long end = System.currentTimeMillis() ;
			return "访问时间：" + (end - start)/1000 + "当前用户是：" + threadLocal.get() ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "访问失败" ;
		}
	}
}
