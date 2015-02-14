package com.alexanza.common.api;

import retrofit.Endpoint;

/**
 * Created by alex on 9/02/15.
 */
public final class DynamicEndPoint implements Endpoint {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override public String getName() {
        return "default";
    }

    @Override public String getUrl() {
        if (url == null) throw new IllegalStateException("url not set.");
        return url;
    }
}
