package com.spring.yogiyo.pppservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.pppmodel.InterYogiyoDAO;

//#31. Service 선언
@Service
public class YogiyoService implements InterYogiyoService {

	// === #34. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired
	private InterYogiyoDAO dao;

	// 카테고리 리스트 불러오기
	@Override
	public List<HashMap<String, String>> getShopCategory() {
		List<HashMap<String, String>> categoryList = dao.getShopCategory();
		return categoryList;
	}
	
}
