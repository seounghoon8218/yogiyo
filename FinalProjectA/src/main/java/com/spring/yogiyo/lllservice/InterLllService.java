package com.spring.yogiyo.lllservice;

import java.util.HashMap;

import com.spring.member.model.MemberVO;

public interface InterLllService {
	MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기       
	}
