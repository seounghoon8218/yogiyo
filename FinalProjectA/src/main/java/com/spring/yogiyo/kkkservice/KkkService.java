package com.spring.yogiyo.kkkservice;

import java.util.HashMap;
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
		public List<oooVO> getShopList(HashMap<String,String> paraMap) {
			List<oooVO> shopList = dao.getShopList(paraMap);
			return shopList;
		}
		

		// 매장하나정보 가지고오기
		@Override
		public oooVO getShopView(String masterno) {
			oooVO shop = dao.getShopView(masterno);
			return shop;
		}

		// 메뉴카테고리 가져오기
		@Override
		public List<HashMap<String, String>> getMenucategoryList() {
			List<HashMap<String, String>> menucategoryList = dao.getMenucategoryList();
			return menucategoryList;
		}

		// 리스트별 메뉴 가져오기
		@Override
		public List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap) {
			List<HashMap<String, String>> menuList = dao.getMenuList(paramap);
			return menuList;
		}

		// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
		@Override
		public int cartSelect(HashMap<String, String> paramap) {
			int m = dao.cartSelect(paramap);
			return m;
		}
		// 장바구니에 insert 해주기
		@Override
		public int cartInsert(HashMap<String, String> paramap) {
			int n = dao.cartInsert(paramap);
			return n;
		}

		// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
		@Override
		public List<HashMap<String, String>> selectCartList(String email) {
			List<HashMap<String, String>> cartList = dao.selectCartList(email);
			return cartList;
		}


		// 주문메뉴 삭제
		@Override
		public int delMenu(String orderno) {
			int n = dao.delMenu(orderno);
			return n;

		}

		// 타음식점 추가 불가 기능
		@Override
		public String getCartMasterno(String email) {
			String msno = dao.getCartMasterno(email);
			return msno;
		}

		// 지훈 - 매장정보
	      @Override
	      public List<HashMap<String, String>> getShopInfo(String masterno) {
	         List<HashMap<String, String>> shopInfo = dao.getShopInfo(masterno);
	         return shopInfo;
	      }

	   // 총 별점 평균 보여주기
	      @Override
	      public double getStarpointAvg(int masterno) {
	         double starpointAvg = dao.getStarpointAvg(masterno);
	         return starpointAvg;
	      }

	      // 총 리뷰 갯수 보여주기
	      @Override
	      public int getReviewCount(int masterno) {
	         int reviewCount = dao.getReviewCount(masterno);
	         return reviewCount;
	      }

	      // 리뷰 보여주기
	      @Override
	      public List<HashMap<String, String>> getReviewList(String masterno) {
	    	  
	    	 // 조건
	    	 int count = dao.getReviewCount1(masterno);
	    	 
	    	 List<HashMap<String, String>> reviewList = null;
	    	 
	    	 if(count > 0) { 
	    		  reviewList = dao.getReviewList(masterno);
	    		  System.out.println("~~~~~ 리뷰 있음!!!");
	    	 }
	    	 else {
	    		 System.out.println("~~~~~ 리뷰 없음!!!");
	    	 }
	    		 
	    	 	 
	         return reviewList;
	      }

	      // 리뷰 등록
	      @Override
	      public int addReview(HashMap<String, String> paramap) {
	         int n = dao.addReview(paramap);
	         return n;
	      }

	      // 결제테이블에서 Status 체크
	      @Override
	      public List<HashMap<String, String>> checkOrderStatus(HashMap<String, String> paramap) {
	         List<HashMap<String, String>> k = dao.checkOrderStatus(paramap);
	         return k;
	         
	      }	      
	      
	      // 별점이랑 리뷰갯수 업데이트 해주기
	      @Override
	      public int updateStarpAndReviewc(HashMap<String, String> masterStarReviewMap) {
	         int sprc = dao.updateStarpAndReviewc(masterStarReviewMap);
	         return sprc;
	      }         
	      
	      
}
