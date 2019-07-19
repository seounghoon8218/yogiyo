package com.spring.yogiyo.oooservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.ooomodel.InteroooDAO;
import com.spring.yogiyo.ooomodel.oooVO;

@Service
public class oooservice implements Interoooservice {

	@Autowired
	private InteroooDAO dao;
	
	
	
	@Override
	public int addshop(oooVO ovo) {
		int n = dao.addshop(ovo);
		return n;
	}

}
