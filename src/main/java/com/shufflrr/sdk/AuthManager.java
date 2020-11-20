package com.shufflrr.sdk;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation for retrieving a session token and priming API use for a single user.
 */
class AuthManager implements Shufflrr {
    private final URI site;
    private final Credentials credentials;
    private final HttpClient client;

    AuthManager(Credentials credentials) {
        this.site = credentials.uri();
        this.credentials = credentials;
        this.client = HttpClient.newBuilder()
                .cookieHandler(CookieHandler.getDefault())
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    private boolean auth() {
        return Requests.LOGIN.sendAsync(this.client, this.site, InTypes.STRING.with(credentials.asJson()), OutTypes.NODE)
                .join().body().map(node -> node.get("success").asBoolean()).orElse(false);
    }

    private boolean valid() {
        List<HttpCookie> cookies = ((CookieManager) CookieHandler.getDefault()).getCookieStore().get(this.site.resolve("api/account/login"));
        return !cookies.isEmpty() && !cookies.get(0).hasExpired();
    }

    @Override
    public <T, R> HttpResponse<R> send(Request request, InType<T> in, OutType<R> out, String... variables) throws IOException, InterruptedException {
        if (valid() || auth()) {
            return request.send(this.client, this.site, in, out, variables);
        } else {
            throw new AuthenticationException("Unable to authenticate.");
        }
    }

    @Override
    public <T, R> CompletableFuture<HttpResponse<R>> sendAsync(Request request, InType<T> in, OutType<R> out, String... variables) {
        if (valid() || auth()) {
            return request.sendAsync(this.client, this.site, in, out, variables);
        } else {
            throw new AuthenticationException("Unable to authenticate.");
        }
    }

    static {
        CookieHandler.setDefault(new CookieManager());
    }
}