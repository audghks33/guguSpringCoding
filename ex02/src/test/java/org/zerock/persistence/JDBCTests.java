package org.zerock.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

//Oracle ���� Ȯ�� -> pom. xml DB�� Ŀ�ؼ��� �����ϴ� Ŀ�ؼ� Ǯ�� �������ش�.
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
