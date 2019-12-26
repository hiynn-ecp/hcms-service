package com.hiynn.cms.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求耗时 aop
 *
 * @author xuxitan
 * @date 2019/10/8 17:34
 **/
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(* com.hiynn.cms.controller..*(..))")
    private void controllerAspect() {
    }

    @Around(value = "controllerAspect()")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        assert request != null;

        //计算方法执行时间
        StopWatch sw = new StopWatch();
        sw.start();
        Object obj = pjp.proceed();
        sw.stop();
        log.info("[{}][{}]-耗时:{}ms", request.getMethod(), request.getRequestURI(), sw.getTime());

        return obj;
    }
}
