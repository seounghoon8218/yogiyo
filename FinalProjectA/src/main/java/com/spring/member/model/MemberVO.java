package com.spring.member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import com.spring.common.AES256;

/*
  VO(Value Object) 또는  DTO(Data Transfer Object) 생성하기 
*/

public class MemberVO { 


   private int idx;            // 회원번호(시퀀스로 데이터가 들어온다)
   private String email;       // 이메일
   private String name;        // 회원명
   private String pwd;         // 비밀번호
   private String tel;         // 휴대폰 번호
   private String post1;       // 우편번호
   private String post2;  
   private String addr1;       // 주소
   private String addr2;       // 상세주소
   
   private int coin;           // 코인액
   private int point;          // 포인트
   
   private String registerday; // 가입일자
   private int status;         // 회원탈퇴유무   1:사용가능(가입중) / 0:사용불능(탈퇴) 
   
   private String lastLoginDate;     // 마지막으로 로그인 한 날짜시간 기록용 
   private String lastPwdChangeDate; // 마지막으로 암호를 변경한 날짜시간 기록용
   
   private boolean requirePwdChange = false; 
   // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 6개월이 지났으면 true
   // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 6개월이 지나지 않았으면 false 
   
   private boolean idleStatus = false;  // 휴면유무(휴면이 아니라면 false, 휴면이면  true)
   // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 true(휴면으로 지정)
   // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지나지 않았으면 false 
   
    /////////////////////////////////////////////////////////////////////////////////////
   private int lastlogindategap;  // 로그인시 현재날짜와 최근 마지막으로 로그인한 날짜와의 개월수 차이 (12개월 동안 로그인을 안 했을 경우 해당 로그인계정을 비활성화 시키려고 함) 
   private int pwdchangegap;      // 로그인시 현재날짜와 최근 마지막으로 암호를 변경한 날짜와의 개월수 차이 (6개월 동안 암호를 변경 안 했을시 암호를 변경하라는 메시지를 보여주기 위함) 
   
   
   public MemberVO() { }
   
   public MemberVO(int idx, String email,String name, String pwd, String tel,
         String post1, String post2, String addr1, String addr2, 
         int coin, int point,
         String registerday, int status) {
      this.idx = idx;
      this.email = email;
      this.name = name;
      this.pwd = pwd;
      this.tel = tel;
      this.post1 = post1;
      this.post2 = post2;
      this.addr1 = addr1;
      this.addr2 = addr2;
      
      this.coin = coin;
      this.point = point;
            
      this.registerday = registerday;
      this.status = status;
      
   }

   
   public int getIdx() {
      return idx;
   }

   public void setIdx(int idx) {
      this.idx = idx;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPwd() {
      return pwd;
   }

   public void setPwd(String pwd) {
		this.pwd =pwd;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getTel() {
      return tel;
   }

   public void setTel(String tel) {
      this.tel = tel;
   }

   public String getPost1() {
      return post1;
   }

   public void setPost1(String post1) {
      this.post1 = post1;
   }

   public String getPost2() {
      return post2;
   }

   public void setPost2(String post2) {
      this.post2 = post2;
   }

   public String getAddr1() {
      return addr1;
   }

   public void setAddr1(String addr1) {
      this.addr1 = addr1;
   }

   public String getAddr2() {
      return addr2;
   }

   public void setAddr2(String addr2) {
      this.addr2 = addr2;
   }

   public int getCoin() {
      return coin;
   }

   public void setCoin(int coin) {
      this.coin = coin;
   }

   public int getPoint() {
      return point;
   }

   public void setPoint(int point) {
      this.point = point;
   }

   public String getRegisterday() {
      return registerday;
   }

   public void setRegisterday(String registerday) {
      this.registerday = registerday;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public String getLastLoginDate() {
      return lastLoginDate;
   }

   public void setLastLoginDate(String lastLoginDate) {
      this.lastLoginDate = lastLoginDate;
   }

   public String getLastPwdChangeDate() {
      return lastPwdChangeDate;
   }

   public void setLastPwdChangeDate(String lastPwdChangeDate) {
      this.lastPwdChangeDate = lastPwdChangeDate;
   }

   public boolean isRequirePwdChange() {
      return requirePwdChange;
   }

   public void setRequirePwdChange(boolean requirePwdChange) {
      this.requirePwdChange = requirePwdChange;
   }

   public boolean isIdleStatus() {
      return idleStatus;
   }

   public void setIdleStatus(boolean idleStatus) {
      this.idleStatus = idleStatus;
   }

   /////////////////////////////////////////////////////////////////////////////////////
   public int getLastlogindategap() {
      return lastlogindategap;
   }

   public void setLastlogindategap(int lastlogindategap) {
      this.lastlogindategap = lastlogindategap;
   }

   public int getPwdchangegap() {
      return pwdchangegap;
   }

   public void setPwdchangegap(int pwdchangegap) {
      this.pwdchangegap = pwdchangegap;
   }
   
}



