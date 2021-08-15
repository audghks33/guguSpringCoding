package org.zerock.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

	private int pageNum;
	private int amount;
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		// 몇번째 페이지에 몇개 ex 3,30 이면 31~60 의 데이터
		this.pageNum = pageNum;
		this.amount = amount;
	}
	// mapper-BoardMapper getListWithPaging() 메서드 작성
}
