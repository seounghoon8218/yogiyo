package com.spring.yogiyo.wwwmodel;

import java.util.HashMap;
import java.util.List;

import com.spring.member.model.MemberVO;

public interface InterWwwDAO {

   MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 
   void setLastLoginDate(HashMap<String, String> paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기 

   void RegisterMember(MemberVO membervo); // 회원가입 처리

   int selectUserID(String email); // 이메일 중복검사

   int edit(MemberVO membervo); // 내정보 수정

   int pwdSearch(HashMap<String, String> map); // 찾기
}