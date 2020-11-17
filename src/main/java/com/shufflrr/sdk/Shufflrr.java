package com.shufflrr.sdk;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public interface Shufflrr {
    static Shufflrr connect(Credentials credentials) {
        return new AuthManager(credentials);
    }

    <T, R> HttpResponse<R> send(Request request, InType<T> in, OutType<R> out, String... variables) throws IOException, InterruptedException;

    <T, R> CompletableFuture<HttpResponse<R>> sendAsync(Request request, InType<T> in, OutType<R> out, String... variables);
}
