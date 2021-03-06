package ru.redguy.rednetworker.clients.ftp.exceptions;

@SuppressWarnings("unused")
public class AuthorizationException extends Exception {
    public AuthorizationException() {super();}
    public AuthorizationException(String message) {super(message);}
    public AuthorizationException(String message, Throwable cause) {super(message,cause);}
    public AuthorizationException(String message, String host, String user) {super(message);}
    public AuthorizationException(Throwable cause) {super(cause);}
    public AuthorizationException(String message, String host, String user, Throwable cause) {super(message, cause);}
}
