package com.wj.core;

import java.lang.reflect.Proxy;

public class InvocationAdapterFactory {

    @SuppressWarnings("unchecked")
    public <T> T createRequestHandler(Class<T> clazz) {
        InvocationAdapter<T> invocationAdapter = new InvocationAdapter<>(clazz);

        return (T) Proxy. newProxyInstance(clazz.getClassLoader(),
                new Class<?>[] { clazz },
                invocationAdapter);
    }

}
