package com.spring.yogiyo.pshmodel;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//== #32. DAO 선언 ===
@Repository
public class YogiyoDAO implements InterYogiyoDAO {

	// === #33. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired	// Type에 따라 알아서 Bean 주입해줌
	private SqlSessionTemplate sqlsession;
	
}

