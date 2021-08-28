package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;
	//uploadController uploadAjaxPost를 수정
}
