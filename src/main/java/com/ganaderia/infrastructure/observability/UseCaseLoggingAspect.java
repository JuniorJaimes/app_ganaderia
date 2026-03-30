package com.ganaderia.infrastructure.observability;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UseCaseLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.ganaderia.application.usecase..*)")
    public void useCasePointcut() {
        // Pointcut definition
    }

    @Around("useCasePointcut()")
    public Object logUseCaseExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String useCaseName = joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
        
        log.info("🚀 Iniciando [{}]", useCaseName);
        long start = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info("✅ [{}] finalizado exitosamente en {} ms", useCaseName, executionTime);
            return result;
        } catch (IllegalArgumentException e) {
            long executionTime = System.currentTimeMillis() - start;
            log.warn("⚠️ [{}] falló por validación de negocio en {} ms: {}", useCaseName, executionTime, e.getMessage());
            throw e;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - start;
            log.error("❌ [{}] falló por error inesperado en {} ms", useCaseName, executionTime, e);
            throw e;
        }
    }
}
