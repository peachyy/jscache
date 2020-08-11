package com.peachyy.xcache.core.key;

import com.peachyy.xcache.common.CacheMetadata;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

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

    private Map<ExpressionKey,Expression> expressionCacheMap =new ConcurrentHashMap<>();

    public SpringElKeyGenerator(){
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null);
        spelExpressionParser = new SpelExpressionParser(spelParserConfiguration);
        parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    }
    private ExpressionKey createCacheKey(CacheMetadata metadata){
        return new ExpressionKey(new AnnotatedEleKey(metadata.getMethod(),metadata.getClazz()),metadata.getKey());
    }

    @Override
    public String generate(CacheMetadata metadata) {
        return generate(metadata,metadata.getArguments());
    }

    @Override
    public String generate(CacheMetadata metadata, Object[] arguments) {
        ExpressionKey cacheKey=createCacheKey(metadata);
        Expression expression=expressionCacheMap.get(cacheKey);
        if(null==expression){
            expression = spelExpressionParser.parseExpression(metadata.getKey());
            expressionCacheMap.putIfAbsent(cacheKey,expression);
        }
        StandardEvaluationContext context =
                new MethodBasedEvaluationContext( TypedValue.NULL,
                        metadata.getMethod(),arguments==null?metadata.getArguments():arguments,parameterNameDiscoverer);
        Object val=expression.getValue(context);
        return metadata.getPrefix().concat(Objects.toString(val));
    }
}
