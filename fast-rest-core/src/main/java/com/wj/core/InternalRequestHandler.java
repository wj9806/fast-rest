package com.wj.core;

import java.lang.reflect.Method;

class InternalRequestHandler implements RequestHandler {

    private final Method method;

    private Class<?> returnType;

    InternalRequestHandler(Method method) {
        this.method = method;
        init();
    }

    private void init() {
        returnType = method.getReturnType();
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        for (Object arg : args) {
            System.out.println(arg);
        }
        return args[0];
    }
}
