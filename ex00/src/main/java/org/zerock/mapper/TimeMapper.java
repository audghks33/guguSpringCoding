package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

	@Select("select sysdate From dual")
	public String getTime();
	/*
	 * mapper 가 인식 가능하도록 root-context 추가 설정 필요
	 */
	
	public String getTime2();
}

