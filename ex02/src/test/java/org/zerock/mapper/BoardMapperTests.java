package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {

		@Setter(onMethod=@__({@Autowired}))
		private BoardMapper mapper;
		
		@Test
		public void testGetList() {
			mapper.getList().forEach(board->log.info(board));
		}
		//log 확인 이후 main/resource org/zerock/mapper BoardMapper.xml생성

		@Test
		public void testInsert() {
			
			BoardVO board = new BoardVO();
			board.setTitle("새작글");
			board.setContent("새작본");
			board.setWriter("새로쓴놈");
			
			mapper.insert(board);
			
			log.info(board);
		}
		
		@Test 
		public void testInsertSelectKey() {
			
			BoardVO board = new BoardVO();
			board.setTitle("새작글 sel");
			board.setContent("새작본 sle");
			board.setWriter("새로쓴놈 sle");
			
			mapper.insertSelectKey(board);
			
			log.info(board);
		}
		//read 처리 mapper BoardMapper

		@Test
		public void testRead() {
			BoardVO board = mapper.read(5L);
			
			log.info("long "+board);
		}
		
		@Test
		public void testDelete() {
			
			
			log.info("삭삭삭삭" +mapper.delete(5L));
		}
		
		@Test
		public void testUpdate() {
			
			BoardVO board =new BoardVO();
			board.setBno(6L);
			board.setTitle("새작글 222222sel");
			board.setContent("새작본 2222sle");
			board.setWriter("수정된놈");
			
			int count = mapper.update(board);
			log.info("업뎃 카운트" + count);
			
		}
}
