package com.spring.yogiyo.wwwmodel;

import java.util.HashMap;
import java.util.List;

import com.spring.member.model.MemberVO;

public interface InterWwwDAO {

   MemberVO getLoginMember(HashMap<String, String> paraMap); // 로그인 처리하기 
   void setLastLoginDate(HashMap<String, String> paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기 

   void RegisterMember(MemberVO membervo); // 회원가입 처리

   int selectUserID(String email); // 이메일 중복검사

   int editMember(MemberVO membervo); // 내정보 수정
   
   void memberDelEnd(MemberVO membervo); // 회원탈퇴

   int pwdSearch(HashMap<String, String> map); // 찾기
   
   int newPwdEnd(MemberVO membervo); // 새 비밀번호 수정
   
   // 검색조건이 없을 경우의 총게시물건수(totalCount)
	int getTotalCountWithNOSearch();

	// 검색조건이 있을 경우의 총게시물건수(totalCount)
	int getTotalCountWithSearch(HashMap<String,String> paraMap);

	// 페이징처리한 글목록보기(검색이 있던지, 검색이 없던지 다 포함한 것)
	List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap);
	
	// tblBoard 테이블에서 grouppno 컬럼의 최대값 구하기
	int getGroupnoMax();
	
	// 글쓰기
	int add(BoardVO boardvo);
	// 글쓰기(파일첨부가 있는 글쓰기)
	int add_withFile(BoardVO boardvo);
	
	BoardVO getView(String seq); // 1개 글 보여주기
	void setAddReadCount(String seq);// 글조회수 1증가하기

	boolean checkPW(BoardVO boardvo); // 글수정 및 글삭제시 암호일치 여부 알아오기

	int updateBoard(BoardVO boardvo);// 1개글 수정하기

	int deleteBoard(BoardVO boardvo);// 1개글 삭제하기

	int addComment(CommentVO commentvo); // 댓글쓰기(tblComment 테이블에 insert)
	int updateCommentCount(String parentSeq);// tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update)

	List<CommentVO> getCommentList(String parentSeq); // 원게시물에 딸린 댓글보여주기

	// 원게시물에 딸린 모든 댓글들을 삭제하기
	int deleteComment(String seq);
	
	// 페이징처리를 안한 검색어가 있는 전체 글목록 보여주기
	List<BoardVO> boardListSearch(HashMap<String, String> paraMap);

	// 검색어 입력시 자동글 완성하기 4 ===
	List<String> wordSearchShow(HashMap<String, String> paraMap);
	
	
	

	
}