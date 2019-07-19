package com.spring.yogiyo.kkkservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.yogiyo.kkkmodel.InterKkkDAO;

@Service
public class KkkService implements InterKkkService {

	// === #34. 의존객체 주입하기 ( DI : Dependency Injection ) ====
		@Autowired
		private InterKkkDAO dao;
	
}
