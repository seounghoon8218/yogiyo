package com.spring.yogiyo.lllmodel;



import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LllDAO implements InterLllDAO {
   // === #33. 의존객체 주입하기(DI: Dependency Injection) ===
      @Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
      private SqlSessionTemplate sqlsession;
      
      // === #55.글쓰기(파일첨부가 없는 글쓰기) ===   
      @Override
      public int moonadd(MoonVO moonvo) {
         int n = sqlsession.insert("lll.moonadd", moonvo);
         return n;
      }
      
      
      // === #127. tblBoard 테이블에서 groupno 컬럼의 최대값 구하기 === 
      @Override
      public int getGroupnoMax() {
         int max = sqlsession.selectOne("lll.getGroupnoMax");
         return max;
      }


      // === #111. 검색조건이 없을 경우의 총 게시물 건수(totalCount) === 
      @Override
      public int getTotalCountWithNOsearch() {
         int count = sqlsession.selectOne("lll.getTotalCountWithNOsearch");
         return count;
      }

      
      // === #114. 검색조건이 있을 경우의 총 게시물 건수(totalCount) === 
      @Override
      public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
         int count = sqlsession.selectOne("lll.getTotalCountWithSearch", paraMap); 
         return count;
      }

      
      // === #117. 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것) ===
      @Override
      public List<MoonVO> boardListWithPaging(HashMap<String, String> paraMap) {
         List<MoonVO> boardList = sqlsession.selectList("lll.boardListWithPaging", paraMap);
         return boardList;
      }


      // === #63. 1개 글 보여주기 === //
      @Override
      public MoonVO getView(String seq) {
         MoonVO moonvo = sqlsession.selectOne("lll.getView", seq);
         return moonvo;
      }
      // === #64. 글조회수 1증가하기 === //
      @Override
      public void setAddReadCount(String seq) {
         sqlsession.update("lll.setAddReadCount", seq);
      }


      // === #88. 원게시물에 딸린 댓글보여주기 ===
      @Override
      public List<CommentVO> getCommentList(String parentSeq) {
         List<CommentVO> commentList = sqlsession.selectList("lll.getCommentList", parentSeq);
         return commentList;
      }


      // === #73. 글수정 및 글삭제시 암호일치 여부 알아오기 === 
      @Override
      public boolean checkPW(MoonVO moonvo) {
         int n = sqlsession.selectOne("lll.checkPW", moonvo); 
         
         if(n==1) 
            return true;
         else
            return false;
      }


      // === #75. 글 1개를 수정한다. ===
      @Override
      public int updateBoard(MoonVO moonvo) {
         int n = sqlsession.update("lll.updateBoard", moonvo);
         return n;
      }


      // === #94. 원게시물에 딸린 모든 댓글들을 삭제하도록 한다. ===
      @Override
      public int deleteComment(String seq) {
         int n = sqlsession.delete("lll.deleteComment", seq);
         return n;
      }


      // === #80. 1개글 삭제하기  ===
      @Override
      public int deleteBoard(MoonVO moonvo) {
         int n = sqlsession.update("lll.deleteBoard", moonvo);
         return n;
      }


      // === #87. 댓글쓰기(tblComment 테이블에 insert) === 
      @Override
      public int addComment(CommentVO commentvo) {
         int n = sqlsession.insert("lll.addComment", commentvo);
         return n;
      }

      // === #88. tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) === 
      @Override
      public int updateCommentCount(String parentSeq) {
         int n = sqlsession.update("lll.updateCommentCount", parentSeq);
         return n;
      }


      // === #105. 검색어 입력시 자동글 완성하기 5 === 
      @Override
      public List<String> wordSearchShow(HashMap<String, String> paraMap) {
         List<String> wordList = sqlsession.selectList("lll.wordSearchShow", paraMap);
         return wordList;
      }
   
   
}