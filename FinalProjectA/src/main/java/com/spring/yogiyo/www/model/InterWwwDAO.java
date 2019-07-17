package com.spring.yogiyo.www.model;

import java.util.HashMap;
import java.util.List;

import com.spring.member.model.MemberVO;

public interface InterWwwDAO {

	List<String> getImgfilenameList(); // 이미지 파일명 가져오기

	MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 
	void setLastLoginDate(HashMap<String, String> paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기 
	
}
