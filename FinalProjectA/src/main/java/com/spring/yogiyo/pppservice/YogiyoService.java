package com.spring.yogiyo.pppservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.pppmodel.InterYogiyoDAO;

//#31. Service 선언
@Service
public class YogiyoService implements InterYogiyoService {

	// === #34. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired
	private InterYogiyoDAO dao;
	
}
