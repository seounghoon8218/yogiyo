package com.spring.yogiyo.ooomodel;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class oooDAO implements InteroooDAO {
	
	@Autowired
	private SqlSessionTemplate sqlsession;
	
	
	// 매장등록완료 
	@Override
	public int addshop(oooVO ovo) {
		int n = sqlsession.insert("ooo.addshop", ovo);
		return n;
	}
	
	
	
	
	
	
}
