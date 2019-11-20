package RedNetWorker.Clients.HttpClient;

public class HttpExceptions {
    public static class URLException extends Exception {
        public URLException() {super();}
        public URLException(String message) {super(message);}
        public URLException(String message, Throwable cause) {super(message,cause);}
        public URLException(String message, String url) {super(message);}
        public URLException(Throwable cause) {super(cause);}
        public URLException(String message,String url,Throwable cause) {super(message, cause);}
    }
    public static class OpenConnectionException extends Exception {
        public OpenConnectionException() {super();}
        public OpenConnectionException(String message) {super(message);}
        public OpenConnectionException(String message, Throwable cause) {super(message,cause);}
        public OpenConnectionException(String message, String url) {super(message);}
        public OpenConnectionException(Throwable cause) {super(cause);}
        public OpenConnectionException(String message,String url,Throwable cause) {super(message, cause);}
    }
    public static class HttpProtocolException extends Exception {
        public HttpProtocolException() {super();}
        public HttpProtocolException(String message) {super(message);}
        public HttpProtocolException(String message, Throwable cause) {super(message,cause);}
        public HttpProtocolException(String message, String url) {super(message);}
        public HttpProtocolException(Throwable cause) {super(cause);}
        public HttpProtocolException(String message,String url,Throwable cause) {super(message, cause);}
    }
    public static class InputStreamException extends Exception {
        public InputStreamException() {super();}
        public InputStreamException(String message) {super(message);}
        public InputStreamException(String message, Throwable cause) {super(message,cause);}
        public InputStreamException(String message, String url) {super(message);}
        public InputStreamException(Throwable cause) {super(cause);}
        public InputStreamException(String message,String url,Throwable cause) {super(message, cause);}
    }
    public static class OutputStreamException extends Exception {
        public OutputStreamException() {super();}
        public OutputStreamException(String message) {super(message);}
        public OutputStreamException(String message, Throwable cause) {super(message,cause);}
        public OutputStreamException(String message, String url) {super(message);}
        public OutputStreamException(Throwable cause) {super(cause);}
        public OutputStreamException(String message,String url,Throwable cause) {super(message, cause);}
    }
    public static class EncodingException extends Exception {
        public EncodingException() {super();}
        public EncodingException(String message) {super(message);}
        public EncodingException(String message, Throwable cause) {super(message,cause);}
        public EncodingException(String message, String url) {super(message);}
        public EncodingException(Throwable cause) {super(cause);}
        public EncodingException(String message,String url,Throwable cause) {super(message, cause);}
    }
}
