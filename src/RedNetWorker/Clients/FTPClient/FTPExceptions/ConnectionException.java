package RedNetWorker.Clients.FTPClient.FTPExceptions;

public class ConnectionException extends Exception{
    public ConnectionException() {super();}
    public ConnectionException(String message) {super(message);}
    public ConnectionException(String message, Throwable cause) {super(message,cause);}
    public ConnectionException(String message, String host, int port, String user) {super(message);}
    public ConnectionException(String message, String host, int port, Throwable cause) {super(message, cause);}
    public ConnectionException(Throwable cause) {super(cause);}
    public ConnectionException(String message, String host, int port, String user, Throwable cause) {super(message, cause);}
}
