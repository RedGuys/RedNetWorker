package RedNetWorker.Clients.HttpClient.HttpExceptions;

public class OutputStreamException extends Exception {
    public OutputStreamException() {super();}
    public OutputStreamException(String message) {super(message);}
    public OutputStreamException(String message, Throwable cause) {super(message,cause);}
    public OutputStreamException(String message, String url) {super(message);}
    public OutputStreamException(Throwable cause) {super(cause);}
    public OutputStreamException(String message,String url,Throwable cause) {super(message, cause);}
}
