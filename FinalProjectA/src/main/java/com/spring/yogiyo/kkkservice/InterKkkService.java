package com.spring.yogiyo.kkkservice;

import java.util.HashMap;
import java.util.List;

import com.spring.yogiyo.ooomodel.oooVO;

public interface InterKkkService {

	// 매장가져오기
	List<oooVO> getShopList(HashMap<String,String> paraMap);

	// 매장하나정보 가지고오기
	oooVO getShopView(String masterno);

}
