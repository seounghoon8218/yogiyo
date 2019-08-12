package com.spring.yogiyo.sssmodel;

import java.util.HashMap;
import java.util.List;

public interface InterSssDAO {

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

	// 글 1개 보여주기 및 readCount 증가하기
	GreetingBoardVO getGreetingView(String seq);
	void addGreetingReadCount(String seq);

	// 글 1개 보여주기
	GreetingBoardVO getGreetingViewNoAddCount(String seq);

	// 댓글 불러오기
	List<GreetingCommentVO> getGreetingCommentList(String seq);

	// 댓글 쓰기
	int addGreetingComment(GreetingCommentVO gcommentvo);

	// 글 수정하기 
	int editGreeting(GreetingBoardVO gbvo);

	// 비밀번호 체크
	boolean checkPW(GreetingBoardVO gbvo);

	// 글 삭제하기
	int destroyGreeting(GreetingBoardVO gbvo);

	// 댓글이 있는 글이라면 댓글도 같이 삭제해주어야한다.
	int destroyGreetingComment(String seq);

	// groupno의 max값 불러오
	int getGroupnoMax();



	
}
