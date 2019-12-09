package ru.redguy.rednetworker.clients.http.exceptions;

public class URLException extends Exception {
    public URLException() {super();}
    public URLException(String message) {super(message);}
    public URLException(String message, Throwable cause) {super(message,cause);}
    public URLException(String message, String url) {super(message);}
    public URLException(Throwable cause) {super(cause);}
    public URLException(String message,String url,Throwable cause) {super(message, cause);}
}
