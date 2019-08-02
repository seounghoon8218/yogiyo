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

	// 차트 테스트 ( 업종별 매장수 차트 )
	@Override
	public List<HashMap<String, String>> chartList() {
		List<HashMap<String, String>> chartList = dao.chartList();
		return chartList;
	}

	// TBL_PAYMENT 테이블에 추가
	@Override
	public int insertPayment(HashMap<String, String> paraMap) {
		int m = dao.insertPayment(paraMap);
		return m;
	}

	// 결제완료했으면 해당 아이디 장바구니 비우기
	@Override
	public int alldeleteCart(String email) {
		int z = dao.alldeleteCart(email);
		return z;
	}
	
}
