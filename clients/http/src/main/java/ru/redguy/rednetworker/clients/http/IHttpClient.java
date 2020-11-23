package ru.redguy.rednetworker.clients.http;

import org.apache.http.Header;
import ru.redguy.rednetworker.utils.NotImplementedException;
import ru.redguy.rednetworker.clients.http.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
public interface IHttpClient {

    IHttpClient method(HttpMethod httpMethod);

    IHttpClient url(String url);

    IHttpClient setGetParams(Map<String, Object> params);

    IHttpClient setPostParams(Map<String, Object> params);

    IHttpClient setPostBody(String body);

    IHttpClient setCharset(Charset charset);

    IHttpResponse execute() throws HttpProtocolException, IOException;
}
