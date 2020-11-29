package ru.redguy.rednetworker.clients.smtp.exceptions;

public class IncorrectAddressException extends Exception {
    public IncorrectAddressException() {super();}
    public IncorrectAddressException(String message) {super(message);}
    public IncorrectAddressException(String message, Throwable cause) {super(message,cause);}
    public IncorrectAddressException(Throwable cause) {super(cause);}
}
