package ru.redguy.rednetworker.clients.http.exceptions;

@SuppressWarnings("unused")
public class HttpProtocolException extends Exception {
    public HttpProtocolException() {super();}
    public HttpProtocolException(String message) {super(message);}
    public HttpProtocolException(String message, Throwable cause) {super(message,cause);}
    public HttpProtocolException(String message, String url) {super(message);}
    public HttpProtocolException(Throwable cause) {super(cause);}
    public HttpProtocolException(String message,String url,Throwable cause) {super(message, cause);}
}
