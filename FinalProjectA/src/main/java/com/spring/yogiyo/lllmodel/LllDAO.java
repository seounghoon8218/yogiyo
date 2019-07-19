package com.spring.yogiyo.lllmodel;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.member.model.MemberVO;

public class LllDAO implements InterLllDAO {
	
	// === #33. 의존객체 주입하기(DI: Dependency Injection) ===
		@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
		private SqlSessionTemplate sqlsession;
	
	// === #46. 로그인 처리하기 ===
		@Override
		public MemberVO getLoginMember(HashMap<String, String> paraMap) {
			MemberVO loginuser = sqlsession.selectOne("lll.getLoginMember", paraMap);
			return loginuser;
		}
		@Override
		public void setLastLoginDate(HashMap<String, String> paraMap) {
			sqlsession.update("lll.setLastLoginDate", paraMap);
		}
}
