package RedNetWorker.Clients.FTPClient.FTPExceptions;

public class FTPFileNotFoundException extends Exception {
    public FTPFileNotFoundException() {super();}
    public FTPFileNotFoundException(String message) {super(message);}
    public FTPFileNotFoundException(String message, Throwable cause) {super(message,cause);}
    public FTPFileNotFoundException(String message, String host, int port, String user) {super(message);}
    public FTPFileNotFoundException(String message, String host, int port, Throwable cause) {super(message, cause);}
    public FTPFileNotFoundException(Throwable cause) {super(cause);}
    public FTPFileNotFoundException(String message, String host, int port, String user, Throwable cause) {super(message, cause);}
    public FTPFileNotFoundException(String message, String host, int port, String user, String file, Throwable cause) {super(message, cause);}
}
