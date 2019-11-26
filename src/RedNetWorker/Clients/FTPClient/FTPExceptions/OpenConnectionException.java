package RedNetWorker.Clients.FTPClient.FTPExceptions;

public class OpenConnectionException extends Exception {
    public OpenConnectionException() {super();}
    public OpenConnectionException(String message) {super(message);}
    public OpenConnectionException(String message, Throwable cause) {super(message,cause);}
    public OpenConnectionException(String message, String host, int port) {super(message);}
    public OpenConnectionException(Throwable cause) {super(cause);}
    public OpenConnectionException(String message, String host, int port, Throwable cause) {super(message, cause);}
}
