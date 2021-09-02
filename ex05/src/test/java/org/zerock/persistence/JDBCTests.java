package org.zerock.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

//Oracle 연결 확인 -> pom. xml DB와 커넥션을 관리하는 커넥션 풀을 설정해준다.
@Log4j
public class JDBCTests {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		
		try (
			Connection con =
			DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE",
					"book_ex",
					"qw12as")){
					
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
