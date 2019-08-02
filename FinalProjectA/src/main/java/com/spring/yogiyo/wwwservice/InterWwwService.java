package com.spring.yogiyo.wwwservice;

import java.util.HashMap;
import java.util.List;


import com.spring.member.model.MemberVO;

public interface InterWwwService {


   MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 interdao 두개를 동시에 불러온다

   public void RegisterMember(MemberVO membervo); // 회원가입처리하기

   int selectUserID(String email);// 이메일 중복검사

   int edit(MemberVO membervo); // 회원정보 수정

   int pwdSearch(HashMap<String, String> map); // 비밀번호 핸드폰번호 불러오기
}