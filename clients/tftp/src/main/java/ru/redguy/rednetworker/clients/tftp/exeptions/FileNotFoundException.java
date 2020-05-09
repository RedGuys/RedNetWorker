package ru.redguy.rednetworker.clients.tftp.exeptions;

@SuppressWarnings("unused")
public class FileNotFoundException extends Exception {
    public FileNotFoundException() {super();}
    public FileNotFoundException(String message) {super(message);}
    public FileNotFoundException(String message, Throwable cause) {super(message,cause);}
    public FileNotFoundException(String message, String host, int port, String user) {super(message);}
    public FileNotFoundException(String message, String host, int port, Throwable cause) {super(message, cause);}
    public FileNotFoundException(Throwable cause) {super(cause);}
    public FileNotFoundException(String message, String host, int port, String user, Throwable cause) {super(message, cause);}
    public FileNotFoundException(String message, String host, int port, String user, String file, Throwable cause) {super(message, cause);}
}
