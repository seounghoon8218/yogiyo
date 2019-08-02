package com.spring.yogiyo.kkkmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.yogiyo.ooomodel.oooVO;

//== #32. DAO 선언 ===
@Repository
public class KkkDAO implements InterKkkDAO {

	// === #33. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired	// Type에 따라 알아서 Bean 주입해줌
	private SqlSessionTemplate sqlsession;

	// 매장가져오기
	@Override
	public List<oooVO> getShopList(HashMap<String,String> paraMap) {
		List<oooVO> shopList = sqlsession.selectList("kkk.getShopList",paraMap);
		return shopList;
	}
	

	// 매장하나정보 가지고오기
	@Override
	public oooVO getShopView(String masterno) {
		oooVO shop = sqlsession.selectOne("kkk.getShopView",masterno);
		return shop;
	}

	// 메뉴카테고리 가져오기
	@Override
	public List<HashMap<String, String>> getMenucategoryList() {
		List<HashMap<String, String>> menucategoryList = sqlsession.selectList("kkk.getMenucategoryList");
		return menucategoryList;
	}

	// 리스트별 메뉴 가져오기
	@Override
	public List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap) {
		List<HashMap<String, String>> menuList = sqlsession.selectList("kkk.getMenuList",paramap);
		return menuList;
	}

	// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
	@Override
	public int cartSelect(HashMap<String, String> paramap) {
		int m = sqlsession.selectOne("kkk.cartSelect",paramap);
		return m;
	}
	// 장바구니에 insert 해주기
	@Override
	public int cartInsert(HashMap<String, String> paramap) {
		int n = sqlsession.insert("kkk.cartInsert",paramap);
		return n;
	}

	// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
	@Override
	public List<HashMap<String, String>> selectCartList(String email) {
		List<HashMap<String, String>> cartList = sqlsession.selectList("kkk.selectCartList",email);
		return cartList;
	}


	// 주문메뉴 삭제
	@Override
	public int delMenu(String orderno) {
		int n = sqlsession.delete("kkk.delMenu", orderno);
		return n;
	}

	// 타음식점 추가 불가 기능
	@Override
	public String getCartMasterno(String email) {
		String msno = sqlsession.selectOne("kkk.getCartMasterno", email);
		return msno;
	}

	// 지훈 - 매장정보
	   @Override
	   public List<HashMap<String, String>> getShopInfo(String masterno) {
	      List<HashMap<String, String>> shopInfo = sqlsession.selectList("kkk.getShopInfo",masterno);
	      return shopInfo;
	   }


	   // 총 별점 평균 가져오기
	   @Override
	   public double getStarpointAvg(int masterno) {
	      double starpointAvg = sqlsession.selectOne("kkk.getStarpointAvg", masterno);
	      return starpointAvg;
	   }

	   // 총 리뷰 갯수 보여주기
	   @Override
	   public int getReviewCount(int masterno) {
	      int reviewCount = sqlsession.selectOne("kkk.getReviewCount", masterno);
	      return reviewCount;
	   }


	   // 리뷰 보여주기
	   @Override
	   public List<HashMap<String, String>> getReviewList(String masterno) {
	      List<HashMap<String, String>> reviewList = sqlsession.selectList("kkk.getReviewList", masterno);
	      return reviewList;
	   }
	   
	   // 리뷰 등록
	   @Override
	   public int addReview(HashMap<String, String> paramap) {
	      int n = sqlsession.insert("kkk.addReview",paramap);
	      return n;
	   }

	   // 결제테이블에서 Status 체크
	   @Override
	   public List<HashMap<String, String>> checkOrderStatus(HashMap<String, String> paramap) {
	      List<HashMap<String,String>> k = sqlsession.selectList("kkk.checkOrderStatus", paramap);
	      return k;
	   }	   
	   
}
