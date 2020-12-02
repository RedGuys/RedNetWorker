package ru.redguy.rednetworker.clients.pop3.exeptions;

public class MessagingException extends Exception {
    public MessagingException() {super();}
    public MessagingException(String message) {super(message);}
    public MessagingException(String message, Throwable cause) {super(message,cause);}
    public MessagingException(String message, String host, int port) {super(message);}
    public MessagingException(Throwable cause) {super(cause);}
    public MessagingException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
