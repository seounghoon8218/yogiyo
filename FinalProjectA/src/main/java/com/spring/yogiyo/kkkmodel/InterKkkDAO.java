package com.spring.yogiyo.kkkmodel;

import java.util.List;

import com.spring.yogiyo.ooomodel.oooVO;

public interface InterKkkDAO {

	// 매장가져오기
	List<oooVO> getShopList();

	// 매장하나정보 가지고오기
	oooVO getShopView(String masterno);

}
