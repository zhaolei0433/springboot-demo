package com.ipanel.web.app.cv.aspect;

import com.ipanel.web.app.cv.annotation.SysLog;
import com.ipanel.web.app.cv.business.myLog.service.IMyLogService;
import com.ipanel.web.app.cv.entity.LogInfo;
import com.ipanel.web.app.cv.global.MyException;
import com.ipanel.web.app.cv.global.MyException2;
import com.ipanel.web.app.cv.global.response.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;

/**
 * The type Controller aop.
 *
 * @author zhaolei
 */
@Aspect
@Component
public class ControllerAop {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    private IMyLogService myLogService;

    @Autowired
    public ControllerAop(IMyLogService myLogService) {
        this.myLogService = myLogService;
    }


    /**
     * 切入点
     * 匹配以Result为方法类型的所有方法
     */
    @Pointcut("execution(public com.ipanel.web.app.cv.global.response.Result *(..))")
    public void controllerAop() {
    }

    /**
     * 前置通知，目标方法调用前被调用
     *
     * @param joinPoint 切点
     */
    @Before("controllerAop()")
    public void doBefore(JoinPoint joinPoint) {
        //logger.info("- - - - - 前置通知，目标方法调用前被调用 - - - - ");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("url={}", request.getRequestURI());
        //method
        logger.info("method={}", request.getMethod());
        //ip
        logger.info("ip={}", request.getRemoteAddr());
        //method
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //param
        logger.info("args={}", joinPoint.getArgs());
    }

    /**
     * 后置最终通知，目标方法执行完执行
     */
    @After("controllerAop()")
    public void doAfter() {
        //logger.info("- - - - - 后置最终通知，目标方法执行完执行 - - - - -");
    }

    /**
     * 后置返回通知
     *
     * @param result
     * @param joinPoint
     */
    @AfterReturning(pointcut = "controllerAop()", returning = "result")
    public void doAfterReturnig(JoinPoint joinPoint, Result<?> result) throws Exception {
        //logger.info("- - - - - 后置返回通知 - - - - -");
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (null == method.getAnnotation(SysLog.class)) {
            // 说明此方法没有日志注解,无需记录数据库
            return;
        }

        //根据注解获取操作类型和操作内容
        String operationType = method.getAnnotation(SysLog.class).operationType();
        String operationName = method.getAnnotation(SysLog.class).operationName();
        // 接收到请求，记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String name = request.getParameter("managerName");
        //存储日志文件
        LogInfo logInfo = new LogInfo();
        logInfo.setOperationType(operationType);
        logInfo.setOperationName(operationName);
        logInfo.setMethod(targetName + "." + methodName + "()");
        logInfo.setCreateBy(name);
        logInfo.setCreateDate(System.currentTimeMillis());
        if (result.getCode() == 0) {
            logInfo.setResult(true);
        } else {
            logInfo.setResult(false);
        }
        logger.info("logInfo = {}", logInfo.toString());
        myLogService.addLog(logInfo);
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing 只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *
     * @param exception 异常类
     */
    @AfterThrowing(pointcut = "controllerAop()", throwing = "exception")
    public void afterThrowingAdvice(Exception exception) {
        logger.info("- - - - - 后置异常通知 - - - - -");
        logger.info("exception={}", exception.toString());
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around("controllerAop()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        //logger.info("- - - - - 环绕通知 - - - -");
        long startTime = Instant.now().toEpochMilli();
        Result<?> result = null;
        try {
            result = (Result<?>) proceedingJoinPoint.proceed();//调用执行目标方法
            logger.info(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName() + " use time:" + (Instant.now().toEpochMilli() - startTime));
            return result;
        } catch (Throwable throwable) {
            result = handlerException(proceedingJoinPoint, throwable);
        } finally {
            //logger.info("- - - - - 环绕通知 end - - - -");
        }
        return result;
    }

    private Result<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        Result<?> result = new Result<>();
        if (e instanceof MyException) {
            result.setCode(Result.FAIL);
            result.setMsg(e.getMessage());
        } else if (e instanceof MyException2) {
            result.setCode(Result.FAIL);
            result.setMsg(e.getMessage());
        } else if (e instanceof Exception) {
            result.setCode(Result.FAIL);
            result.setMsg("参数"+e.getMessage());
        }else {
            //主线程未知异常，告知处理
            logger.error(pjp.getSignature() + " Unknown exception occurs in main thread :", e);
            result.setMsg(e.toString());
            result.setCode(Result.EXCEPTION);
        }
        return result;
    }
}
