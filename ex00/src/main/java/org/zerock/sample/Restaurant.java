package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
@Data
public class Restaurant {
	
	/*
	Setter(onMethod_= @Autowired)
	@Setter(onMethod=@__({@Autowired})) (언더바가 2개)
	의 차이는 후자가 더 최신
	
	Setter에는 value, onMethod, onParam이 가능하고
	value는 접근제한, onParam은 메서드의 파라미터에 이노테이션을 사용하는 경우
	*/
	@Setter(onMethod=@__({@Autowired}))
	private Chef chef;
	
	
}	
