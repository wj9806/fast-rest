package com.wj.core.http;

public class RequestConfig {

    private int connectTimeout;
    private int readTimeout;
    private boolean instanceFollowRedirects = true;
    private boolean useCaches = true;

    public static RequestConfig.Builder custom() {
        return new Builder();
    }

    RequestConfig(int connectTimeout, int readTimeout, boolean instanceFollowRedirects, boolean useCaches) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.instanceFollowRedirects = instanceFollowRedirects;
        this.useCaches = useCaches;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public boolean isInstanceFollowRedirects() {
        return instanceFollowRedirects;
    }

    public boolean isUseCaches() {
        return useCaches;
    }

    public static class Builder {

        private int connectTimeout;
        private int readTimeout;
        private boolean instanceFollowRedirects;
        private boolean useCaches;

        Builder() {
            this.connectTimeout = 10000;
            this.readTimeout = 10000;
        }

        public Builder setConnectTimeout(int ms) {
            this.connectTimeout = ms;
            return this;
        }

        public Builder setReadTimeout(int ms) {
            this.readTimeout = ms;
            return this;
        }

        public Builder setInstanceFollowRedirects(boolean instanceFollowRedirects) {
            this.instanceFollowRedirects = instanceFollowRedirects;
            return this;
        }

        public Builder setUseCaches(boolean useCaches) {
            this.useCaches = useCaches;
            return this;
        }

        public RequestConfig build() {
            return new RequestConfig(connectTimeout, readTimeout, instanceFollowRedirects, useCaches);
        }
    }
}
