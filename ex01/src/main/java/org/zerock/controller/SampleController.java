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
// ����

@RequestMapping("/sample/*")
@Log4j
@Controller
public class SampleController {
	
	/*
	 * @InitBinder //��û�� ���� �߰����� ������ �ϰ� ������ ��밡�� public void
	 * initBinder(WebDataBinder binder) { SimpleDateFormat dataFormet = new
	 * SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(java.util.Date.class,new
	 * CustomDateEditor(dataFormet, false)); }
	 * 
	 * @DateTimeFormat(pattern ="yyyy/MM/dd")�� ������ �����ָ� �ʿ����.
	 */

	@RequestMapping("")
	public void basic() {
		
		
		log.info("basic.......................");
	}
	
	@GetMapping("/basicOnlyGet")
	//get �̳� post�� �����ؼ� ��� ���� RequestMapping �� ������
	// �� �� domain->sampelDTO
	
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
		//ȭ��ܱ��� �������ٰ� �ϸ� .jsp������ �̸��� ��������
		//�⺻ �ڷ����� ȭ��ܱ��� ������ ���� ����. sampleDTO�� ������ int page�� ������ ����
		//�׷��� @ModelAttribute�� �����
		//http://localhost:8080/sample/ex04?name=ttttt&age=2323&page=4444
		
		/*Controller ����Ÿ��
		 * String ���� �����̸��� �����ҋ� redirect:�̵������� ������ �Ȱ����� ,forward:�̵������� ������������
		 * url�� ������ �̸��� ȣ���Ҷ�
		 * VO, DTO : jsonŸ�� ��ȯ�뵵
		 * ResponseEntity: response�Ҷ� http��������� ���밡���뵵
		 * Model,ModelAndView: ������ ��ȯ �� ȭ�����
		 * HttpHeaders: ���䳻�� ���� Http ��� �޽����� �����ϴ� �뵵
		 * */
		
		//Controller�� �޼��� ���� Ÿ���� VO�� DTO�� �����ϰ� �ϱ� ���ؼ��� jackson-databind ���̺귯���� �ʿ��ϴ�
		//pom.xml -> ���� ex06
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06..............................");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("ȫ�浿");
		
		return dto;
		//http://localhost:8080/sample/ex06
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07..........");

		// {"name": "ȫ�浿"}
		String msg = "{\"name\": \"ȫ�浿\"}";

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		// header�� UTF-8 ������ ���־� �ѱ��� ���� �� �ְ� �� ��.

		return new ResponseEntity<>(msg, header, HttpStatus.OK);
		////http://localhost:8080/sample/ex07
		//���Ͼ��ε�ó���� pom.xml
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
			//4040�߰� �α׸� Ȯ��
			//exception test -> commonExceptionAdvice
		});
	}
	
	
}
