package org.zerock.domain;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		// ���° �������� � ex 3,30 �̸� 31~60 �� ������
		this.pageNum = pageNum;
		this.amount = amount;
	}
	public String[] getTypeArr() {
		
		return type == null? new String[] {}: type.split("");
	//BoardMapper.xml
	}
	
	// mapper-BoardMapper getListWithPaging() �޼��� �ۼ�
	
	public String getListLink() {
		// 웹페이지에서 매번 파라미터를 유지하는 일이 번거롭고 힘들때.-> boardController modify
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
		.queryParam("pageNum", this.getPageNum())
		.queryParam("amount", this.getAmount())
		.queryParam("type",this.getType())
		.queryParam("keyword",this.getKeyword());
		
		return builder.toUriString();
		
	}
}
