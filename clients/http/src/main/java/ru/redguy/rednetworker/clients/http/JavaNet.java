package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.utils.HttpUtils;
import ru.redguy.rednetworker.clients.http.exceptions.*;

import java.io.*;
import java.net.*;
import java.util.Map;

public class JavaNet implements IHttpClient {
    private Proxy proxy = null;
    public final int connectionTimeout = 5000;
    public final int readTimeout = 5000;

    public JavaNet() {

    }

    public JavaNet(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public JavaNetResponse get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        URL url;
        try {
            url = new URL(uri+ HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpURLConnection connection;
        if(proxy != null) {
            try {
                connection = (HttpURLConnection) url.openConnection(this.proxy);
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        } else {
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        }
        connection.setConnectTimeout(this.connectionTimeout);
        connection.setReadTimeout(this.readTimeout);
        try {
            return new JavaNetResponse(connection.getInputStream());
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public JavaNetResponse get(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return get(url, (Map<String, Object>) null);
    }

    @Override
    public JavaNetResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException {
        URL url;
        try {
            url = new URL(uri+HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpURLConnection connection;
        if(proxy != null) {
            try {
                connection = (HttpURLConnection) url.openConnection(this.proxy);
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        } else {
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        }
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        }
        connection.setConnectTimeout(this.connectionTimeout);
        connection.setReadTimeout(this.readTimeout);
        try {
            if (postArgs != null) {
                connection.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(HttpUtils.buildPost(postArgs));
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            throw new OutputStreamException(e.getMessage(),uri,e.getCause());
        }
        try {
            return new JavaNetResponse(connection.getInputStream());
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public JavaNetResponse post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException {
        return post(uri, postArgs, (Map<String, Object>) null);
    }

    @Override
    public JavaNetResponse post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException {
        return post(uri, null, (Map<String, Object>) null);
    }
}
