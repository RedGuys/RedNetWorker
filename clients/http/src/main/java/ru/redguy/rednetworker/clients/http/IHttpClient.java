package ru.redguy.rednetworker.clients.http;

import org.apache.http.Header;
import ru.redguy.rednetworker.utils.NotImplementedException;
import ru.redguy.rednetworker.clients.http.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
public interface IHttpClient {
    default InputStream get(String uri, Map<String, Object> args, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException, NotImplementedException {
        throw new NotImplementedException();
    }

    InputStream get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException;

    default InputStream get(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException, NotImplementedException {
        throw new NotImplementedException();
    }

    InputStream get(String uri) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    default String getString(String uri, Map<String, Object> args, ArrayList<Header> headers) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException, NotImplementedException {
        throw new NotImplementedException();
    }

    String getString(String uri, Map<String, Object> args) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    default String getString(String uri, ArrayList<Header> headers) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException, NotImplementedException {
        throw new NotImplementedException();
    }

    String getString(String uri) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    default InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException;

    default InputStream post(String uri, Map<String, Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    InputStream post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException;

    default InputStream post(String uri, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    InputStream post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    default String postString(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    String postString(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    default String postString(String uri, Map<String, Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    String postString(String uri, Map<String, Object> postArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    default String postString(String uri, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    String postString(String url) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;

    File downloadFile(String uri, String pathToFile, Map<String, Object> getArgs) throws URLException, OpenConnectionException, FileNotFoundException, InputStreamException, OutputStreamException, HttpProtocolException;

    File downloadFile(String uri, String pathToFile) throws FileNotFoundException, URLException, OutputStreamException, OpenConnectionException, InputStreamException, HttpProtocolException;
}
