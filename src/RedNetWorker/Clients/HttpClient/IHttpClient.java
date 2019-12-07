package RedNetWorker.Clients.HttpClient;

import RedNetWorker.Clients.HttpClient.HttpExceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public interface IHttpClient {
    public InputStream get(String uri, Map<String,Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException;

    public InputStream get(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    public String getString(String url, Map<String,Object> args) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    public String getString(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    public InputStream post(String uri, Map<String,Object> postArgs, Map<String,Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException;

    public InputStream post(String uri, Map<String,Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException;

    public InputStream post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException;

    public String postString(String url, Map<String,Object> postArgs, Map<String,Object> getArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException;

    public String postString(String url, Map<String,Object> postArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException;

    public String postString(String url) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException;

    public File DownloadFile(String uri, String pathToFile, Map<String,Object> getArgs) throws URLException, OpenConnectionException, FileNotFoundException, InputStreamException, OutputStreamException;

    public File DownloadFile(String uri, String pathToFile) throws FileNotFoundException, URLException, OutputStreamException, OpenConnectionException, InputStreamException;
}
