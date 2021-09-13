package org.zerock.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTests {

		@Setter(onMethod=@__({@Autowired}))
		private DataSource dataSource;
		
		@Setter(onMethod= @__({@Autowired}))
		private SqlSessionFactory sqlSessionFactory;
		
//		@Test
//		public void testConnection() {
//			
//			try (Connection con = dataSource.getConnection()) {
//				log.info(con);
//			} catch (Exception e) {
//				fail(e.getMessage());
//			}
//		}
		
		@Test 
		public void testMyBatis() {
			try(SqlSession session = sqlSessionFactory.openSession();
					Connection con = session.getConnection()) {
				
				log.info(session);
				log.info(con);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
		
		/*
		 * Mapper를 작성하여 SQL 처리를 용하게함. mapper 패키지 생성 후 TimeMapper생성
		 */
		
}
