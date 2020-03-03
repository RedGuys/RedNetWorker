package ru.redguy.rednetworker.clients.ftp.exceptions;

@SuppressWarnings("unused")
public class AbortedException extends Exception {
    public AbortedException() {super();}
    public AbortedException(String message) {super(message);}
    public AbortedException(String message, Throwable cause) {super(message,cause);}
    public AbortedException(String message, String host, int port, String user) {super(message);}
    public AbortedException(String message, String host, int port, Throwable cause) {super(message, cause);}
    public AbortedException(Throwable cause) {super(cause);}
    public AbortedException(String message, String host, int port, String user, Throwable cause) {super(message, cause);}
}
