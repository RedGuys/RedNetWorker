package ru.redguy.rednetworker.clients.http;

import org.apache.http.entity.ContentType;
import ru.redguy.rednetworker.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApacheFluentAPI implements IHttpClient {

    private HttpMethod httpMethod = HttpMethod.GET;
    private String url = null;
    private Map<String, Object> getParams = new HashMap<>();
    private Map<String, Object> postParams = new HashMap<>();
    private String postTextBody = null;
    private byte[] postByteBody = null;
    private File postFileBody = null;
    private InputStream postStreamBody = null;
    private BodyType bodyType = BodyType.params;
    private Charset charset = Charset.defaultCharset();
    private String contentType = "text/plain";

    public ApacheFluentAPI() {

    }

    public ApacheFluentAPI(String url) {
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
        bodyType = BodyType.params;
        postParams = params;
        return this;
    }

    @Override
    public ApacheFluentAPI setPostBody(String body) {
        bodyType = BodyType.text;
        this.postTextBody = body;
        return this;
    }

    @Override
    public ApacheFluentAPI setByteBody(byte[] bytes) {
        bodyType = BodyType.bytes;
        this.postByteBody = bytes;
        return this;
    }

    @Override
    public ApacheFluentAPI setFileBody(File file) {
        bodyType = BodyType.file;
        this.postFileBody = file;
        return this;
    }

    @Override
    public ApacheFluentAPI setStreamBody(InputStream stream) {
        bodyType = BodyType.stream;
        this.postStreamBody = stream;
        return this;
    }

    @Override
    public ApacheFluentAPI setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public ApacheFluentAPI setContentType(String contentType) {
        this.contentType = contentType;
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
        String url = Protocols.formatUrlString(this.url, "http") + HttpUtils.buildGet(getParams);
        Request request = Request.Post(url);
        switch (bodyType) {
            case params:
                if (!postParams.isEmpty()) {
                    List<NameValuePair> params = new ArrayList<>();
                    this.postParams.forEach((name, value) -> params.add(new BasicNameValuePair(name, String.valueOf(value))));
                    request.bodyForm(params, charset);
                }
                break;
            case text:
                request.bodyString(postTextBody, ContentType.parse(contentType));
                break;
            case bytes:
                request.bodyByteArray(postByteBody);
                break;
            case file:
                request.bodyFile(postFileBody,ContentType.parse(contentType));
                break;
            case stream:
                request.bodyStream(postStreamBody);
                break;
        }
        try {
            return new ApacheFluentAPIResponse(request.execute());
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(), e.getCause());
        }
    }
}
