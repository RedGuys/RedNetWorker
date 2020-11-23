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
    private Map<String, Object> postParams = new HashMap<>();
    private String body = "";
    private boolean isParamsBody = true;
    private Charset charset = Charset.defaultCharset();
    private String contentType = "text/plain";

    ApacheFluentAPI() {

    }

    ApacheFluentAPI(String url) {
        this.url = url;
    }

    @Override
    public ApacheFluentAPI method(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public ApacheFluentAPI url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public ApacheFluentAPI setGetParams(Map<String, Object> params) {
        this.getParams = params;
        return this;
    }

    @Override
    public ApacheFluentAPI setPostParams(Map<String, Object> params) {
        isParamsBody = true;
        postParams = params;
        return this;
    }

    @Override
    public ApacheFluentAPI setPostBody(String body) {
        isParamsBody = false;
        this.body = body;
        return this;
    }

    @Override
    public IHttpClient setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public ApacheFluentAPIResponse execute() throws HttpProtocolException, IOException {
        switch (httpMethod) {
            case GET:
                return get();
            case POST:
                return post();
        }
        return null;
    }

    private ApacheFluentAPIResponse get() throws HttpProtocolException, IOException {
        String url = Protocols.formatUrlString(this.url,"http") + HttpUtils.buildGet(getParams);
        try {
            return new ApacheFluentAPIResponse(Request.Get(url).execute());
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),e.getCause());
        }
    }

    private ApacheFluentAPIResponse post() throws HttpProtocolException, IOException {
        String url = Protocols.formatUrlString(this.url,"http") + HttpUtils.buildGet(getParams);
        Request request = Request.Post(url);
        if(isParamsBody) {//TODO: библа может не только в параметры и string
            if(!postParams.isEmpty()) {
                List<NameValuePair> params = new ArrayList<>();
                this.postParams.forEach((name, value) -> params.add(new BasicNameValuePair(name, String.valueOf(value))));
                request.bodyForm(params,charset);
            }
        } else {
            request.bodyString(body,ContentType.parse(contentType));
        }
        try {
            return new ApacheFluentAPIResponse(request.execute());
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),e.getCause());
        }
    }
}
