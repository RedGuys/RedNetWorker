package ru.redguy.rednetworker.clients.http;

import org.apache.http.entity.ContentType;
import ru.redguy.rednetworker.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.NotImplementedException;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ApacheFluentAPI implements IHttpClient {

    private HttpMethod httpMethod = HttpMethod.GET;
    private String url = null;
    private Map<String, Object> getParams = new HashMap<>();

    ApacheFluentAPI() {

    }

    ApacheFluentAPI(String url) {
        this.url = url;
    }

    @Override
    public IHttpClient method(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public IHttpClient url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IHttpClient getParams(Map<String, Object> params) {
        this.getParams = params;
        return this;
    }

    @Override
    public IHttpResponse execute() {
        switch (httpMethod) {
            case GET:
                return get();
                break;
            case POST:

                break;
        }
    }

    private IHttpResponse get() throws URLException, OpenConnectionException {
        URL url = null;
        try {
            url = new URL(Protocols.formatUrlString(this.url,"http") + HttpUtils.buildGet(getParams));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),this.url,e.getCause());
        }
        try {
            return new ApacheFluentAPIResponse(Request.Get(url.toURI()).execute());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),this.url,e.getCause());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),this.url,e.getCause());
        }
    }

    @Override
    public ApacheFluentAPIResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        List<NameValuePair> params = new ArrayList<>();
        if(postArgs != null) {
            List<NameValuePair> finalParams = params;
            postArgs.forEach((name, value) -> finalParams.add(new BasicNameValuePair(name, (String) value)));
            params = finalParams;
        }
        try {
            return new ApacheFluentAPIResponse(Request.Post(url.toURI()).bodyForm(params, Charset.defaultCharset()).execute()); //TODO: fix error with 301 HTTP code
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public ApacheFluentAPIResponse post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException {
        return post(uri, postArgs , (Map<String, Object>) null);
    }

    @Override
    public ApacheFluentAPIResponse post(String uri) throws URLException, OpenConnectionException {
        return post(uri, null, (Map<String, Object>) null);
    }
}
