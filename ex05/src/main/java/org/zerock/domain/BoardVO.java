package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	
	private long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	//���� mapper interface�� mapper xml
	
	private int replyCnt;
	
	//BoardVO 에서 등록 시 BoardAttachVO 처리 가능하도록 추가
	private List<BoardAttachVO> attachList;
	//boardAttachMapper
	
}
