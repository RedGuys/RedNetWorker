package ru.redguy.rednetworker.clients.smtp.exceptions;

public class MessagingException extends Exception {
    public MessagingException() {super();}
    public MessagingException(String message) {super(message);}
    public MessagingException(String message, Throwable cause) {super(message,cause);}
    public MessagingException(Throwable cause) {super(cause);}
}
