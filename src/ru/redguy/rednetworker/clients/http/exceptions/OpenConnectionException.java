package ru.redguy.rednetworker.clients.http.exceptions;

@SuppressWarnings("unused")
public class OpenConnectionException extends Exception {
    public OpenConnectionException() {super();}
    public OpenConnectionException(String message) {super(message);}
    public OpenConnectionException(String message, Throwable cause) {super(message,cause);}
    public OpenConnectionException(String message, String url) {super(message);}
    public OpenConnectionException(Throwable cause) {super(cause);}
    public OpenConnectionException(String message,String url,Throwable cause) {super(message, cause);}
}
