package ru.redguy.rednetworker.clients.pop3.exeptions;

public class FolderException extends Exception {
    public FolderException() {super();}
    public FolderException(String message) {super(message);}
    public FolderException(String message, Throwable cause) {super(message,cause);}
    public FolderException(String message, String host, int port) {super(message);}
    public FolderException(Throwable cause) {super(cause);}
    public FolderException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
