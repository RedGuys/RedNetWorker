package ru.redguy.rednetworker.clients.ftp.exceptions;

@SuppressWarnings("unused")
public class UnknownServerErrorException extends Exception {
    public UnknownServerErrorException() {super();}
    public UnknownServerErrorException(String message) {super(message);}
    public UnknownServerErrorException(String message, Throwable cause) {super(message,cause);}
    public UnknownServerErrorException(String message, String host, int port, String user) {super(message);}
    public UnknownServerErrorException(String message, String host, int port, Throwable cause) {super(message, cause);}
    public UnknownServerErrorException(Throwable cause) {super(cause);}
    public UnknownServerErrorException(String message, String host, int port, String user, Throwable cause) {super(message, cause);}
}
