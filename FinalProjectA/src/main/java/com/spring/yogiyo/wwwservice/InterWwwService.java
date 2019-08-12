package com.spring.yogiyo.wwwservice;

import java.util.HashMap;
import java.util.List;

import com.spring.member.model.MemberVO;
import com.spring.yogiyo.wwwmodel.BoardVO;
import com.spring.yogiyo.wwwmodel.CommentVO;

public interface InterWwwService {


   MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 interdao 두개를 동시에 불러온다

   public void RegisterMember(MemberVO membervo); // 회원가입처리하기

   int selectUserID(String email);// 이메일 중복검사

   int editMember(MemberVO membervo); // 회원정보 수정

   // 회원탈퇴
	void memberDelEnd(MemberVO membervo); 
   
   int pwdSearch(HashMap<String, String> map); // 비밀번호 핸드폰번호 불러오기
   
   int newPwdEnd(MemberVO membervo); // 새비밀번호 수정
   
   // 검색조건이 없을 경우의 총게시물건수(totalCount)
   int getTotalCountWithNOSearch();
	
   // 검색조건이 있을 경우의 총게시물건수(totalCount)
   int getTotalCountWithSearch(HashMap<String, String> paraMap);

   // 페이징처리한 글목록보기(검색이 있던지, 검색이 없던지 다 포함한 것)
   List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap);
   
   int add(BoardVO boardvo); 		   // 글쓰기(파일첨부가 없는 글쓰기)
   int add_withFile(BoardVO boardvo); // 글쓰기(파일첨부가 있는 글쓰기)
   
   BoardVO getView(String seq, String userid); // 1개 글 보여주기.
	 // 글 조회수 증가는 다른 사람의 글을 읽을 때만 증가하도록 한다.
	 // 로그인 하지 않은 상태에서 글을 읽을때 조회수 증가가 일어나지 않게 한다.
   
   // 글조회수 증가는 없고 글조회만 해주는 것
	BoardVO getViewWithNoAddCount(String seq);
   
	// 원게시물에 딸린 댓글들을 조회해오는것
	List<CommentVO> getCommentList(String parentSeq);
   
	// 1개글 수정하기
	int edit(BoardVO boardvo);
	
	// 1개글 삭제하기(댓글쓰기가 있는 게시판)
	int del(BoardVO boardvo) throws Throwable;
	
	// 댓글 쓰기
	int addComment(CommentVO commentvo) throws Throwable;
	
	// 검색어 입력시 자동글 완성하기 
	List<String> wordSearchShow(HashMap<String, String> paraMap);

	// 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기
	List<BoardVO> boardListSearch(HashMap<String, String> paraMap);

	

	
		
	
	
}