package com.service.impl;

import com.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

	public boolean login(String name) {
		try{
			checkUser(name);
			return true ;
		}catch(Exception e){
			return false;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void checkUser(String name) throws Exception {
		if ("Tom".equals(name)) {
			System.out.println("用户名检验成功");
		} else {
			throw new Exception("用户名错误");
		}
	}
}
