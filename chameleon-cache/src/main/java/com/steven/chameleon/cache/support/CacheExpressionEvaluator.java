package com.steven.chameleon.cache.support;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存表达式解析器
 */
public class CacheExpressionEvaluator {
    private final ExpressionParser parser = new SpelExpressionParser();
    private final Map<String, Expression> expressionCache = new ConcurrentHashMap<>(256);

    /**
     * 解析缓存键表达式
     */
    public Object parseKey(String keyExpression, Method method, Object[] args) {
        Expression expression = getExpression(keyExpression);
        EvaluationContext context = createEvaluationContext(method, args);
        return expression.getValue(context);
    }

    /**
     * 解析条件表达式
     */
    public boolean parseCondition(String conditionExpression, Method method, Object[] args) {
        if (conditionExpression == null || conditionExpression.isEmpty()) {
            return true;
        }
        Expression expression = getExpression(conditionExpression);
        EvaluationContext context = createEvaluationContext(method, args);
        return Boolean.TRUE.equals(expression.getValue(context, Boolean.class));
    }

    private Expression getExpression(String expression) {
        return expressionCache.computeIfAbsent(expression, parser::parseExpression);
    }

    private EvaluationContext createEvaluationContext(Method method, Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();

        // 添加方法参数
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                context.setVariable("p" + i, args[i]);
            }
            context.setVariable("args", args);
        }

        // 添加方法信息
        context.setVariable("method", method);
        context.setVariable("methodName", method.getName());
        context.setVariable("targetClass", method.getDeclaringClass());

        return context;
    }
}