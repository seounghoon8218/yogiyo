package com.spring.yogiyo.oooservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.ooomodel.InteroooDAO;
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
    
   
   
   
   
   
   
   
   
   
   
   
   
}