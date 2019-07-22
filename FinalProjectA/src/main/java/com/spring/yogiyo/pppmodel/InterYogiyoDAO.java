package com.spring.yogiyo.pppmodel;

import java.util.HashMap;
import java.util.List;

public interface InterYogiyoDAO {

	// 카테고리 리스트 불러오기
	List<HashMap<String, String>> getShopCategory();

}
