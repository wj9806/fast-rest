package com.wj.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class InvocationAdapter<T> implements InvocationHandler {

    private final Class<T> clazz;

    private final Map<String, RequestHandler> handlers;

    public InvocationAdapter(Class<T> clazz) {
        this.clazz = clazz;
        this.handlers = createHandlers();
    }

    private Map<String, RequestHandler> createHandlers() {
        Map<String, RequestHandler> handlers = new HashMap<>();
        LinkedList<Class<?>> interfaces = getAllInterfaces(this.clazz);
        Class<?> in;
            while ((in = interfaces.pollFirst()) != null) {
            for (Method method : in.getDeclaredMethods()) {
                handlers.put(generateKey(method), new InternalRequestHandler(method));
            }
        }
        return handlers;
    }

    private static LinkedList<Class<?>> getAllInterfaces(Class<?> interfaceClass) {
        LinkedList<Class<?>> interfaces = new LinkedList<>();
        interfaces.push(interfaceClass);
        getParentInterfaces(interfaces, interfaceClass);
        return interfaces;
    }

    private static void getParentInterfaces(LinkedList<Class<?>> interfaces, Class<?> interfaceClass) {
        Class<?>[] classInterfaces = interfaceClass.getInterfaces();
        for (Class<?> classInterface : classInterfaces) {
            interfaces.push(classInterface);
        }
        for (Class<?> classInterface : classInterfaces) {
            getParentInterfaces(interfaces, classInterface);
        }
    }

    private String generateKey(Method method) {
        return method.getName() + "#" + Arrays.toString(method.getParameterTypes());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!"equals".equals(method.getName())) {
            if ("hashCode".equals(method.getName())) {
                return this.hashCode();
            } else {
                return "toString".equals(method.getName()) ? this.toString() : handlers.get(generateKey(method)).invoke(args);
            }
        } else {
            try {
                Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return this.equals(otherHandler);
            } catch (IllegalArgumentException var5) {
                return false;
            }
        }
    }

}
