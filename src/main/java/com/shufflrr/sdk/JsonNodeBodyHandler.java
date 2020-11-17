package com.shufflrr.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Converts response JSON into a JsonNode.
 */
public class JsonNodeBodyHandler implements HttpResponse.BodyHandler<Optional<JsonNode>> {
    @Override
    public HttpResponse.BodySubscriber<Optional<JsonNode>> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8), this::convert);
    }

    private Optional<JsonNode> convert(String string) {
        try {
            return Optional.of(new ObjectMapper().readTree(string));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
