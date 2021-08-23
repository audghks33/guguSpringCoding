package org.zerock.aop;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {

	//BeforeAdvice 구현메서드
	@Before("execution(* org.zerock.service.SampleService*.*(..))") 
	public void logBefore() {
		
		log.info("===================================");
	}
	//root-context.xml에서 aop, context 추가
	
	//해당 메서드에 전달되는 파라미터가 무엇인지 기록하거나, 예외가 발생했을 때 어떤 파라미터에 문제가 있는지 알고 싶은 경우
	//너무 많은 파라미터에는 && 가 편치 못해서 @Around와 ProceedingJoinPoint를 후에 이용할 것임
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String,String)) && args(str1,str2)")
	public void logBeforeWithParam(String str1,String str2) {
		
		log.info("str1: " +str1);
		log.info("str2: " +str2);
	}
	
	@AfterThrowing(pointcut= "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
	public void logExcpetion(Exception exception) {
		
		log.info("exception..........!!!!!!!");
		log.info("excpetion : "+ exception);
	}
	
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime( ProceedingJoinPoint pjp) {
		
		long start = System.currentTimeMillis();
		
		log.info("Target :  " + pjp.getTarget());
		log.info("param : " + Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		
		try {
			result = pjp.proceed();
		} catch(Throwable e) {
			e.printStackTrace();
			
		}
		
		long end = System.currentTimeMillis();
		log.info("Time:  " +(end- start));
		
		return result;
		
	}
}
