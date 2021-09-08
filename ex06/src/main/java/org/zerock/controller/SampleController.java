package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {

	@GetMapping("/all")
	public void doAll() {
		
		log.info("다 접속 가능 URI");
	}
	
	@GetMapping("/member")
	public void doMember() {
		
		log.info("멤버 접속 URI");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		
		log.info("관리자 권한 접속");
	}
}
