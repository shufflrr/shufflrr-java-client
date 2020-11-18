package com.shufflrr.sdk;

import java.io.FileNotFoundException;
import java.net.http.HttpRequest;
import java.nio.file.Path;

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

    private InTypes() {}
}
