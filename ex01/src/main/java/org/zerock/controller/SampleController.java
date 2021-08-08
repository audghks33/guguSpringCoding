package org.zerock.controller;



import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;
// 시작

@RequestMapping("/sample/*")
@Log4j
@Controller
public class SampleController {
	
	/*
	 * @InitBinder //요청에 대한 추가적인 설정을 하고 싶을때 사용가능 public void
	 * initBinder(WebDataBinder binder) { SimpleDateFormat dataFormet = new
	 * SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(java.util.Date.class,new
	 * CustomDateEditor(dataFormet, false)); }
	 * 
	 * @DateTimeFormat(pattern ="yyyy/MM/dd")을 변수에 직접주면 필요없다.
	 */

	@RequestMapping("")
	public void basic() {
		
		
		log.info("basic.......................");
	}
	
	@GetMapping("/basicOnlyGet")
	//get 이나 post를 구분해서 사용 가능 RequestMapping 의 구분형
	// 이 후 domain->sampelDTO
	
	public void basicGet2() {
		log.info("basic get only get......................");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info(""+dto);
		
		return "ex01";
		//http://localhost:8080/sample/ex01?name=aaaa&age=2222
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name,
			@RequestParam("age") int age) {
		log.info("name "+name);
		log.info("age "+age);
		
		return "ex02Array";
		//http://localhost:8080/sample/ex02?name=aaaa&age=2222
	}
	
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids")ArrayList<String> ids) {
		
		log.info("ids: " + ids );
		
		return "ex02List";
		//http://localhost:8080/sample/ex02List?ids=111&ids=222&ids=33
			
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		
		log.info("array ids : "+ Arrays.toString(ids));
		
		return "ex02Array";
		//http://localhost:8080/sample/ex02Array?ids=ssssss&ids=gggg&ids=rrrr
		//SampleDTOList
	}
	
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos: "+ list);
		
		return "ex02Bean";
		//http://localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaaa&list%5B1%5D.name=bbbbbb
		// [  = %5B ] = %5D
		//TodoDTO
	}
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		
		log.info("todo: "+todo);
		return "ex03";
		//http://localhost:8080/sample/ex03?title=tttt&dueDate=1999-01-01
		//http://localhost:8080/sample/ex03?title=tttt&dueDate=1999/01/01
	}
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, int page
			//@ModelAttribute("page") int page
			//
			) {
		
		log.info("dto :"+dto);
		log.info("page :" + page);
		
		return "/sample/ex04" ;
		//화면단까지 가져간다고 하면 .jsp파일의 이름을 리턴해줌
		//기본 자료형은 화면단까지 전달이 되지 않음. sampleDTO는 나오나 int page는 나오지 않음
		//그래서 @ModelAttribute를 사용함
		//http://localhost:8080/sample/ex04?name=ttttt&age=2323&page=4444
		
		/*Controller 리턴타입
		 * String 따로 파일이름을 지정할떄 redirect:이동하지만 내용을 안가지는 ,forward:이동하지만 내용을가지는
		 * url과 통일한 이름을 호출할때
		 * VO, DTO : json타입 변환용도
		 * ResponseEntity: response할때 http헤더정보와 내용가공용도
		 * Model,ModelAndView: 데이터 변환 및 화면까지
		 * HttpHeaders: 응답내용 없이 Http 헤더 메시지만 전달하는 용도
		 * */
		
		//Controller의 메서드 리턴 타입을 VO나 DTO등 복잡하게 하기 위해서는 jackson-databind 라이브러리가 필요하다
		//pom.xml -> 이후 ex06
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06..............................");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
		//http://localhost:8080/sample/ex06
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07..........");

		// {"name": "홍길동"}
		String msg = "{\"name\": \"홍길동\"}";

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		// header의 UTF-8 설정을 해주어 한글이 나올 수 있게 끔 함.

		return new ResponseEntity<>(msg, header, HttpStatus.OK);
		////http://localhost:8080/sample/ex07
		//파일업로드처리로 pom.xml
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload.................");
		
		//view/sample/exUpload.jsp
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file->{
			log.info("....................");
			log.info("name: " + file.getOriginalFilename());
			log.info("size: " +file.getSize());
			//4040뜨고 로그만 확인
			//exception test -> commonExceptionAdvice
		});
	}
	
	
}
