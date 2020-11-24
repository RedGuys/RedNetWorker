package ru.redguy.rednetworker.clients.http;

import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.entity.ContentType;
import ru.redguy.rednetworker.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class ApacheHttpClient implements IHttpClient {

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

    public ApacheHttpClient() {

    }

    @Override
    public ApacheHttpClient method(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public ApacheHttpClient url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public ApacheHttpClient setGetParams(Map<String, Object> params) {
        this.getParams = params;
        return this;
    }

    @Override
    public ApacheHttpClient setPostParams(Map<String, Object> params) {
        this.bodyType = BodyType.params;
        this.postParams = params;
        return this;
    }

    @Override
    public ApacheHttpClient setPostBody(String body) {
        this.bodyType = BodyType.text;
        this.postTextBody = body;
        return this;
    }

    @Override
    public ApacheHttpClient setByteBody(byte[] bytes) {
        this.bodyType = BodyType.bytes;
        this.postByteBody = bytes;
        return this;
    }

    @Override
    public ApacheHttpClient setFileBody(File file) {
        this.bodyType = BodyType.file;
        this.postFileBody = file;
        return this;
    }

    @Override
    public ApacheHttpClient setStreamBody(InputStream stream) {
        this.bodyType = BodyType.stream;
        this.postStreamBody = stream;
        return this;
    }

    @Override
    public ApacheHttpClient setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public ApacheHttpClient setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public ApacheHttpClientResponse execute() throws HttpProtocolException, IOException {
        switch (httpMethod) {
            case GET:
                return get();
            case POST:
                return post();
        }
        return null;
    }

    private ApacheHttpClientResponse get() throws IOException, HttpProtocolException {
        HttpGet get = new HttpGet(Protocols.formatUrlString(url,"http") + HttpUtils.buildGet(getParams));
        get.setConfig(RequestConfig.DEFAULT);
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            return new ApacheHttpClientResponse(client.execute(get));
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),e.getCause());
        }
    }

    private ApacheHttpClientResponse post() throws HttpProtocolException, IOException {
        HttpPost post = new HttpPost(Protocols.formatUrlString(url,"http")+HttpUtils.buildGet(getParams));
        post.setConfig(RequestConfig.DEFAULT);
        switch (bodyType) {
            case params:
                if (!postParams.isEmpty()) {
                    List<NameValuePair> params = new ArrayList<>();
                    this.postParams.forEach((name, value) -> params.add(new BasicNameValuePair(name, String.valueOf(value))));
                    post.setEntity(new UrlEncodedFormEntity(params,charset));
                }
                break;
            case text:
                post.setEntity(EntityBuilder.create()
                        .setText(postTextBody)
                        .setContentType(ContentType.parse(contentType))
                        .build()
                );
                break;
            case stream:
                post.setEntity(EntityBuilder.create()
                        .setStream(postStreamBody)
                        .setContentType(ContentType.parse(contentType))
                        .build()
                );
                break;
            case file:
                post.setEntity(EntityBuilder.create()
                        .setFile(postFileBody)
                        .setContentType(ContentType.parse(contentType))
                        .build());
                break;
            case bytes:
                post.setEntity(EntityBuilder.create()
                        .setBinary(postByteBody)
                        .setContentType(ContentType.parse(contentType))
                        .build()
                );
                break;
        }
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            return new ApacheHttpClientResponse(client.execute(post));
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),e.getCause());
        }
    }
}
