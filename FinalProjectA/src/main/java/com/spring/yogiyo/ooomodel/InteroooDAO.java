package com.spring.yogiyo.ooomodel;

import java.util.HashMap;
import java.util.List;

public interface InteroooDAO {

   int addshop(oooVO ovo); // 매장 등록

   List<HashMap<String, String>> selectShopCategory(); // 업종카테고리 가져오기

    int getMasterNo();  // 사업자 번호 가져오기

   
   
}