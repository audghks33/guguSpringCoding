package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod =  @__({@Autowired}))
	private BoardService service;
	
	@Test
	public void testExist() {
		
		log.info(service);
		assertNotNull(service);
	}
	//테스트 완료 후 등록 작업의 구현과 테스트를 위해 다시 boardServiceImpl
	
	@Test
	public void testRegister() {
		
		BoardVO board = new BoardVO();
		board.setTitle("서비스 새작글");
		board.setContent("서비스새작내용");
		board.setWriter("서비스 새작 쓴놈");
		
		service.register(board);
		
		log.info("만들어진 게시물의 번호  " +board.getBno());
	}
	
	@Test
	public void testGetList() {
		service.getList().forEach(board ->log.info(board));
	}
	
	@Test
	public void testGet() {
		log.info("섭스겟"+service.get(1L));
	}
	
	@Test
	public void testDelete() {
		
		log.info("삭제 됨. " + service.remove(2L));
		
	}
	
	@Test
	public void testUpdate() {
		
		BoardVO board = service.get(1L);
		
		if(board == null) {
			return;
		}
		
		board.setTitle("제목 수정한다.");
		board.setContent("서비스내용바뀜");
		log.info(" 대충 바뀜"  + service.modify(board));
	}
	
	// 10강으로 
}
