package ru.redguy.rednetworker.clients.tftp.exeptions;

public class UnknownHostException extends Exception {
    public UnknownHostException() {super();}
    public UnknownHostException(String message) {super(message);}
    public UnknownHostException(String message, Throwable cause) {super(message,cause);}
    public UnknownHostException(String message, String host, int port) {super(message);}
    public UnknownHostException(Throwable cause) {super(cause);}
    public UnknownHostException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
