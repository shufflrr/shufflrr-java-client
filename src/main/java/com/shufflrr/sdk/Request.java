package com.shufflrr.sdk;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class Request {
    private static final String ROOT = "api";

    protected final HttpRequest.Builder builder;
    protected final String[] headers;
    private final Category category;
    private final Type type;
    private final String path;

    Request(Category category, Type type, String path, String... headers) {
        this.builder = HttpRequest.newBuilder();
        this.category = category;
        this.type = type;
        this.path = path;
        this.headers = headers;
    }

    public Request apply(UnaryOperator<HttpRequest.Builder> op) {
        op.apply(this.builder);
        return this;
    }

    protected <T, R> HttpRequest build(HttpRequest.Builder builder, InType<T> in, URI base, String... variables) {
        switch (this.type) {
            case POST -> builder.POST(in.publisher());
            case PUT -> builder.PUT(in.publisher());
            case DELETE -> builder.DELETE();
            case GET -> builder.GET();
        }

        if (this.headers.length != 0) {
            builder.headers(this.headers);
        }

        return builder.uri(uri(base, variables)).build();
    }

    private URI uri(URI base, String... variables) {
        String path = String.format(String.join("/", ROOT, this.category.path, this.path), (Object[]) variables);
        return base.resolve(path);
    }

    <T, R> HttpResponse<R> send(HttpClient client, URI base, InType<T> in, OutType<R> out, String... variables) throws IOException, InterruptedException {
        return client.send(build(this.builder, in, base, variables), out.handler());
    }

    <T, R> CompletableFuture<HttpResponse<R>> sendAsync(HttpClient client, URI base, InType<T> in, OutType<R> out, String... variables) {
        return client.sendAsync(build(this.builder, in, base, variables), out.handler());
    }

    public enum Category {
        ACCOUNT("account"),
        ACTIONS_QUEUE("actionsqueue"),
        BILLING("billing"),
        BUILDERS("builders"),
        DOCUMENTS("documents"),
        EMAIL("email"),
        FILES("files"),
        FOLDERS("folders"),
        FONTS("fonts"),
        GROUPS("groups"),
        LIVE_CONTENT("livecontent"),
        LIVE_PRESENTER("livepresenter"),
        METADATA("metadata"),
        METADATA_TYPES("metadatatypes"),
        PORTAL("portal"),
        PRESENTATIONS("presentations"),
        REPORTS("reports"),
        SHARES("shares"),
        SLIDES("slides"),
        USER("user"),
        USERS("users"),
        VIDEOS("videos");

        private final String path;

        Category(String path) {
            this.path = path;
        }
    }

    public enum Type {
        DELETE,
        GET,
        POST,
        PUT
    }
}