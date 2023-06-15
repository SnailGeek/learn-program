//package com.snail.advice;
//
//import org.springframework.aop.AfterReturningAdvice;
//
//import java.lang.reflect.Method;
//
//public class TaskExecutionAfterReturningAdvice implements AfterReturningAdvice {
//    private SqlMapClientTemplate sqlMapClientTemplate;
//
//    @Override
//    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
//        Class<?> aClass = target.getClass();
//        getSqlMapClientTemplate().insert("BATCH.insertTaskStatus", clazz.getName());
//    }
//
//    public SqlMapClientTemplate getSqlMapClientTemplate() {
//        return sqlMapClientTemplate;
//    }
//
//    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
//        this.sqlMapClientTemplate = sqlMapClientTemplate;
//    }
//}
