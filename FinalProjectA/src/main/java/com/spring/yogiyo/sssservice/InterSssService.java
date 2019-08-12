package com.spring.yogiyo.sssservice;

import java.util.HashMap;
import java.util.List;

import com.spring.yogiyo.sssmodel.GreetingBoardVO;
import com.spring.yogiyo.sssmodel.GreetingCommentVO;
import com.spring.yogiyo.sssmodel.MenuVO;

public interface InterSssService {

	// 메뉴등록
	int menuRegister(MenuVO menuvo);

	// 검색조건이 없는 경우의 totalCount불러오기
	int getTotalCountWithOutSearch();

	// 검색조건이 있는 경우의 totalCount불러오기
	int getTotalCountWithSearch(HashMap<String, String> paramap);

	// 가입인사 등록
	int addGreeting(GreetingBoardVO gbvo);
	
	// 게시물 리스트 불러오기
	List<GreetingBoardVO> greetingList(HashMap<String, String> paramap);

	// 글 1개 불러오기 + readCount 증가
	GreetingBoardVO getGreetingView(String seq, String useremail);

	// 글 1개 불러오기
	GreetingBoardVO getGreetingViewNoAddCount(String seq);

	// 댓글 불러오기
	List<GreetingCommentVO> getGreetingCommentList(String seq);

	// 댓글쓰기
	int addGreetingComment(GreetingCommentVO gcommentvo);

	// 글 수정하기
	int editGreeting(GreetingBoardVO gbvo);

	// 글 삭제하기
	int destroyGreeting(GreetingBoardVO gbvo);


	
	

	
	
}
