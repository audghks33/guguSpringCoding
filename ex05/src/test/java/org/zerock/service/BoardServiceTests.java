package org.zerock.service;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

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
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod =  @__({@Autowired}))
	private BoardService service;
	
	/*
	 * @Test public void testExist() {
	 * 
	 * log.info(service); assertNotNull(service); } //�׽�Ʈ �Ϸ� �� ��� �۾��� ������ �׽�Ʈ�� ���� �ٽ�
	 * boardServiceImpl
	 * 
	 * @Test public void testRegister() {
	 * 
	 * BoardVO board = new BoardVO(); board.setTitle("���� ���۱�");
	 * board.setContent("���񽺻��۳���"); board.setWriter("���� ���� ����");
	 * 
	 * service.register(board);
	 * 
	 * log.info("������� �Խù��� ��ȣ  " +board.getBno()); }
	 * 
	 * 
	 * @Test public void testGet() { log.info("������"+service.get(1L)); }
	 * 
	 * @Test public void testDelete() {
	 * 
	 * log.info("���� ��. " + service.remove(2L));
	 * 
	 * }
	 * 
	 * @Test public void testUpdate() {
	 * 
	 * BoardVO board = service.get(1L);
	 * 
	 * if(board == null) { return; }
	 * 
	 * board.setTitle("���� �����Ѵ�."); board.setContent("���񽺳���ٲ�"); log.info(" ���� �ٲ�" +
	 * service.modify(board)); }
	 */
	
	// 10������ 
	
	@Test
	public void testGetList() {
//		service.getList().forEach(board ->log.info(board));
		service.getList(new Criteria(3,10)).forEach(board-> log.info(board));
	}
}