package ru.redguy.rednetworker.clients.http.exceptions;

import java.net.HttpURLConnection;

public class HttpProtocolException extends Exception {
    private int code;

    public HttpProtocolException() {super();}
    public HttpProtocolException(String message) {super(message);}
    public HttpProtocolException(String message, int code) {
        super(message);
        this.code = code;
    }
    public HttpProtocolException(String message, Throwable cause) {super(message,cause);}
    public HttpProtocolException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
    public HttpProtocolException(Throwable cause) {super(cause);}

    public int getCode() {
        return code;
    }
}
