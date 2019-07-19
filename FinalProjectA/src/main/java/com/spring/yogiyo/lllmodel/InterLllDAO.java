package com.spring.yogiyo.lllmodel;

import java.util.HashMap;

import com.spring.member.model.MemberVO;

public interface InterLllDAO {
	MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 
	void setLastLoginDate(HashMap<String, String> paraMap);   // 마지막으로 로그인 한 날짜시간 변경(기록)하기 
}
