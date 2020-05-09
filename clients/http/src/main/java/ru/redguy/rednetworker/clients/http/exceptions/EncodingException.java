package ru.redguy.rednetworker.clients.http.exceptions;

@SuppressWarnings("unused")
public class EncodingException extends Exception {
    public EncodingException() {super();}
    public EncodingException(String message) {super(message);}
    public EncodingException(String message, Throwable cause) {super(message,cause);}
    public EncodingException(String message, String url) {super(message);}
    public EncodingException(Throwable cause) {super(cause);}
    public EncodingException(String message,String url,Throwable cause) {super(message, cause);}
}
