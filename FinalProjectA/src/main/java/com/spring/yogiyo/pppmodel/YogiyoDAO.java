package com.spring.yogiyo.pppmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//== #32. DAO 선언 ===
@Repository
public class YogiyoDAO implements InterYogiyoDAO {

	// === #33. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired	// Type에 따라 알아서 Bean 주입해줌
	private SqlSessionTemplate sqlsession;

	// 카테고리 리스트 불러오기
	@Override
	public List<HashMap<String, String>> getShopCategory() {
		List<HashMap<String, String>> categoryList = sqlsession.selectList("yogiyo.getShopCategory");
		return categoryList;
	}

	// 차트 테스트 ( 업종별 매장수 차트 )
	@Override
	public List<HashMap<String, String>> chartList() {
		List<HashMap<String, String>> chartList = sqlsession.selectList("yogiyo.chartList");
		return chartList;
	}

	// TBL_PAYMENT 테이블에 추가
	@Override
	public int insertPayment(HashMap<String, String> paraMap) {
		int m = sqlsession.insert("yogiyo.insertPayment",paraMap);
		return m;
	}

	// 결제완료했으면 해당 아이디 장바구니 비우기
	@Override
	public int alldeleteCart(String email) {
		int z = sqlsession.delete("yogiyo.alldeleteCart",email);
		return z;
	}
	
}

