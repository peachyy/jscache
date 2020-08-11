package com.peachyy.xcache.core.key;

import org.springframework.util.ObjectUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@Getter
@Setter
public class ExpressionKey  {
    private AnnotatedEleKey annotatedEleKey;
    private String expression;

    public ExpressionKey(AnnotatedEleKey annotatedKey, String expression) {
        this.annotatedEleKey = annotatedKey;
        this.expression = expression;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExpressionKey)) {
            return false;
        }
        ExpressionKey otherKey = (ExpressionKey) other;
        return (this.annotatedEleKey.equals(otherKey.annotatedEleKey) &&
                ObjectUtils.nullSafeEquals(this.expression, otherKey.expression));
    }

    @Override
    public int hashCode() {
        return this.annotatedEleKey.hashCode() * 29 + this.expression.hashCode();
    }

    @Override
    public String toString() {
        return this.annotatedEleKey + " , expression= \"" + this.expression + "\"";
    }
}
