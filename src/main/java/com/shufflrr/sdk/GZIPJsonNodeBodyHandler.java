package com.shufflrr.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class GZIPJsonNodeBodyHandler implements HttpResponse.BodyHandler<Optional<JsonNode>> {
    @Override
    public HttpResponse.BodySubscriber<Optional<JsonNode>> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofInputStream(), in -> {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(new GZIPInputStream(in)))) {
                return Optional.of(new ObjectMapper().readTree(r.lines().collect(Collectors.joining("\n"))));
            } catch (IOException e) {
                return Optional.empty();
            }
        });
    }
}
