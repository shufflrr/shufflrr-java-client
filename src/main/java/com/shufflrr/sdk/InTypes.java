package com.shufflrr.sdk;

import com.github.mizosoft.methanol.MultipartBodyPublisher;

import java.io.FileNotFoundException;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class InTypes {
    public static final InType<Void> NULL = new InType<>(null);
    public static final InType<String> STRING = new InType<>(HttpRequest.BodyPublishers::ofString);
    public static final InType<Path> PATH = new InType<>(path -> {
        try {
            return HttpRequest.BodyPublishers.ofFile(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found.", e);
        }
    });
    public static final InType<UnaryOperator<MultipartBodyPublisher.Builder>> MULTIPART = new InType<>(op -> {
        return op.apply(MultipartBodyPublisher.newBuilder()).build();
    });

    private InTypes() {}
}
