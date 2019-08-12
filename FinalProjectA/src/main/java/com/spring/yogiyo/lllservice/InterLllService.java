package com.spring.yogiyo.lllservice;

import java.util.HashMap;
import java.util.List;

import com.spring.yogiyo.lllmodel.CommentVO;
import com.spring.yogiyo.lllmodel.MoonVO;

public interface InterLllService {

   int moonadd(MoonVO moonvo); // 글쓰기(파일첨부가 없는 글쓰기) 

   int getTotalCountWithNOsearch(); // 검색조건이 없을 경우의 총 게시물 건수(totalCount)

   int getTotalCountWithSearch(HashMap<String, String> paraMap); // 검색조건이 있을 경우의 총 게시물 건수(totalCount) 

   List<MoonVO> boardListWithPaging(HashMap<String, String> paraMap); // 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)

   MoonVO getView(String seq, String email);

   MoonVO getViewWithNoAddCount(String seq);

   List<CommentVO> getCommentList(String seq);

   int edit(MoonVO moonvo);

   int del(MoonVO moonvo) throws Throwable;

   int addComment(CommentVO commentvo) throws Throwable;

   List<String> wordSearchShow(HashMap<String, String> paraMap);
   
}
   
   