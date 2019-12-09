package ru.redguy.rednetworker.clients.sftp.exceptions;

public class ServerMethodErrorException extends Exception {
    public ServerMethodErrorException() {super();}
    public ServerMethodErrorException(String message) {super(message);}
    public ServerMethodErrorException(String message, Throwable cause) {super(message,cause);}
    public ServerMethodErrorException(String message, String host, int port) {super(message);}
    public ServerMethodErrorException(Throwable cause) {super(cause);}
    public ServerMethodErrorException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
