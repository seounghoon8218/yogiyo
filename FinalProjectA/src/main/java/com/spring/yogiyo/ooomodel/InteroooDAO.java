package com.spring.yogiyo.ooomodel;

import java.util.HashMap;
import java.util.List;

public interface InteroooDAO {

   int addshop(oooVO ovo); // 매장 등록

   List<HashMap<String, String>> selectShopCategory(); // 업종카테고리 가져오기

    int getMasterNo();  // 사업자 번호 가져오기

   int getTotalCountWithNOsearch(); // 검색조건이 없을 경우

   int getTotalCountWithSearch(HashMap<String, String> paraMap); // 검색 조건이 있을 경우

   List<oooBoardVO> boardListWithPaging(HashMap<String, String> paraMap); //페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)

   int getGroupnoMax();  // tbl_oooBoard 테이블에서 groupno 컬럼의 최대값 구하기

   int add(oooBoardVO oooboardvo); //  글쓰기(파일첨부가 없는 글쓰기)
   int add_withFile(oooBoardVO oooboardvo); // 글쓰기(파일첨부가 있는 글쓰기)

   oooBoardVO getView(String seq); // 1개 글 보여주기

   void setAddReadCount(String seq); // 글조회수 증가하기

   boolean checkPW(oooBoardVO oooboardvo); // 글 수정 및 글 삭제시 암호 일치여부 알아오기

   int updateBoard(oooBoardVO oooboardvo); // 글 수정하기
   
   List<oooCommentVO> getCommentList(String parentseq); // 원게시물에 딸린 댓글 조회해오기      
   
   int deleteBoard(oooBoardVO oooboardvo); // 글 1개를 삭제하기

   int addComment(oooCommentVO ooocommentvo); // 댓글쓰기
   int deleteComment(String seq); // 원게시물에 딸린 모든 댓글들 삭제하기
   int updateCommentCount(String parentSeq); // tblBoard 테이블에 commentCount컬럼의 값을 1증가(update)

   List<String> wordSearchShow(HashMap<String, String> paraMap);  // 검색어 입력시 자동글 완성하기
    
    
}