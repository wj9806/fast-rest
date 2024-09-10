package com.wj.core.http;

public class HttpEntity<T> {

    private T body;

    private HttpHeaders headers;

    public HttpEntity(T body, HttpHeaders headers) {
        this.body = body;
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
}
