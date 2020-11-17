package com.shufflrr.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Converts response JSON into an object.
 *
 * Note: Java 13 or above recommended.
 * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8217264">JDK-8217264</a>
 *
 * @param <T> the object type
 */
public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<Optional<T>> {
    private final Class<T> target;

    public JsonBodyHandler(Class<T> target) {
        this.target = target;
    }

    @Override
    public HttpResponse.BodySubscriber<Optional<T>> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8), this::convert);
    }

    private Optional<T> convert(String string) {
        try {
            return Optional.of(new ObjectMapper().readValue(string, this.target));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
