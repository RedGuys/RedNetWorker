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
    default IHttpResponse get(String uri, Map<String, Object> args, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException, NotImplementedException {
        throw new NotImplementedException();
    }

    IHttpResponse get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException;

    default IHttpResponse get(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException, NotImplementedException {
        throw new NotImplementedException();
    }

    IHttpResponse get(String uri) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException;

    default IHttpResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    IHttpResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException;

    default IHttpResponse post(String uri, String data) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    default IHttpResponse post(String uri, Map<String, Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    IHttpResponse post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException;

    default IHttpResponse post(String uri, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException, NotImplementedException {
        throw new NotImplementedException();
    }

    IHttpResponse post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException;
}
