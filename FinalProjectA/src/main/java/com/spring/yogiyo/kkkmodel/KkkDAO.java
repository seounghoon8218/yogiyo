package com.spring.yogiyo.kkkmodel;

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
	public List<oooVO> getShopList() {
		List<oooVO> shopList = sqlsession.selectList("kkk.getShopList");
		return shopList;
	}
	
}
