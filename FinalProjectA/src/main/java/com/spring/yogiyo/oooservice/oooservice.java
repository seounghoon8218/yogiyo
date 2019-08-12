package com.spring.yogiyo.oooservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.yogiyo.ooomodel.InteroooDAO;
import com.spring.yogiyo.ooomodel.oooBoardVO;
import com.spring.yogiyo.ooomodel.oooCommentVO;
import com.spring.yogiyo.ooomodel.oooVO;

@Service
public class oooservice implements Interoooservice {

   @Autowired
   private InteroooDAO dao;

   // 매장 등록 완료
   @Override
   public int addshop(oooVO ovo) {
      int n = dao.addshop(ovo);
      return n;
   }

   // 업종 카테고리 가져오기
   @Override
   public List<HashMap<String, String>> selectShopCategory() {
      List<HashMap<String, String>> shopCategoryList = dao.selectShopCategory();
      return shopCategoryList;
   }

   // 사업자 번호 가져오기
   @Override
   public int getMasterNo() {
      int masterno = dao.getMasterNo();
      return masterno;
   }

   // 글 1개를 보여주는 페이지 요청
   @Override
   public int getTotalCountWithNOsearch() {
      int count = dao.getTotalCountWithNOsearch();
      return count;

   }

   // 검색조건이 있을 경우
   @Override
   public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
      int count = dao.getTotalCountWithSearch(paraMap);
      return count;
   }

   //페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)
   @Override
   public List<oooBoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
      List<oooBoardVO> boardList = dao.boardListWithPaging(paraMap);
      return boardList;
   }
   
   //글쓰기(파일첨부가 없는 글쓰기)
   @Override
   public int add(oooBoardVO oooboardvo) {
      if(oooboardvo.getFk_seq() == null || oooboardvo.getFk_seq().trim().isEmpty()) {
         // 원글쓰기인 경우
         int groupno = dao.getGroupnoMax()+1;
         oooboardvo.setGroupno(String.valueOf(groupno));
      }
      
      
      /////////////////////////////////////////////////////////////////////////////////////////
      int n = dao.add(oooboardvo);
      return n;
   }
   
   // 글쓰기(파일첨부가 있는 글쓰기)
   @Override
   public int add_withFile(oooBoardVO oooboardvo) {
      if(oooboardvo.getFk_seq() == null || oooboardvo.getFk_seq().trim().isEmpty()) {
         // 원글쓰기인 경우
         int groupno = dao.getGroupnoMax()+1;
         oooboardvo.setGroupno(String.valueOf(groupno));
      }
      
      
      /////////////////////////////////////////////////////////////////////////////////////////
      int n = dao.add_withFile(oooboardvo);
      return n;
   }

   
   // 글 조회수 증가와 함께 글조회를 해주는 것
   @Override
   public oooBoardVO getView(String seq, String email) {
      
      oooBoardVO oooboardvo = dao.getView(seq);
      
      if(email != null && !oooboardvo.getFk_userid().equals(email)) {
         
         dao.setAddReadCount(seq); // 글조회수 증가하기
         oooboardvo = dao.getView(seq); //내가 쓰지않은 글을 읽었을떄는 글조회수 증가 후 보여주기
      }
      
      return oooboardvo;
   }
   
   // 글 조회수 증가없이 글조회를 해주는 것
   @Override
   public oooBoardVO getViewWithNoAddCount(String seq) {
      oooBoardVO ooobaordvo = dao.getView(seq);
      return ooobaordvo;
   }
   
   // 원게시물에 딸린 댓글 조회해오기
   @Override
   public List<oooCommentVO> getCommentList(String parentseq) {
      List<oooCommentVO> commentList = dao.getCommentList(parentseq);
      return commentList;
   }
   
   // 글 1개 수정하기
   @Override
   public int edit(oooBoardVO oooboardvo) {
      
      boolean bool = dao.checkPW(oooboardvo);
      if(!bool) return 0;
      else {
         int n = dao.updateBoard(oooboardvo);
         return 1;
      }   
   }
   
   // === #79. 1개 글 삭제하기(댓글쓰기 게시판인 경우 트랜잭션 처리를 해야한다.) === //
   @Override
   @Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
   public int del(oooBoardVO oooboardvo) throws Throwable {
      // 삭제하려고 하는 글에 대한 원래의 암호를 읽어와서 입력한 암호와 비교를 한다.
      boolean bool = dao.checkPW(oooboardvo);
      if(!bool)
         return 0;
      
      else {
         
         // === 댓글이 있는 게시판인 경우라면 === //
         // 원게시물에 딸린 모든 댓글들을 삭제하도록 한다. // 
         int m = 0;
         String strcommentCount = dao.getView(oooboardvo.getSeq()).getCommentCount();
         
         int commentCount = Integer.parseInt(strcommentCount);
         if(commentCount>0) {
            m = dao.deleteComment(oooboardvo.getSeq());
         }
         // 글 삭제한다.
         int n = dao.deleteBoard(oooboardvo);
                  
         int result = (m==0)?n:n*m;
         
         return n*m;
      }
   }
   
   // 댓글 작성하기
   @Override
   @Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
   public int addComment(oooCommentVO ooocommentvo) throws Throwable {
      
       int result = 0;
      int n = 0;
      
      n = dao.addComment(ooocommentvo);   // 댓글쓰기 (tblComment 테이블에 insert)
      
      if(n==1) {
         result = dao.updateCommentCount(ooocommentvo.getParentSeq());          // tblBoard 테이블에 commentCount컬럼의 값을 1증가(update)
      }
      return result;
   }
   
   // 검색어 입력시 자동글 완성하기
   @Override
   public List<String> wordSearchShow(HashMap<String, String> paraMap) {
      List<String> wordList = dao.wordSearchShow(paraMap);
      return wordList;
   }
   
   
   
   
   
   
   
   
}