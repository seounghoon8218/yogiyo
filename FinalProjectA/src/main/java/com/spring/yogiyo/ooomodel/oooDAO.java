package com.spring.yogiyo.ooomodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class oooDAO implements InteroooDAO {

   @Autowired
   private SqlSessionTemplate sqlsession;

   // 매장 등록 완료
   @Override
   public int addshop(oooVO ovo) {
      int n = sqlsession.insert("ooo.addshop", ovo);
      return n;
   }

   // 업종 카테고리 가져오기
   @Override
   public List<HashMap<String, String>> selectShopCategory() {
      List<HashMap<String, String>> shopCategoryList = sqlsession.selectList("ooo.selectShopCategory");
      return shopCategoryList;
   }

   // 사업자 번호 가져오기
   @Override
   public int getMasterNo() {
      int masterno = sqlsession.selectOne("ooo.getMasterNo");
      return masterno;
   }

   // 검색 조건이 없을 경우
   @Override
   public int getTotalCountWithNOsearch() {
      int count = sqlsession.selectOne("ooo.getTotalCountWithNOsearch");
      return count;
   }
   
   // 검색 조건이 있을 경우
   @Override
   public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
      int count = sqlsession.selectOne("ooo.getTotalCountWithSearch", paraMap);
      return count;
   }
   
   
   //페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)
   @Override
   public List<oooBoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
      List<oooBoardVO> boardList = sqlsession.selectList("ooo.boardListWithPaging", paraMap);
      return boardList;
   }
   
   // tbl_oooBoard 테이블에서 groupno 컬럼의 최대값 구하기
   @Override
   public int getGroupnoMax() {
      int max = sqlsession.selectOne("ooo.getGroupnoMax");
      return max;
   }
   
   
   // 글쓰기(파일첨부가 없는 글쓰기)
   @Override
   public int add(oooBoardVO oooboardvo) {
      int n = sqlsession.insert("ooo.add", oooboardvo);
      return n;
   }

   // 글쓰기 (파일 첨부가 있는 글쓰기)
   @Override
   public int add_withFile(oooBoardVO oooboardvo) {
      int n = sqlsession.insert("ooo.add_withFile", oooboardvo);
      return n;   
   }
   
   // 1개 글 보여주기
   @Override
   public oooBoardVO getView(String seq) {
      oooBoardVO oooboardvo = sqlsession.selectOne("ooo.getView", seq);
      return oooboardvo;
   }
   
   // 글 조회수 증가하기
   @Override
   public void setAddReadCount(String seq) {
      sqlsession.update("ooo.setAddReadCount", seq);
      
   }
   
   // 원게시물에 딸린 댓글들 조회해오기
   @Override
   public List<oooCommentVO> getCommentList(String parentseq) {
      List<oooCommentVO> commentList = sqlsession.selectList("ooo.getCommentList", parentseq);
      return commentList;
   }

   // 글 수정 및 글 삭제시 암호 일치여부 알아오기
   @Override
   public boolean checkPW(oooBoardVO oooboardvo) {
      int n = sqlsession.selectOne("ooo.checkPW", oooboardvo);
      
      if(n==1) return true;
      else return false;
   }
   
   // 글 1개를 수정한다
   @Override
   public int updateBoard(oooBoardVO oooboardvo) {
      int n = sqlsession.update("ooo.updateBoard", oooboardvo);
      return n;
   }
   
   // 원게시물에 딸린 모든 댓글들 삭제하기
   @Override
   public int deleteComment(String seq) {
      int n = sqlsession.delete("ooo.deleteComment", seq);
      return n;
   }

   // 글 1개를 삭제하기
   @Override
   public int deleteBoard(oooBoardVO oooboardvo) {
      int n = sqlsession.delete("ooo.deleteBoard", oooboardvo);
      return n;
   }

   // 댓글쓰기(tblComment 테이블에 insert) 
   @Override
   public int addComment(oooCommentVO ooocommentvo) {
      int n = sqlsession.insert("ooo.addComment", ooocommentvo);
      return n;
   }
      
   // tbl_oooBoard 테이블에 commentCount컬럼의 값을 1증가(update)
   @Override
   public int updateCommentCount(String parentSeq) {
      int n = sqlsession.update("ooo.updateCommentCount", parentSeq);
      return n;
   }

   
   // 검색어 입력시 자동글 완성하기
   @Override
   public List<String> wordSearchShow(HashMap<String, String> paraMap) {
      List<String> wordList = sqlsession.selectList("ooo.wordSearchShow", paraMap);
      return wordList;
      
   }
   
   
   
   
   
   
   
}