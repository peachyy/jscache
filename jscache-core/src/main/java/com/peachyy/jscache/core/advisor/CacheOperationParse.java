package com.peachyy.jscache.core.advisor;

import com.peachyy.jscache.common.CacheMetadata;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Xs.Tao
 */
public interface CacheOperationParse {

    List<CacheMetadata> findOperation(Method method,Class targetClass);
}
