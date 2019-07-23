package com.controller;

import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService ;

	/**
	 * 登陆接口，测试事务
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public String login(@RequestParam String name){
		boolean result = loginService.login(name) ;
		if(result){
			return "user name = " + name ;
		}else{
			return "there is no user name " + name ;
		}
	}
}
