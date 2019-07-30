package com.spring.yogiyo.kkkmodel;

import java.util.HashMap;
import java.util.List;

import com.spring.yogiyo.ooomodel.oooVO;

public interface InterKkkDAO {

	// 매장가져오기
	List<oooVO> getShopList(HashMap<String,String> paraMap);

	// 매장하나정보 가지고오기
	oooVO getShopView(String masterno);

	// 메뉴카테고리 가져오기
	List<HashMap<String, String>> getMenucategoryList();
	// 리스트별 메뉴 가져오기
	List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap);

}
