package ru.redguy.rednetworker.clients.http.exceptions;

import ru.redguy.rednetworker.clients.http.IHttpResponse;

public class HttpProtocolException extends Exception {
    private int code = 0;
    private IHttpResponse response = null;
    
    public HttpProtocolException(Throwable cause, String message) {
        super(message, cause);
    }

    public HttpProtocolException(String message, int code, IHttpResponse response) {
        super(message);
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public IHttpResponse getResponse() {
        return response;
    }
}
