
package com.csc.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ApplicationLoggerAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Pointcut("within(com.csc.controller..*)")
	public void definePackagePointcuts() {
		// empty method just to name the location specified in the pointcut
	}


	@Before("definePackagePointcuts()")
    public void logBefore (){
        log.debug("before debug log --------------------");
    }


    @After("definePackagePointcuts()")
    public void logAfter (){
        log.debug("After debug log --------------------");

    }


	@Around("definePackagePointcuts()")
	public Object logAround(ProceedingJoinPoint jp) {
		log.debug(" \n \n \n ");
		log.debug("************ Before Method Execution ************ \n {}.{} () with arguments[s] = {}",
				jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
		log.debug("_________________________________________________ \n \n \n");

		Object o = null;

		try {
			o = jp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.debug("************ After Method Execution ************ \n {}.{} () with arguments[s] = {}",
				jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
		log.debug("_________________________________________________ \n \n \n");

		return o;
	}
}

