package com.wj.core;

public interface RequestHandler {

    Object invoke(Object[] args) throws Throwable;

}
