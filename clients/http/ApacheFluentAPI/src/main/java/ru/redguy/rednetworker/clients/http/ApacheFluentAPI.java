package ru.redguy.rednetworker.clients.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.utils.HttpUtils;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private Charset requestCharset = Charset.defaultCharset();
    private Charset responseCharset = Charset.defaultCharset();
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
    public IHttpClient setRequestCharset(Charset charset) {
        this.requestCharset = charset;
        return this;
    }

    @Override
    public IHttpClient setResponseCharset(Charset charset) {
        this.responseCharset = charset;
        return this;
    }

    @Override
    public ApacheFluentAPI setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public ApacheFluentAPIResponse execute() throws HttpProtocolException, IOException {
        if (httpMethod == HttpMethod.POST) {
            return post();
        }
        return get();
    }

    private ApacheFluentAPIResponse get() throws HttpProtocolException, IOException {
        String url = Protocols.formatUrlString(this.url,"http") + HttpUtils.buildGet(getParams);
        try {
            Response response = Request.Get(url).execute();
            HttpResponse httpResponse = response.returnResponse();
            if(httpResponse.getStatusLine().getStatusCode() >= 400) {
                throw new HttpProtocolException(
                        httpResponse.getStatusLine().getReasonPhrase(),
                        httpResponse.getStatusLine().getStatusCode(),
                        new ApacheFluentAPIResponse(httpResponse,responseCharset));
            } else {
                return new ApacheFluentAPIResponse(httpResponse,responseCharset);
            }
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getCause(),e.getMessage());
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
                    request.bodyForm(params, requestCharset);
                }
                break;
            case text:
                request.bodyString(postTextBody, ContentType.parse(contentType).withCharset(requestCharset));
                break;
            case bytes:
                request.bodyByteArray(postByteBody);
                break;
            case file:
                request.bodyFile(postFileBody,ContentType.parse(contentType).withCharset(requestCharset));
                break;
            case stream:
                request.bodyStream(postStreamBody);
                break;
        }
        try {
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            if(httpResponse.getStatusLine().getStatusCode() >= 400) {
                throw new HttpProtocolException(
                        httpResponse.getStatusLine().getReasonPhrase(),
                        httpResponse.getStatusLine().getStatusCode(),
                        new ApacheFluentAPIResponse(httpResponse,responseCharset)
                );
            } else {
                return new ApacheFluentAPIResponse(httpResponse,responseCharset);
            }
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getCause(),e.getMessage());
        }
    }
}
