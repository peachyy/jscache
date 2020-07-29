package com.peachyy.xcache.core.exception;

/**
 * @author Xs.Tao
 */
public class CacheException extends RuntimeException {
    public CacheException() {
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
