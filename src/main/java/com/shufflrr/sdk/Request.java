package com.shufflrr.sdk;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

public class Request {
    private static final Function<URI, String[]> HEADERS = uri -> new String[]{
            "Accept-Encoding", "gzip, deflate, br",
            "Accept-Language", "en-US,en;q=0.9",
            "Access-Control-Allow-Credentials", "true",
            "Access-Control-Allow-Origin", uri.toString(),
            "Content-Type", "application/json",
            "Origin", uri.toString(),
            "Accept", "application/json, text/javascript, /; q=0.01",
            "Referer", uri.resolve("/Shufflrr").toString(),
            "X-Requested-With", "XMLHttpRequest",
    };
    private static final String ROOT = "api";

    protected final String[] headers;
    private final Category category;
    private final Type type;
    private final String path;

    Request(Category category, Type type, String path, String... headers) {
        this.category = category;
        this.type = type;
        this.path = path;
        this.headers = headers;
    }

    public Request withHeaders(String... headers) {
        return new Request(this.category, this.type, this.path, Stream.of(headers, this.headers).flatMap(Stream::of).toArray(String[]::new));
    }

    protected <T> HttpRequest.Builder configure(InType<T> in, URI base, String... variables) {
        URI uri = uri(base, variables);
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);

        switch (this.type) {
            case POST -> builder.POST(in.publisher()).headers(HEADERS.apply(base));
            case PUT -> builder.PUT(in.publisher()).headers(HEADERS.apply(base));
            case DELETE -> builder.DELETE().headers(HEADERS.apply(base));
            case GET -> builder.GET().setHeader("Accept", "application/json");
        }

        for (int i = 0; i < this.headers.length; i += 2) {
            builder.setHeader(this.headers[i], this.headers[i + 1]);
        }

        return builder;
    }

    protected URI uri(URI base, String... variables) {
        String path = String.format(String.join("/", ROOT, this.category.path, this.path), (Object[]) variables);
        return base.resolve(path);
    }

    <T, R> HttpResponse<R> send(HttpClient client, URI base, InType<T> in, OutType<R> out, String... variables) throws IOException, InterruptedException {
        return client.send(configure(in, base, variables).build(), out.handler());
    }

    <T, R> CompletableFuture<HttpResponse<R>> sendAsync(HttpClient client, URI base, InType<T> in, OutType<R> out, String... variables) {
        return client.sendAsync(configure(in, base, variables).build(), out.handler());
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