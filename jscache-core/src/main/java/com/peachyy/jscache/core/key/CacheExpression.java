package com.peachyy.jscache.core.key;

import com.peachyy.jscache.common.CacheMetadata;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xs.Tao
 */
public class CacheExpression {

    private  ExpressionParser parse;
    private Map<ExpressionKey,Expression> expressionCacheMap =new ConcurrentHashMap<>();
    private Map<ExpressionKey,Expression> argConditionCacheMap =new ConcurrentHashMap<>();
    private Map<ExpressionKey,Expression> returnConditionCacheMap =new ConcurrentHashMap<>();
    protected Expression getKeyExpression(AnnotatedEleKey elementKey, String expression) {
        return getInnerExpression(elementKey, expression, expressionCacheMap);
    }
    protected Expression getArgConditionExpression(
                                          AnnotatedEleKey elementKey, String expression) {
        return getInnerExpression(elementKey, expression, argConditionCacheMap);
    }
    protected Expression getReturnConditionExpression(
            AnnotatedEleKey elementKey, String expression) {
        return getInnerExpression(elementKey, expression, returnConditionCacheMap);
    }
    private Expression getInnerExpression(AnnotatedEleKey elementKey, String expression, Map<ExpressionKey, Expression> cacheMap) {
        ExpressionKey expressionKey = createKey(elementKey, expression);
        Expression    expr   = cacheMap.get(expressionKey);
        if (expr == null) {
            expr = getParse().parseExpression(expression);
            cacheMap.putIfAbsent(expressionKey, expr);
        }
        return expr;
    }

    protected AnnotatedEleKey buildAnnotatedEleKey(CacheMetadata metadata){
        return new AnnotatedEleKey(metadata.getMethod(),metadata.getClazz());
    }
    public ExpressionParser getParse() {
        return parse;
    }

    public void setParse(ExpressionParser parse) {
        this.parse = parse;
    }

    private ExpressionKey createKey(AnnotatedEleKey elementKey, String expression) {
        return new ExpressionKey(elementKey, expression);
    }


}
