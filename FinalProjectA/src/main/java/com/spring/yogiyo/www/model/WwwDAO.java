package com.spring.yogiyo.www.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.member.model.MemberVO;

//=== #32. DAO 선언 ===
@Repository
public class WwwDAO implements InterWwwDAO {

	//=== #33. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired
	private SqlSessionTemplate sqlsession;

	// === #38. 메인 페이지용 이미지 파일을 가져오기===
	@Override
	public List<String> getImgfilenameList() {
		List<String> imgfilenameList = sqlsession.selectList("board.getImgfilenameList");
		return imgfilenameList;
	}

	// === #46. 로그인 처리하기 ===
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser = sqlsession.selectOne("board.getLoginMember", paraMap); // loginuser 서비스로감
		return loginuser;
	}
	@Override
	public void setLastLoginDate(HashMap<String, String> paraMap) {
		sqlsession.update("board.setLastLoginDate", paraMap);
		
	}
}
