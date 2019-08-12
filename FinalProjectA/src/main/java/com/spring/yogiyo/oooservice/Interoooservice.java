package com.spring.yogiyo.oooservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.yogiyo.ooomodel.oooBoardVO;
import com.spring.yogiyo.ooomodel.oooCommentVO;
import com.spring.yogiyo.ooomodel.oooVO;

public interface Interoooservice {

   int addshop(oooVO ovo); // 매장 등록 완료

   List<HashMap<String, String>> selectShopCategory(); // 업종 카테고리 가져오기

   int getMasterNo(); // 사업자 번호 가져오기

   int getTotalCountWithNOsearch(); // 검색조건이 없을 경우

   int getTotalCountWithSearch(HashMap<String, String> paraMap); // 검색 조건이 있을 경우

   List<oooBoardVO> boardListWithPaging(HashMap<String, String> paraMap); // 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)

   int add(oooBoardVO oooboardvo); // 글쓰기(파일첨부가 없는 글쓰기) 

   int add_withFile(oooBoardVO oooboardvo); // 글쓰기(파일첨부가 있는 글쓰기)

   oooBoardVO getView(String seq, String email); // 글 조회수 증가와 함께 글조회를 해주는 것

   oooBoardVO getViewWithNoAddCount(String seq); // 글 조회수 증가없이 글조회해주는 것

   List<oooCommentVO> getCommentList(String parentseq); // 원게시물에 딸린 댓글들을 조회해오는 것

   int edit(oooBoardVO oooboardvo); // 글 수정하기

   int del(oooBoardVO oooboardvo) throws Throwable; // 글 삭제하기

   int addComment(oooCommentVO ooocommentvo) throws Throwable; // 댓글쓰기

   List<String> wordSearchShow(HashMap<String, String> paraMap); // 검색어 입력시 자동글 완성하기
   
   
   
   
   
   
    
   
   
   
   
   
   
   

}