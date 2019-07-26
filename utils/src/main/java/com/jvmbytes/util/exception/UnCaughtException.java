package com.jvmbytes.util.exception;

/**
 * 未捕获异常
 * 用来封装不希望抛出的异常
 * @author luanjia
 */
public class UnCaughtException extends RuntimeException {

    public UnCaughtException(Throwable cause) {
        super(cause);
    }
}
