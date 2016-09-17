/**
 * Copyright (C) 2015-2020 gome meixin_search Inc.All Rights Reserved.
 * 
 * FileName:SearchPlatformLogAspector.java
 *
 * Description：简要描述本文件的内容
 *
 * History：
 * 版本号           作者                  日期               简要介绍相关操作
 *  1.0  liuyuxin  2016年1月21日
 *
 */
package com.kuaikanwang.email.utils.log;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;


@Aspect
public class SearchPlatformLogAspector {
    private static final Logger logger = LoggerFactory.getLogger(SearchPlatformLogAspector.class);

    /**
     * 
     * @Title：doBeforeInServiceLayer
     * @Description: 方法调用前触发 记录开始时间
     * @param joinPoint
     */
    @Before("execution(* com.kuaikanwang.image.controller..*.*(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {

    }

    /**
     * 
     * @Title：doAfterInServiceLayer
     * @Description: 方法调用后触发 记录结束时间
     * @param joinPoint
     */
    @After("execution(* com.kuaikanwang.image.controller..*.*(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {

    }

    /**
     * 
     * @Title：doAround
     * @Description: 环绕触发
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.kuaikanwang.image.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 获取输入参数
        Map<?, ?> inputParamMap = request.getParameterMap();
        // 获取请求地址
        String requestPath = request.getRequestURI();

        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        Map<String, Object> outputParamMap = new HashMap<String, Object>();
        Object result = pjp.proceed();// result的值就是被拦截方法的返回值
        outputParamMap.put("output", result);
        long endTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
        logger.warn("\n url={"+requestPath+"};exe_time={"+(endTimeMillis - startTimeMillis)+"}ms;input={"+JSON.toJSONString(inputParamMap)+"};\n output={"+processRealOutput(JSON.toJSONString(outputParamMap))+"}");
//        logger.info("\n url={};exe_time={}ms;input={};\n output={}", requestPath, (endTimeMillis - startTimeMillis),
//                JSON.toJSONString(inputParamMap), processRealOutput(JSON.toJSONString(outputParamMap)));
        return result;
    }

    private String processRealOutput(String output) {
        if (output.length() > getOutputLength()) {
            StringBuilder sb = new StringBuilder();
            sb.append(output.substring(0, outputLength));
            sb.append("...more ").append(output.length() - getOutputLength()).append(" chars has been omitted.");
            return sb.toString();
        }
        return output;
    }

    private int outputLength = 1000;

    public int getOutputLength() {
        return outputLength;
    }

    public void setOutputLength(int outputLength) {
        this.outputLength = outputLength;
    }

}
