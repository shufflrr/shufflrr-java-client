package com.shufflrr.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * A very simple function class which helps in procuring data from JsonNodes.
 */
@FunctionalInterface
public interface Lens<T, R> extends Function<T, R> {
    Lens<String, Lens<JsonNode, JsonNode>> TRAVERSE = s -> n -> n.get(s);
    Lens<JsonNode, String> STRING = JsonNode::asText;
    Lens<JsonNode, Integer> INTEGER = JsonNode::asInt;
    Lens<JsonNode, Long> LONG = JsonNode::asLong;
    Lens<JsonNode, Double> DOUBLE = JsonNode::asDouble;
    Lens<JsonNode, Boolean> BOOLEAN = JsonNode::asBoolean;

    Lens<String, LocalDateTime> LDT = LocalDateTime::parse;
    Lens<JsonNode, LocalDateTime> DATE = LDT.compose(STRING);

    Lens<JsonNode, ArrayNode> ARRAY = ArrayNode.class::cast;

    R apply(T node);

    default <V> Lens<V, R> compose(Lens<? super V, ? extends T> transform) {
        return v -> apply(transform.apply(v));
    }

    default <V> Lens<T, V> andThen(Lens<? super R, ? extends V> transform) {
        return v -> transform.apply(apply(v));
    }
}
