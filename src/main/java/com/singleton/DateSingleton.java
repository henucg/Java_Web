package com.singleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 定义一个时间格式的懒汉单例模式
 */
public class DateSingleton {

	private static DateFormat dateFormat = null ;

	private DateSingleton(){}

	public static DateFormat getDateformat(){
		if(dateFormat==null){
			// 保证线程安全
			synchronized (DateSingleton.class){
				if(dateFormat==null){
					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
				}
			}
		}

		return dateFormat ;
	}
}
