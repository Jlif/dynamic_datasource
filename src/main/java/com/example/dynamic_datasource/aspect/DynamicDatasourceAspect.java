package com.example.dynamic_datasource.aspect;

import com.example.dynamic_datasource.config.ThreadLocalDataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiangchen
 * @since 2022-08-26
 */
@Slf4j
@Aspect
@Component
@Order(1) // 请注意：这里order一定要小于tx:annotation-driven的order，即先执行DynamicDataSourceAspectAdvice切面，再执行事务切面，才能获取到最终的数据源
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DynamicDatasourceAspect {

    @Around("execution(* com.example.dynamic_datasource.controller.*.*(..)) || execution(* com.example.dynamic_datasource.*.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object result = null;
        try {
            HttpServletRequest request = sra.getRequest();
            String parkId = sra.getRequest().getHeader("parkId");

            log.info("当前租户园区Id:{}", parkId);
            if (StringUtils.isNotBlank(parkId)) {
                ThreadLocalDataSourceContext.setLocalDataSource(parkId);
                result = jp.proceed();
            } else {
                log.error("查询失败，当前租户信息未取到，请联系技术专家！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("系统异常，请联系技术专家！");
        } finally {
            ThreadLocalDataSourceContext.clear();
        }
        return result;
    }

}
