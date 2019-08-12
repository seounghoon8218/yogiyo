package com.spring.yogiyo.lllmodel;

import java.util.HashMap;
import java.util.List;


public interface InterLllDAO {
   
   int moonadd(MoonVO moonvo);  // 글쓰기(파일첨부가 없는 글쓰기)

   int getGroupnoMax(); // tblBoard 테이블에서 groupno 컬럼의 최대값 구하기 

   int getTotalCountWithNOsearch();

   int getTotalCountWithSearch(HashMap<String, String> paraMap);

   List<MoonVO> boardListWithPaging(HashMap<String, String> paraMap);

   MoonVO getView(String seq); // 1개 글 보여주기
   void setAddReadCount(String seq); // 글조회수 1증가하기

   List<CommentVO> getCommentList(String parentSeq); // 원게시물에 딸린 댓글보여주기

   boolean checkPW(MoonVO moonvo);
   int updateBoard(MoonVO moonvo);

   int deleteComment(String seq); // 원게시물에 딸린 모든 댓글들을 삭제하도록 한다.
   int deleteBoard(MoonVO moonvo); // 글 1개를 삭제한다.

   int addComment(CommentVO commentvo); // 댓글쓰기(tblComment 테이블에 insert) 
   int updateCommentCount(String parentSeq); // tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) 

   List<String> wordSearchShow(HashMap<String, String> paraMap);
   

}