package com.controller;

import com.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试事务
 */
@Controller
public class TransactionalController {

	@Autowired
	private TransactionalService transactionalService ;

	@RequestMapping("/trans/test1")
	@ResponseBody
	public String transTest() throws Exception {
		transactionalService.testA(true);
		return "OK" ;
	}
}
