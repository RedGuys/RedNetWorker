package ru.redguy.rednetworker.clients.http;

import okhttp3.*;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.HttpUtils;
import ru.redguy.rednetworker.utils.Protocols;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class OKHttp implements IHttpClient {

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

    public OKHttp() {

    }

    @Override
    public OKHttp method(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public OKHttp url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public OKHttp setGetParams(Map<String, Object> params) {
        this.getParams = params;
        return this;
    }

    @Override
    public OKHttp setPostParams(Map<String, Object> params) {
        bodyType = BodyType.params;
        postParams = params;
        return this;
    }

    @Override
    public OKHttp setPostBody(String body) {
        bodyType = BodyType.text;
        postTextBody = body;
        return this;
    }

    @Override
    public OKHttp setByteBody(byte[] bytes) {
        bodyType = BodyType.bytes;
        postByteBody = bytes;
        return this;
    }

    @Override
    public OKHttp setFileBody(File file) {
        bodyType = BodyType.file;
        postFileBody = file;
        return this;
    }

    @Override
    public OKHttp setStreamBody(InputStream stream) {
        bodyType = BodyType.stream;
        postStreamBody = stream;
        return this;
    }

    @Override
    public OKHttp setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public OKHttp setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public OKHttpResponse execute() throws HttpProtocolException, IOException {
        switch (httpMethod) {
            case GET:
                return get();
            case POST:
                return post();
        }
        return null;
    }

    private OKHttpResponse get() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Protocols.formatUrlString(url, "http") + HttpUtils.buildGet(getParams)).build();
        Response response = client.newCall(request).execute();
        return new OKHttpResponse(response);
    }

    private OKHttpResponse post() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        request.url(Protocols.formatUrlString(url, "http") + HttpUtils.buildGet(getParams));

        switch (bodyType) {
            case text:
                request.post(RequestBody.create(MediaType.parse(contentType),postTextBody));
                break;
            case stream:
                request.post(RequestBody.create(MediaType.parse(contentType), IOUtils.readAllBytes(postStreamBody)));
                break;
            case bytes:
                request.post(RequestBody.create(MediaType.parse(contentType), postByteBody));
                break;
            case file:
                request.post(RequestBody.create(MediaType.parse(contentType),postFileBody));
                break;
            case params:
                request.post(RequestBody.create(MediaType.parse(contentType),HttpUtils.buildPost(postParams)));
                break;
        }

        Response response = client.newCall(request.build()).execute();
        return new OKHttpResponse(response);
    }
}
