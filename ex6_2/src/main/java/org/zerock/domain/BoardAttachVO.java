package org.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
// 첨부파일 관련 VO
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
	
	private Long bno;
}
