package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {

		@Setter(onMethod=@__({@Autowired}))
		private BoardMapper mapper;
		
		/*
		 * @Test public void testGetList() {
		 * mapper.getList().forEach(board->log.info(board)); } //log Ȯ�� ���� main/resource
		 * org/zerock/mapper BoardMapper.xml����
		 * 
		 * @Test public void testInsert() {
		 * 
		 * BoardVO board = new BoardVO(); board.setTitle("���۱�");
		 * board.setContent("���ۺ�"); board.setWriter("���ξ���");
		 * 
		 * mapper.insert(board);
		 * 
		 * log.info(board); }
		 * 
		 * @Test public void testInsertSelectKey() {
		 * 
		 * BoardVO board = new BoardVO(); board.setTitle("���۱� sel");
		 * board.setContent("���ۺ� sle"); board.setWriter("���ξ��� sle");
		 * 
		 * mapper.insertSelectKey(board);
		 * 
		 * log.info(board); } //read ó�� mapper BoardMapper
		 * 
		 * @Test public void testRead() { BoardVO board = mapper.read(5L);
		 * 
		 * log.info("long "+board); }
		 * 
		 * @Test public void testDelete() {
		 * 
		 * 
		 * log.info("�����" +mapper.delete(5L)); }
		 * 
		 * @Test public void testUpdate() {
		 * 
		 * BoardVO board =new BoardVO(); board.setBno(6L);
		 * board.setTitle("���۱� 222222sel"); board.setContent("���ۺ� 2222sle");
		 * board.setWriter("�����ȳ�");
		 * 
		 * int count = mapper.update(board); log.info("���� ī��Ʈ" + count);
		 * 
		 * }
		 */
		/*
		 * @Test public void testPaging() {
		 * 
		 * Criteria cri = new Criteria(3,30); // ���� �׽�Ʈ �� BoardService Controller
		 * ����
		 * 
		 * List<BoardVO> list = mapper.getListWithPaging(cri);
		 * 
		 * list.forEach(board->log.info(board)); }
		 */
		
		@Test
		public void testSearch() {
			
			Criteria cri = new Criteria();
			cri.setKeyword("서비스");
			cri.setType("TC");
			
			List<BoardVO> list = mapper.getListWithPaging(cri);
			list.forEach(board -> log.info(board));
			
		}
}
