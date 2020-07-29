package com.peachyy.xcache.core.spring.aspect;

import com.peachyy.xcache.common.CacheMetadata;
import com.peachyy.xcache.core.key.KeyGenerator;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Xs.Tao
 */
public class SpringElKeyGenerator implements KeyGenerator {
    /**
     * spring el expiress parse
     */
    private final SpelExpressionParser    spelExpressionParser;
    /**
     * method param gets
     */
    private final ParameterNameDiscoverer parameterNameDiscoverer;

    private Map<Method,Expression> expressionCacheMap =new ConcurrentHashMap<>();

    public SpringElKeyGenerator(){
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null);
        spelExpressionParser = new SpelExpressionParser(spelParserConfiguration);
        parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    }

    @Override
    public String generate(CacheMetadata metadata) {
        Expression expression=expressionCacheMap.get(metadata.getMethod());
        if(null==expression){
            expression = spelExpressionParser.parseExpression(metadata.getKey());
            expressionCacheMap.putIfAbsent(metadata.getMethod(),expression);
        }
        StandardEvaluationContext context =
                new MethodBasedEvaluationContext( TypedValue.NULL,
                        metadata.getMethod(),metadata.getArguments(),parameterNameDiscoverer);
        Object val=expression.getValue(context);
        return metadata.getPrefix().concat(Objects.toString(val));
    }
}
