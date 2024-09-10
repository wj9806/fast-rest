package com.wj.core.http;

import java.util.*;

public class HttpHeaders implements Map<String, List<String>> {

    public static final String CONTENT_TYPE = "Content-Type";

    private final LinkedHashMap<String, List<String>> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
    }

    public HttpHeaders(Map<String, List<String>> headers) {
        this.headers = new LinkedHashMap<>();
        addAll(headers);
    }

    public void add(String name, String value) {
        List<String> values = this.headers.computeIfAbsent(name, k -> new ArrayList<>(1));
        values.add(value);
    }

    public void addAll(HttpHeaders headers) {
        addAll(headers.headers);
    }

    public void addAll(Map<String, List<String>> headers) {
        for (Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = this.headers.computeIfAbsent(entry.getKey(), k -> new ArrayList<>(1));
            values.addAll(entry.getValue());
        }
    }

    public String getFirst(String name) {
        List<String> list = headers.get(name);
        if (list == null) return null;
        return list.isEmpty() ? null : list.get(0);
    }

    public MediaType getContentType() {
        String value = getFirst(CONTENT_TYPE);
        return value != null ? MediaType.parseMediaType(value) : null;
    }

    @Override
    public int size() {
        return headers.size();
    }

    @Override
    public boolean isEmpty() {
        return headers.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return headers.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return headers.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        return headers.put(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        return headers.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> m) {
        headers.putAll(m);
    }

    @Override
    public void clear() {
        headers.clear();
    }

    @Override
    public Set<String> keySet() {
        return headers.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return headers.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return headers.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return headers.equals(o);
    }

    @Override
    public int hashCode() {
        return headers.hashCode();
    }
}
