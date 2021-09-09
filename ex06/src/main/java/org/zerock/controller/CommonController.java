package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {

	@GetMapping("accessError")
	public void accessDenied(Authentication auth, Model model) {
		
		log.info("access Denied : " + auth );
		
		model.addAttribute("msg" ,  "접속 거부");
	}
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		
		log.info("error : "+error);
		log.info("logout : " + logout);
		
		if(error != null) {
			model.addAttribute("error", "로그인 에러 계정확인 부탁");
		}
		if(logout != null) {
			model.addAttribute("logout", "로그아웃");
		}
	}
	@GetMapping("/customLogout")
	public void logoutGET() {
		
		log.info("custom logout");
	}
}
