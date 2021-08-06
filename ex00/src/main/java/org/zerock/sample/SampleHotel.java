package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.Data;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data

/*
 * @Component
 * @Data
 * @AllArgsConstructor 로 SampleHotel(Chef chef) 메소드를 없앨 수 있음.
 * --- 특정 변수를 쓰고 싶을 경우 @RequiredArgsConstructor로하고
 * 해당 변수 @NonNull or final이 붙은 인스턴스 변수에 대한 생성자를 만들어냄
 */
public class SampleHotel {
	
	private Chef chef;
	
	////////////////////////////////////////// @AllArgsConstructor을 쓸 시 생략
	public SampleHotel(Chef chef) {
		this.chef = chef;
	}
	///////////////////////////////////////////
}
