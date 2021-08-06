package org.zerock.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	
	@Setter(onMethod = @__({@Autowired}))
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		log.info(timeMapper.getClass().getName());
		log.info(timeMapper.getTime());
	}
	/*
	 * src/main/resource mapper 폴더를 만들어서 mapper xml를 따로 관리해주는 것이
	 * sql문 관리가 훨씬 용이해짐 xml 생성 후 TimeMapper 인터페이스에 관련 인자 추가
	 */
	
	@Test
	public void testGetTime2() {
		log.info("getTime2");
		log.info(timeMapper.getTime2());
	}
	
	/*
	 *  sql에 전달되는 파라미터는 JDBC에서 ? 로 나오기에 이를 확인하는 기능 추가
	 *  pom.xml -> log4jdbc-log4j2-jdbc4
	 */
	
	
}
