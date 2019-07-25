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
}