package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.Utils.HttpUtils;
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

import java.io.*;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApacheHttpClient implements IHttpClient {
    private Proxy proxy = null;
    public int connectionTimeout = 5000;
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
            url = new URL(uri+ HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpGet req;
        try {
            req = new HttpGet(url.toURI());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),url.toString(),e.getCause());
        }
        if(proxy != null) {
            req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
        } else {
            req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
        }
        if(headers.size() > 0) {
            headers.forEach((value) -> {
                req.setHeader(value);
            });
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
            url = new URL(uri+ HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpGet req;
        try {
            req = new HttpGet(url.toURI());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),url.toString(),e.getCause());
        }
        if(proxy != null) {
            req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
        } else {
            req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
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
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //TODO: java.net.SocketException: Socket is closed
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
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
    public InputStream post(String uri, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        URL url;
        try {
            url = new URL(uri+HttpUtils.buildGet(getArgs));
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
            headers.forEach((value) -> {
                req.setHeader(value);
            });
        }
        List<NameValuePair> params = new ArrayList<>();
        if(postArgs != null) {
            List<NameValuePair> finalParams1 = params;
            postArgs.forEach((name, value) ->{
                finalParams1.add(new BasicNameValuePair(name, (String) value));
            });
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
    public InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        URL url;
        try {
            url = new URL(uri+HttpUtils.buildGet(getArgs));
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
            postArgs.forEach((name, value) ->{
                finalParams1.add(new BasicNameValuePair(name, (String) value));
            });
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
    public InputStream post(String uri, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        return post(uri, postArgs,null, headers);
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        return post(uri,postArgs, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri, ArrayList<Header> headers) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        return post(uri,null,null,headers);
    }

    @Override
    public InputStream post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return post(uri, null, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url, Map<String,Object> postArgs, Map<String,Object> getArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
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
    public String postString(String url, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        InputStream stream = post(url, postArgs, getArgs);
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //TODO: java.net.SocketException: Socket is closed
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public String postString(String url, Map<String,Object> postArgs, ArrayList<Header> headers) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return postString(url, postArgs, null, headers);
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return postString(url, postArgs, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url, ArrayList<Header> headers) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return postString(url, null, null, headers);
    }

    @Override
    public String postString(String url) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return postString(url, null, (Map<String, Object>) null);
    }

    @Override
    public File downloadFile(String uri, String pathToFile, Map<String, Object> getArgs) throws URLException, OpenConnectionException, FileNotFoundException, InputStreamException, OutputStreamException {
        URL url;
        try {
            url = new URL(uri + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods
                .build();
        try {
            HttpGet get = new HttpGet(url.toURI()); // we're using GET but it could be via POST as well
            File downloaded = httpclient.execute(get, new FileDownloadResponseHandler(new File(pathToFile)));
            return downloaded;
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
    public File downloadFile(String uri, String pathToFile) throws FileNotFoundException, URLException, OutputStreamException, OpenConnectionException, InputStreamException {
        return downloadFile(uri,pathToFile,null);
    }
}
