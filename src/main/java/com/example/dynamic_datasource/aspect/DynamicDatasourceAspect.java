package com.example.dynamic_datasource.aspect;

import com.example.dynamic_datasource.config.DynamicDataSource;
import com.example.dynamic_datasource.config.ThreadLocalDataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * @author jiangchen
 * @since 2022-08-26
 */
@Slf4j
@Aspect
@Component
public class DynamicDatasourceAspect {

    @Resource
    DynamicDataSource dynamicDataSource;

    @Around("execution(* com.example.dynamic_datasource.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object result = null;
        try {
            String parkId = sra.getRequest().getHeader("parkId");

            log.info("当前租户园区Id:{}", parkId);
            if (StringUtils.isNotBlank(parkId)) {
                ThreadLocalDataSourceContext.setLocalDataSource(parkId);
            } else {
                log.info("没有租户标识，使用默认数据源");
                ThreadLocalDataSourceContext.setLocalDataSource("default");
            }

            result = joinPoint.proceed();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("系统异常，请联系技术专家！");
        } finally {
            ThreadLocalDataSourceContext.clear();
        }
        return result;
    }

}
