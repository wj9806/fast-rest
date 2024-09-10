package com.wj.core.http;

import java.net.URI;

public class RequestEntity<T> extends HttpEntity<T> {

    private final HttpMethod method;

    private final URI uri;

    public RequestEntity(T body, HttpHeaders headers, HttpMethod method, URI uri) {
        super(body, headers);

        if (uri == null)
            throw new HttpException("uri can not be null");

        this.method = method;
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }
}
