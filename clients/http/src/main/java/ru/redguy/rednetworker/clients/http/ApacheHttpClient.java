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
    public InputStream get(String uri, Map<String, Object> args, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
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
                return response.getEntity().getContent();
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
    public InputStream get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
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
                return response.getEntity().getContent();
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
    public InputStream get(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        return get(uri, null, headers);
    }

    @Override
    public InputStream get(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return get(url, (Map<String, Object>) null);
    }

    @Override
    public String getString(String url, Map<String,Object> args, ArrayList<Header> headers) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        InputStream stream = get(url, args, headers);
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public String getString(String url, Map<String, Object> args) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        InputStream stream = get(url, args);
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try(Reader in = new InputStreamReader(stream)) {
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    @Override
    public String getString(String url, ArrayList<Header> headers) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return getString(url, null, headers);
    }

    @Override
    public String getString(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return getString(url, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
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
                return response.getEntity().getContent();
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
    public InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
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
                return response.getEntity().getContent();
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
    public InputStream post(String uri, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri, postArgs,null, headers);
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri,postArgs, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri,null,null,headers);
    }

    @Override
    public InputStream post(String uri) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return post(uri, null, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        InputStream stream = post(url, postArgs, getArgs, headers);
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        InputStream stream = post(url, postArgs, getArgs);
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            try {
                while ((inputLine = in.readLine()) != null) { //TODO: java.net.SocketException: Socket is closed
                    content.append(inputLine);
                }
            } catch (SocketException ignored) {}
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public String postString(String url, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return postString(url, postArgs, null, headers);
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return postString(url, postArgs, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url, ArrayList<Header> headers) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return postString(url, null, null, headers);
    }

    @Override
    public String postString(String url) throws URLException, HttpProtocolException, InputStreamException, EncodingException {
        return postString(url, null, (Map<String, Object>) null);
    }

    @SuppressWarnings("ThrowFromFinallyBlock")
    @Override
    public File downloadFile(String uri, String pathToFile, Map<String, Object> getArgs) throws URLException, InputStreamException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods
                .build();
        try {
            HttpGet get = new HttpGet(url.toURI()); // we're using GET but it could be via POST as well
            return httpclient.execute(get, new FileDownloadResponseHandler(new File(pathToFile)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new InputStreamException(e.getMessage(),uri,e.getCause());
            }
        }
    }

    @Override
    public File downloadFile(String uri, String pathToFile) throws URLException, InputStreamException {
        return downloadFile(uri,pathToFile,null);
    }
}
