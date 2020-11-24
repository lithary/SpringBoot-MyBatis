package com.shawn.monitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;
import java.util.Arrays;

//@Aspect
//@Component
public class SubmitTaskMonitor {
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

//    @Pointcut("execution(public * com.shawn.service.impl..*.submitTask(..))")
//    public void submit(){
//
//    }
    /**
     * 环绕通知：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
     * @return
     */
//    @Around(value="submit()")
    public Object aroundMethod(ProceedingJoinPoint jp){
        String methodName = jp.getSignature().getName();
        Object result = null;
        try {
            System.out.println("【环绕通知中的--->前置通知】：the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
            //执行目标方法
            Method beforeMethod = getTargetMethod(jp,"beforeSubmit");
            beforeMethod.invoke(jp.getTarget(),jp.getArgs());
            //开启事务
            TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

            result = jp.proceed();
            //回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);

            Method afterMethod = getTargetMethod(jp,"afterSubmit");
            afterMethod.invoke(jp.getTarget(),jp.getArgs());

        } catch (Throwable e) {
            System.out.println("【环绕通知中的--->异常通知】：the method 【" + methodName + "】 occurs exception " + e);
        }

        System.out.println("【环绕通知中的--->后置通知】：-----------------end.----------------------");


        return result;
    }

    //基于连接点信息获取目标方法对象
    private Method getTargetMethod(ProceedingJoinPoint joinPoint,String methodName) throws NoSuchMethodException {
        //获取目标类对象
        Class<?> aClass = joinPoint.getTarget().getClass();
        //获取方法签名信息,方法名和参数列表
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取目标方法对象
        Method method = aClass.getDeclaredMethod(methodName, signature.getParameterTypes());
        return method;
    }

    //获取方法类全名+方法名
    private String getClassAndMethodName(Method method){
        //获取类全名
        String className = method.getDeclaringClass().getName();
        //获取方法名
        String methodName = method.getName();
        return new StringBuffer(className).append(".").append(methodName).toString();
    }
}
