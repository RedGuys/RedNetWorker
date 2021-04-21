package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.utils.HttpUtils;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class JavaNet implements IHttpClient {

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

    public JavaNet() {

    }

    @Override
    public JavaNet method(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public JavaNet url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public JavaNet setGetParams(Map<String, Object> params) {
        getParams = params;
        return this;
    }

    @Override
    public JavaNet setPostParams(Map<String, Object> params) {
        this.bodyType = BodyType.params;
        postParams = params;
        return this;
    }

    @Override
    public JavaNet setPostBody(String body) {
        this.bodyType = BodyType.text;
        postTextBody = body;
        return this;
    }

    @Override
    public JavaNet setByteBody(byte[] bytes) {
        this.bodyType = BodyType.bytes;
        postByteBody = bytes;
        return this;
    }

    @Override
    public JavaNet setFileBody(File file) {
        this.bodyType = BodyType.file;
        postFileBody = file;
        return this;
    }

    @Override
    public JavaNet setStreamBody(InputStream stream) {
        this.bodyType = BodyType.stream;
        postStreamBody = stream;
        return this;
    }

    @Override
    public JavaNet setRequestCharset(Charset charset) {
        requestCharset = charset;
        return this;
    }

    @Override
    public JavaNet setResponseCharset(Charset charset) {
        responseCharset = charset;
        return this;
    }

    @Override
    public JavaNet setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public JavaNetResponse execute() throws HttpProtocolException, IOException {
        switch (httpMethod) {
            case GET:
                return get();
            case POST:
                return post();
        }
        return null;
    }

    private JavaNetResponse get() throws IOException, HttpProtocolException {
        URL url = new URL(Protocols.formatUrlString(this.url,"http") + HttpUtils.buildGet(getParams));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new HttpProtocolException(e.getCause(),e.getMessage());
        }
        try {
            return new JavaNetResponse(connection.getInputStream());
        } catch (FileNotFoundException e) {
            throw new HttpProtocolException("Not Found",404,null);
        }
    }

    private JavaNetResponse post() throws HttpProtocolException, IOException {
        URL url = new URL(Protocols.formatUrlString(this.url,"http")+HttpUtils.buildGet(getParams));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new HttpProtocolException(e.getCause(),e.getMessage());
        }
        switch (bodyType) {
            case params:
                if (postParams.size() != 0) {
                    connection.setDoOutput(true);
                    PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                    printWriter.print(HttpUtils.buildPost(postParams));
                    printWriter.flush();
                    printWriter.close();
                }
                break;
            case file:
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                FileInputStream fis = new FileInputStream(postFileBody);
                byte[] buf = new byte[4096];
                int len = 0;
                while ((len = fis.read(buf)) >= 0)
                {
                    os.write(buf, 0, len);
                }
                os.flush();
                os.close();
                fis.close();
                break;
            case bytes:
                connection.setDoOutput(true);
                os = connection.getOutputStream();
                os.write(postByteBody);
                os.flush();
                os.close();
                break;
            case stream:
                connection.setDoOutput(true);
                os = connection.getOutputStream();
                buf = new byte[4096];
                len = 0;
                while ((len = postStreamBody.read(buf)) >= 0)
                {
                    os.write(buf, 0, len);
                }
                os.flush();
                os.close();
                break;
            case text:
                connection.setDoOutput(true);
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.print(postTextBody);
                printWriter.flush();
                printWriter.close();
                break;
        }
        try {
            return new JavaNetResponse(connection.getInputStream());
        } catch (FileNotFoundException e) {
            throw new HttpProtocolException("Not Found",404,null);
        }
    }
}
