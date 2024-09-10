package com.wj.core.http;

import com.wj.core.InvocationAdapterFactory;
import org.junit.jupiter.api.Test;

public class MyTest {

    @Test
    public void test1() {
        InvocationAdapterFactory factory = new InvocationAdapterFactory();
        MyReq myReq = factory.createRequestHandler(MyReq.class);

        System.out.println(myReq.get("abc"));
    }

}
