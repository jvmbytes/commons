package com.jvmbytes.commons.utils;

import com.jvmbytes.commons.utils.exception.UnCaughtException;

/**
 * 懒加载
 *
 * @param <T> 懒加载类型
 * @author luanjia
 */
public abstract class LazyGet<T> {

    private volatile boolean isInit = false;
    private volatile T object;

    /**
     * initial object
     *
     * @return initial value
     * @throws Throwable 异常
     */
    abstract protected T initialValue() throws Throwable;

    public T get() {

        if (isInit) {
            return object;
        }

        // lazy get
        try {
            object = initialValue();
            isInit = true;
            return object;
        } catch (Throwable throwable) {
            throw new UnCaughtException(throwable);
        }
    }

}
