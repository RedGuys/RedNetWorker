package RedNetWorker.Clients.HttpClient;

import RedNetWorker.Clients.HttpClient.HttpExceptions.*;
import org.apache.http.Header;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public interface IHttpClient {
    public default InputStream get(String uri, Map<String, Object> args, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        throw new NotImplementedException();
    }

    public InputStream get(String uri, Map<String,Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException;

    public default InputStream get(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        throw new NotImplementedException();
    }

    public InputStream get(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    public default String getString(String url, Map<String,Object> args, ArrayList<Header> headers) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        throw new NotImplementedException();
    }

    public String getString(String url, Map<String,Object> args) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    public default String getString(String url, ArrayList<Header> headers) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        throw new NotImplementedException();
    }

    public String getString(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    public default InputStream post(String uri, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        throw new NotImplementedException();
    }

    public InputStream post(String uri, Map<String,Object> postArgs, Map<String,Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException;

    public default InputStream post(String uri, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        throw new NotImplementedException();
    }

    public InputStream post(String uri, Map<String,Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException;

    public default InputStream post(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        throw new NotImplementedException();
    }

    public InputStream post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    public default String postString(String url, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        throw new NotImplementedException();
    }

    public String postString(String url, Map<String,Object> postArgs, Map<String,Object> getArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    public default String postString(String url, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        throw new NotImplementedException();
    }

    public String postString(String url, Map<String,Object> postArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    public default String postString(String url, ArrayList<Header> headers) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        throw new NotImplementedException();
    }

    public String postString(String url) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    public File downloadFile(String uri, String pathToFile, Map<String,Object> getArgs) throws URLException, OpenConnectionException, FileNotFoundException, InputStreamException, OutputStreamException;

    public File downloadFile(String uri, String pathToFile) throws FileNotFoundException, URLException, OutputStreamException, OpenConnectionException, InputStreamException;
}
