package com.peachyy.jscache.core.key;

import com.peachyy.jscache.common.CacheMetadata;
import com.peachyy.jscache.common.CachePutMetadata;
import com.peachyy.jscache.common.CacheableMetadata;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 *
 * @author Xs.Tao
 */
public class SpringElKeyGenerator extends CacheExpression implements KeyGenerator {

    /**
     * method param gets
     */
    private final ParameterNameDiscoverer parameterNameDiscoverer;
    public static final String RESULT="result";


    public SpringElKeyGenerator(){
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null);
        SpelExpressionParser    spelExpressionParser = new SpelExpressionParser(spelParserConfiguration);
        parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        setParse(spelExpressionParser);
    }


    @Override
    public String generate(CacheMetadata metadata) {
        return generate(metadata,metadata.getArguments());
    }

    @Override
    public String generate(CacheMetadata metadata, Object[] arguments) {
        Object val=metadata.getKey();
        if(arguments!=null && arguments.length>0){
            AnnotatedEleKey cacheKey=buildAnnotatedEleKey(metadata);
            Expression expression=getKeyExpression(cacheKey,metadata.getKey());
            StandardEvaluationContext context =
                    new MethodBasedEvaluationContext( TypedValue.NULL,
                            metadata.getMethod(),arguments,parameterNameDiscoverer);
            val=expression.getValue(context);
        }
        return metadata.getPrefix().concat(Objects.toString(val));
    }

    @Override
    public boolean argCondition(CacheMetadata metadata, Object[] arguments) {
        if(StringUtils.isEmpty(metadata.getArgCondition())){
           return true;
        }
        Expression expression=getArgConditionExpression(buildAnnotatedEleKey(metadata),metadata.getArgCondition());
        StandardEvaluationContext context =
                new MethodBasedEvaluationContext( TypedValue.NULL,
                        metadata.getMethod(),arguments,parameterNameDiscoverer);

        return Boolean.TRUE.equals(expression.getValue(context,Boolean.class));
    }

    @Override
    public boolean returnCondition(CacheMetadata metadata, Object returnValue) {
        String returnCondition="";
        if(metadata instanceof CacheableMetadata){
            returnCondition=((CacheableMetadata) metadata).getReturnCondition();
        }else if(metadata instanceof CachePutMetadata){
            returnCondition=((CachePutMetadata) metadata).getReturnCondition();
        }
        if(StringUtils.isEmpty(returnCondition)){
          return true;
        }
        Expression expression=getArgConditionExpression(buildAnnotatedEleKey(metadata),returnCondition);
        StandardEvaluationContext context =
                new MethodBasedEvaluationContext( TypedValue.NULL,
                        metadata.getMethod(),null,parameterNameDiscoverer);
        if(returnValue!=null){
            context.setVariable(RESULT,returnValue);
        }
        return Boolean.TRUE.equals(expression.getValue(context,Boolean.class));
    }


}
