package com.spring.yogiyo.www.service;

import java.util.HashMap;
import java.util.List;

import com.spring.member.model.MemberVO;

public interface InterWwwService {

	List<String> getImgfilenameList(); // 이미지 파일명 가져오기

	MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 interdao 두개를 동시에 불러온다
	
}
