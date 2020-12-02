package ru.redguy.rednetworker.clients.pop3.exeptions;

public class NoSuchProviderException extends Exception {
    public NoSuchProviderException() {super();}
    public NoSuchProviderException(String message) {super(message);}
    public NoSuchProviderException(String message, Throwable cause) {super(message,cause);}
    public NoSuchProviderException(String message, String host, int port) {super(message);}
    public NoSuchProviderException(Throwable cause) {super(cause);}
    public NoSuchProviderException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
