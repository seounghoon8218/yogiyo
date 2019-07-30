package com.spring.yogiyo.sssmodel;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SssDAO implements InterSssDAO {

	@Autowired
	private SqlSessionTemplate sqlsession;
	
	// 메뉴등록
	@Override
	public int menuRegister(MenuVO menuvo) {
		int n = sqlsession.insert("sss.menuRegister",menuvo);
		return n;
	}


	
	

}
