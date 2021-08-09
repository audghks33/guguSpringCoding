package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Servlet의 ServletContext = 스프링 WebApplicationContext를 사용하기 위함임
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/**/appServlet/servlet-context.xml"})
//servlet-context.xml는 항상 원래패스대로 하면 에러가 나서 중간에 /**/로 패스 보정
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;//가짜 mvc를 생성한 것 처럼 해줌
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testList() throws Exception{
		
		log.info(
			mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
			.andReturn()
			.getModelAndView()
			.getModelMap());
		//boardList를 한번 돌려보겠다.
		
	}
	
	@Test
	public void testRegister() throws Exception{
		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "콘테스트 새글제목")
				.param("content", "콘테스트 새글내용")
				.param("writer", "콘테스트 새글등록자")
				).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	@Test
	public void testGet() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "6"))
				.andReturn().getModelAndView().getModelMap();
	}
	
	@Test
	public void testModify() throws Exception{
		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/modify")
				.param("bno", "6")
				.param("title", "콘수정테스트 새글제목")
				.param("content", "콘수정테스트 새글내용")
				.param("writer", "콘수정테스트 새글등록자"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
		
	}
	
	@Test
	public void testRemove() throws Exception{
		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/remove")
				.param("bno", "7"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
		
	}
	//p222
}
