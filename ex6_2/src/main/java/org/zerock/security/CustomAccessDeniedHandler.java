package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;


//access error를 원하는 대로 처리하는 방법
@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.access.AccessDeniedException accessDeniedException)
			throws IOException, ServletException {

	log.error("Access Denied Handler");
	
	log.error("redirect..........");
	
	response.sendRedirect("/accessError");
	
	}

	
}
