package com.wj.core.http;

public interface HttpClient {

    <T> ResponseEntity<T> req(RequestEntity<?> request, Class<T> respType);

}
