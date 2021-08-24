package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {

	@Setter(onMethod = @__({ @Autowired}))
	private Sample1Mapper mapper1;
	
	@Setter(onMethod = @__({ @Autowired}))
	private Sample2Mapper mapper2;
	
	// delete tbl_sample1
	// delete tbl_sample2  +commit으로 테이블을 지웅더라도 테스트 코드 실행시 rollback아혀 실행 됨을 알 수 있음
	// 정작 다 끝나고 롤백이라 내용은 안들어가있음.
	@Transactional
	@Override
	public void addData(String value) {
		
		log.info("mapper.....................................");
		mapper1.insertCol1(value);
		
		log.info("mapper2................................");
		mapper2.insertCol2(value);
		
		log.info("end...............................");
		
	}
}
