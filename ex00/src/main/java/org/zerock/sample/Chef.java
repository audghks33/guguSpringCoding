package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
//스프링에게 해당 클래스가 관리대상임을 알려줌
@Data
// setter , toString() 등 자동으로 생성해줌
public class Chef {

}
