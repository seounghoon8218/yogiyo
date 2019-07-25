package com.spring.yogiyo.oooservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.yogiyo.ooomodel.oooVO;


public interface Interoooservice {

   int addshop(oooVO ovo);   // 매장 등록 완료

   List<HashMap<String, String>> selectShopCategory(); // 업종 카테고리 가져오기

   int getMasterNo(); // 사업자 번호 가져오기

}