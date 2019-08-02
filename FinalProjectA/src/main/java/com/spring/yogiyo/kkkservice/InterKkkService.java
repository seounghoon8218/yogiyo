package com.spring.yogiyo.kkkservice;

import java.util.HashMap;
import java.util.List;

import com.spring.yogiyo.ooomodel.oooVO;

public interface InterKkkService {

	// 매장가져오기
	List<oooVO> getShopList(HashMap<String,String> paraMap);

	// 매장하나정보 가지고오기
	oooVO getShopView(String masterno);

	// 메뉴카테고리 가져오기
	List<HashMap<String, String>> getMenucategoryList();
	// 리스트별 메뉴 가져오기
	List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap);

	// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
	int cartSelect(HashMap<String, String> paramap);
	// 장바구니에 insert 해주기
	int cartInsert(HashMap<String, String> paramap);

	// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
	List<HashMap<String, String>> selectCartList(String email);

	// 주문메뉴 삭제
	int delMenu(String orderno);

	// 타음식점 추가 불가 기능
	String getCartMasterno(String email);

	// 지훈 - 매장정보
	List<HashMap<String, String>> getShopInfo(String masterno);
	
	// 총 별점 평균 보여주기
	double getStarpointAvg(int masterno);

	// 총 리뷰 갯수 보여주기
	int getReviewCount(int masterno);

	// 리뷰 등록 // 
	List<HashMap<String, String>> getReviewList(String masterno);
	int addReview(HashMap<String, String> paramap);

	// 결제테이블에서 Status 체크
	List<HashMap<String, String>> checkOrderStatus(HashMap<String, String> paramap);
	
}
