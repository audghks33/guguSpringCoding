package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

	@GetMapping(value ="/getText", produces="text/plain; charset=UTF-8")
	public String getText() {

		log.info("MIME TYPE:  "+ MediaType.TEXT_PLAIN_VALUE );

		return "안녕하세요";
		//@Controller는 문자열반환의 경우 jsp파일이름이 되지만 @RestController는 순수한 데이터
		//객체를 반환하는 작업은 JSON이나 XML을 이용함. org.zerock.domain - SampleVO
	}

	@GetMapping(value="/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	//produces는 꼭 지정 안해줘도 됨.
	public SampleVO getSample() {

		return new SampleVO(112,"스타","로드");
		//서버로 /sample/getSample을 띄우면 xml데이터를 확인 가능
		//						.json을 호출하면 데이터가 전달됨을 확인가능
	}

	@GetMapping(value="/getList")
	public List<SampleVO> getList(){

		return IntStream.range(1, 10).mapToObj(i->new SampleVO(i,i+ "first", i+"last"))
				.collect(Collectors.toList());
		//http://localhost:8080/sample/getList or +.json
	}

	@GetMapping(value="/getMap")
	public Map<String, SampleVO> getMap(){

		Map<String, SampleVO> map =new HashMap<>();
		map.put("First", new SampleVO(111,"그루트","주니어"));

		return map;
	}

	//ResponseEntity 데이터와 Http 헤더의 상태 메시지 등을 같이 전달하는 용도
	// HTTP 상태 코드와 에러 메시지 등을 함께 전달할 수 있어서 받는 입장에서 확실하게 결과를 알 수 있게 해줌
	@GetMapping(value="/check", params = {"height", "weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){

		SampleVO vo = new SampleVO(0,""+height,""+weight);

		ResponseEntity<SampleVO> result = null;

		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}

		return result;
		//http://localhost:8080/sample/check?height=110&weight=130
	}

	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(
		@PathVariable("cat") String cat,
		@PathVariable("pid") Integer pid){

			return new String[] {"category: " + cat, "productid: " + pid};
			//http://localhost:8080/sample/product/bags/1234
	}

	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {

		log.info("convert.....ticket  " + ticket);

		return ticket;
		//post이므로 약간 테스트 방식이 약간 다름
		//관련 테스트는 Rest테스트 학습 이후
		// test/java - SampleControllerTests
	}
	
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
	
	
	///스프링 시큐리티 설정
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping
	public void doMember2() {
		
		log.info("logined annotation member");
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		
		log.info("admin annotation only");
	}

}