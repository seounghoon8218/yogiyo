package com.spring.yogiyo.pppmodel;

import java.util.HashMap;
import java.util.List;

public interface InterYogiyoDAO {

	// 카테고리 리스트 불러오기
	List<HashMap<String, String>> getShopCategory();

	// 차트 테스트 ( 업종별 매장수 차트 )
	List<HashMap<String, String>> chartList();

	// TBL_PAYMENT 테이블에 추가
	int insertPayment(HashMap<String, String> paraMap);

	// 결제완료했으면 해당 아이디 장바구니 비우기
	int alldeleteCart(String email);

}
