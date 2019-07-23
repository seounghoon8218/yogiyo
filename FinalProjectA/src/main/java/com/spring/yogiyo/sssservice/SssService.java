package com.spring.yogiyo.sssservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.sssmodel.InterSssDAO;
import com.spring.yogiyo.sssmodel.MenuVO;

@Service
public class SssService implements InterSssService {

	// 의존객체
	@Autowired
	private InterSssDAO dao;
	
	// 메뉴등록
	@Override
	public int menuRegister(MenuVO menuvo) {
		int menuReg = dao.menuRegister(menuvo); 
		return menuReg;
	}

}
