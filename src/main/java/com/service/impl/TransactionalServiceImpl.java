package com.service.impl;

import com.service.TransactionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalServiceImpl implements TransactionalService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String testA(boolean flag) throws Exception {
		testB(true);

		return null ;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String testB(boolean flag) throws Exception {
		if(flag) {
			throw new Exception("testB抛出异常");
		}
		return null ;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String testC(boolean flag) throws Exception {
		return null ;
	}
}
