package com.shufflrr.sdk;

/**
 * Unchecked exception for errors authenticating.
 */
public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = -3265792946654947492L;

    public AuthenticationException() {}

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
