package com.peachyy.jscache.core.key;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.AnnotatedElement;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@Getter
@Setter
public class AnnotatedEleKey  implements Comparable<AnnotatedEleKey>{
    private AnnotatedElement element;
    private final Class<?> targetClass;

    public AnnotatedEleKey(AnnotatedElement element, Class<?> targetClass) {
        this.element = element;
        this.targetClass = targetClass;
    }

    @Override
    public int compareTo(AnnotatedEleKey o) {
        int result = this.element.toString().compareTo(o.element.toString());
        if (result == 0 && this.targetClass != null) {
            if (o.targetClass == null) {
                return 1;
            }
            result = this.targetClass.getName().compareTo(o.targetClass.getName());
        }
        return result;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnnotatedEleKey)) {
            return false;
        }
        AnnotatedEleKey otherKey = (AnnotatedEleKey) other;
        return (this.element.equals(otherKey.element) &&
                ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass));
    }

    @Override
    public int hashCode() {
        return this.element.hashCode() + (this.targetClass != null ? this.targetClass.hashCode() * 29 : 0);
    }

    @Override
    public String toString() {
        return this.element + (this.targetClass != null ? " on " + this.targetClass : "");
    }
}
