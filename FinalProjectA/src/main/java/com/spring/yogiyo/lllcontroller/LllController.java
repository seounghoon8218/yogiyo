package com.spring.yogiyo.lllcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.spring.yogiyo.lllservice.InterLllService;

//=== #30. 컨트롤러 선언 ===
@Controller
public class LllController {
	
	// === #35. 의존객체 주입하기(DI: Dependency Injection) ===
		@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
		private InterLllService service;
		
	
}
