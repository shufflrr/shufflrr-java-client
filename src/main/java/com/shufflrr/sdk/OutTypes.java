package com.shufflrr.sdk;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.http.HttpResponse;
import java.util.Optional;

public class OutTypes {
    public static final OutType<Void> NULL = new OutType<>(HttpResponse.BodyHandlers.discarding());
    public static final OutType<String> STRING = new OutType<>(HttpResponse.BodyHandlers.ofString());
    public static final OutType<Optional<JsonNode>> GZIP_NODE = new OutType<>(new GZIPJsonNodeBodyHandler());
    public static final OutType<Optional<JsonNode>> NODE = new OutType<>(new JsonNodeBodyHandler());

    private OutTypes() {}
}
