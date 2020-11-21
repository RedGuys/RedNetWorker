package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.utils.HttpUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ApacheHttpClient implements IHttpClient {
    private Proxy proxy = null;
    public final int connectionTimeout = 5000;
    public int readTimeout = 5000;

    public ApacheHttpClient() {

    }

    public ApacheHttpClient(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public ApacheHttpClientResponse get(String uri, Map<String, Object> args, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpGet req;
        try {
            req = new HttpGet(url.toURI());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),url.toString(),e.getCause());
        }
        req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
        if(headers.size() > 0) {
            headers.forEach(req::setHeader);
        }
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(req) ) {
            try {
                return new ApacheHttpClientResponse(response.getEntity().getContent());
            } catch (IOException e) {
                throw new InputStreamException(e.getMessage(),uri,e.getCause());
            }
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public ApacheHttpClientResponse get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpGet req;
        try {
            req = new HttpGet(url.toURI());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),url.toString(),e.getCause());
        }
        req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(req) ) {
            try {
                return new ApacheHttpClientResponse(response.getEntity().getContent());
            } catch (IOException e) {
                throw new InputStreamException(e.getMessage(),uri,e.getCause());
            }
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public ApacheHttpClientResponse get(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        return get(uri, null, headers);
    }

    @Override
    public ApacheHttpClientResponse get(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return get(url, (Map<String, Object>) null);
    }

    @Override
    public ApacheHttpClientResponse post(String uri, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpPost req;
        try {
            req = new HttpPost(url.toURI());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        if(headers.size() > 0) {
            headers.forEach(req::setHeader);
        }
        List<NameValuePair> params = new ArrayList<>();
        if(postArgs != null) {
            List<NameValuePair> finalParams1 = params;
            postArgs.forEach((name, value) -> finalParams1.add(new BasicNameValuePair(name, (String) value)));
            params = finalParams1;
        }
        try {
            req.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException(e.getMessage(),uri,e.getCause());
        }
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(req) ) {
            try {
                return new ApacheHttpClientResponse(response.getEntity().getContent());
            } catch (IOException e) {
                throw new InputStreamException(e.getMessage(),uri,e.getCause());
            }
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApacheHttpClientResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpPost req;
        try {
            req = new HttpPost(url.toURI());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        List<NameValuePair> params = new ArrayList<>();
        if(postArgs != null) {
            List<NameValuePair> finalParams1 = params;
            postArgs.forEach((name, value) -> finalParams1.add(new BasicNameValuePair(name, (String) value)));
            params = finalParams1;
        }
        try {
            req.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException(e.getMessage(),uri,e.getCause());
        }
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(req) ) {
            try {
                return new ApacheHttpClientResponse(response.getEntity().getContent());
            } catch (IOException e) {
                throw new InputStreamException(e.getMessage(),uri,e.getCause());
            }
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApacheHttpClientResponse post(String uri, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri, postArgs,null, headers);
    }

    @Override
    public ApacheHttpClientResponse post(String uri, Map<String, Object> postArgs) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri,postArgs, (Map<String, Object>) null);
    }

    @Override
    public ApacheHttpClientResponse post(String uri, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri,null,null,headers);
    }

    @Override
    public ApacheHttpClientResponse post(String uri) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri, null, (Map<String, Object>) null);
    }
}
