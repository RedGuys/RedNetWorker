package RedNetWorker.Clients.HttpClient.HttpExceptions;

public class InputStreamException extends Exception {
    public InputStreamException() {super();}
    public InputStreamException(String message) {super(message);}
    public InputStreamException(String message, Throwable cause) {super(message,cause);}
    public InputStreamException(String message, String url) {super(message);}
    public InputStreamException(Throwable cause) {super(cause);}
    public InputStreamException(String message,String url,Throwable cause) {super(message, cause);}
}
