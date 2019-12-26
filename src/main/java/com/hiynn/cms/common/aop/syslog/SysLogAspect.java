package com.hiynn.cms.common.aop.syslog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hiynn.cms.common.util.ShiroUtils;
import com.hiynn.cms.entity.SysLogEntity;
import com.hiynn.cms.entity.SysUserEntity;
import com.hiynn.cms.service.SysLogService;
import com.hiynn.component.requestAddressUtils.RequestAddressUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 系统日志，切面处理类
 *
 * @author zhang
 * @date 2019-07-25
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    private final SysLogService sysLogService;

    @Autowired
    public SysLogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;

    }

    @Before("@annotation(com.hiynn.cms.common.aop.syslog.SysLog)")
    public void saveSysLog(JoinPoint joinPoint) throws DbMakerConfigException, IOException {
        log.debug("---系统日志切面---");
        // 获取请求方法用于后面的获取注解 如果直接getSignature 的name 也可以获取方法名称但是MethodSignature 实现了方法本身的相关功能
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 日志模型
        SysLogEntity sysLog = new SysLogEntity();

        // 获取方法上的注解
        SysLog syslogAnn = method.getAnnotation(SysLog.class);


        // 初始化模型描述 和 产品描述
        if (syslogAnn != null) {
            // 是否有产品描述 如果没有就取swagger的
            if (StringUtils.isBlank(syslogAnn.product())) {
                Api annotation = joinPoint.getTarget().getClass().getAnnotation(Api.class);
                String[] tags = annotation.tags();
                // 按照规范如果设置了 api的 tags
                if (tags.length > 0) {
                    sysLog.setProduct(tags[0]);
                }
            } else {
                // 如果sysLog注解存在产品描述 则优先获取sysLog的属性作为产品描述
                sysLog.setProduct(syslogAnn.product());
            }
            // 是否有方法操作描述 如果没有就取swagger的 sysLog 优先级最高
            if (StringUtils.isBlank(syslogAnn.operation())) {
                ApiOperation apiOperationAnn = method.getAnnotation(ApiOperation.class);
                if (apiOperationAnn != null) {
                    String aoValue = apiOperationAnn.value();
                    sysLog.setOperation(aoValue);
                }
            } else {
                // 如果
                sysLog.setOperation(syslogAnn.operation());
            }
        }

        //请求的方法名
        StringBuilder methodStr = new StringBuilder();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        methodStr.append(className).append('.').append(methodName);
        sysLog.setMethod(methodStr.toString());

        //请求的参数
        Object[] args = joinPoint.getArgs();
        if (null != args) {
            // 序列化时过滤掉request和response和MultipartFile
            List<Object> logArgs = Arrays.stream(args)
                    .filter(arg -> (arg != null &&
                            !(arg instanceof ServletRequest) &&
                            !(arg instanceof ServletResponse) &&
                            !(arg instanceof MultipartFile) &&
                            !(arg instanceof MultipartFile[]))
                    ).collect(Collectors.toList());
            String params = JSON.toJSONString(logArgs);
            // 如果超长就截取数据
            sysLog.setParams(params.length() > 2000 ? params.substring(0, 2000) : params);
        }

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //IP地址
        String ip = RequestAddressUtils.getIp(request);
        sysLog.setIp(ip);

        //归属地
        String attribution = RequestAddressUtils.getAttribution(ip);
        sysLog.setAttribution(attribution);

        // 获取系统用户补全其他信息
        SysUserEntity user = ShiroUtils.getUser(SysUserEntity.class);

        sysLog.setUserAccount(user.getAccount());
        sysLog.setUserName(user.getUserName());
        sysLog.setCreatorId(user.getId());

        //保存系统日志
        sysLogService.insert(sysLog);
    }
}
