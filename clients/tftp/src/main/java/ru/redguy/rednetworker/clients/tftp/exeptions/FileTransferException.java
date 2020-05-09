package ru.redguy.rednetworker.clients.tftp.exeptions;

public class FileTransferException extends Exception {
    public FileTransferException() {super();}
    public FileTransferException(String message) {super(message);}
    public FileTransferException(String message, Throwable cause) {super(message,cause);}
    public FileTransferException(String message, String host, int port) {super(message);}
    public FileTransferException(Throwable cause) {super(cause);}
    public FileTransferException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
