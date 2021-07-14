package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

public interface IHttpClient {

    IHttpClient method(HttpMethod httpMethod);

    IHttpClient url(String url);

    IHttpClient setGetParams(Map<String, Object> params);

    IHttpClient setPostParams(Map<String, Object> params);

    IHttpClient setPostBody(String body);

    IHttpClient setByteBody(byte[] bytes);

    IHttpClient setFileBody(File file);

    IHttpClient setStreamBody(InputStream stream);

    default IHttpClient setRequestCharset(Charset charset) {return null;};

    default IHttpClient setResponseCharset(Charset charset) {return null;};

    IHttpClient setContentType(String contentType);

    IHttpResponse execute() throws HttpProtocolException, IOException;
}
