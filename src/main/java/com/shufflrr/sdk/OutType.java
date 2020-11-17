package com.shufflrr.sdk;

import java.net.http.HttpResponse;

public class OutType<R> {
    private final HttpResponse.BodyHandler<R> handler;

    public OutType(HttpResponse.BodyHandler<R> handler) {
        this.handler = handler;
    }

    public HttpResponse.BodyHandler<R> handler() {
        return this.handler;
    }
}
