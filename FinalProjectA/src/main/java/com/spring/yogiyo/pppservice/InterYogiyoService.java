package com.spring.yogiyo.pppservice;

import java.util.HashMap;
import java.util.List;

public interface InterYogiyoService {

	// 카테고리 리스트 불러오기
	List<HashMap<String, String>> getShopCategory();

	// 차트 테스트 ( 업종별 매장수 차트 )
	List<HashMap<String, String>> chartList();

}
