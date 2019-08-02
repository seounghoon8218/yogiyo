package com.spring.yogiyo.wwwmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.common.SHA256;
import com.spring.member.model.MemberVO;

//=== #32. DAO 선언 ===
@Repository
public class WwwDAO implements InterWwwDAO {

public SHA256 as = null;

   //=== #33. 의존객체 주입하기(DI: Dependency Injection) ===
   @Autowired
   private SqlSessionTemplate sqlsession;

   // === #46. 로그인 처리하기 ===
   @Override
   public MemberVO getLoginMember(HashMap<String, String> paraMap) {
      MemberVO loginuser = sqlsession.selectOne("www.getLoginMember", paraMap); // loginuser 서비스로감
      return loginuser;
   }
   @Override
   public void setLastLoginDate(HashMap<String, String> paraMap) {
      sqlsession.update("www.setLastLoginDate", paraMap);
   }

   // 회원가입
   @Override
   public void RegisterMember(MemberVO membervo) {
         membervo.setPwd(as.encrypt(membervo.getPwd()));
      sqlsession.insert("www.RegisterMember", membervo);
   }

   // 이메일 중복검사
   @Override
   public int selectUserID(String email) {
      int n = sqlsession.selectOne("www.selectUserID", email);
      return n;
   }

<<<<<<< HEAD
   // 내정보수정
   @Override
   public int edit(MemberVO membervo) {
      membervo.setPwd(as.encrypt(membervo.getPwd()));
      int n = sqlsession.update("www.edit", membervo);
       return n;
   }
   
   // 찾기
   @Override
   public int pwdSearch(HashMap<String, String> map) {
     int pwdOK = sqlsession.selectOne("www.pwdSearch", map); 
      return pwdOK;
   }
=======
	@Override
	public int edit(MemberVO membervo) {
		membervo.setPwd(as.encrypt(membervo.getPwd()));
		int n = sqlsession.update("www.edit", membervo);
	    return n;
	}
>>>>>>> branch 'master' of https://github.com/seounghoon8218/yogiyo.git


}