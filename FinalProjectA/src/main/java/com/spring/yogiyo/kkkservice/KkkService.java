package com.spring.yogiyo.kkkservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.kkkmodel.InterKkkDAO;
import com.spring.yogiyo.ooomodel.oooVO;

@Service
public class KkkService implements InterKkkService {

	// === #34. 의존객체 주입하기 ( DI : Dependency Injection ) ====
		@Autowired
		private InterKkkDAO dao;

		// 매장 가져오기
		@Override
		public List<oooVO> getShopList() {
			List<oooVO> shopList = dao.getShopList();
			return shopList;
		}

		// 매장하나정보 가지고오기
		@Override
		public oooVO getShopView(String masterno) {
			oooVO shop = dao.getShopView(masterno);
			return shop;
		}
	
}
