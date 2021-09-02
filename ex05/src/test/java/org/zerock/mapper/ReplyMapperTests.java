package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//SpringJUnit4ClassRunner 나 같다. SpringRunner가 상속 받고 있음.
@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
//Java Config
//@ContextConfiguration(classes={org.zerock.config.RootConfig.class})
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = {
			753681L,
			753674L,
			753673L,
			753672L,
			753671L,
			753670L };
	
	
	@Setter(onMethod_ = {@Autowired})
	private ReplyMapper mapper;
	
	@Test
	public void testMapper( ) {
		log.info(mapper);
	}
	//확인 후 CRUD 작업 Mapper 순회
	
	/*
	 * @Test public void testCreate() {
	 * 
	 * IntStream.rangeClosed(1,10).forEach(i ->{
	 * 
	 * ReplyVO vo = new ReplyVO();
	 * 
	 * vo.setBno(bnoArr[ i % 5 ]); vo.setReply("댓 테스트 " + i );
	 * vo.setReplyer("리플작성자 " + i);
	 * 
	 * mapper.insert(vo); }); // 이후 조회 처리 read }
	 */
	/*
	 * @Test public void testRead() {
	 * 
	 * Long targetRno = 5L;
	 * 
	 * ReplyVO vo = mapper.read(targetRno);
	 * 
	 * log.info(vo); }
	 */
	
	/*
	 * @Test public void testDelete() {
	 * 
	 * Long targetRno = 1L;
	 * 
	 * mapper.delete(targetRno);
	 * 
	 * }
	 */
	/*
	 * @Test public void testUpdate() {
	 * 
	 * Long targetRno = 10L;
	 * 
	 * ReplyVO vo = mapper.read(targetRno);
	 * 
	 * vo.setReply("update reply com");
	 * 
	 * int count = mapper.update(vo);
	 * 
	 * log.info("업데이트 카운트 :  "+ count); }
	 */
	/*
	 * @Test public void testList() {
	 * 
	 * Criteria cri= new Criteria();
	 * 
	 * List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
	 * 
	 * replies.forEach(reply -> log.info(reply)); }
	 */
	//서비스 영역과 controller 처리
	
	@Test
	public void testList2() {
		
		Criteria cri =new Criteria(1,10);
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 753681L);
	}
}
