package com.shufflrr.sdk;

import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.function.Function;

public class InType<T> {
    private final Function<T, HttpRequest.BodyPublisher> fn;
    private final T content;

    public InType<T> with(T content) {
        return new InType<>(this.fn, content);
    }

    public InType(Function<T, HttpRequest.BodyPublisher> fn) {
        this(fn, null);
    }

    private InType(Function<T, HttpRequest.BodyPublisher> fn, T content) {
        this.fn = fn;
        this.content = content;
    }

    public Optional<T> content() {
        return Optional.ofNullable(this.content);
    }

    public HttpRequest.BodyPublisher publisher() {
        if (this.content == null) {
            throw new IllegalStateException("No content available to produce publisher.");
        }

        return this.fn.apply(this.content);
    }
}
