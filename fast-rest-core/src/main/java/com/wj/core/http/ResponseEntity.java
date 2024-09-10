package com.wj.core.http;

public class ResponseEntity<T> extends HttpEntity<T> {

    private final int statusCode;

    public ResponseEntity(T body, HttpHeaders headers, int statusCode) {
        super(body, headers);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
