package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.Utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ApacheFluentAPI implements IHttpClient {
    @Override
    public InputStream get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException {
        URL url;
        try {
            url = new URL(uri+ HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        try {
            return Request.Get(url.toURI()).execute().returnContent().asStream();
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public InputStream get(String uri) throws URLException, OpenConnectionException {
        return get(uri, (Map<String, Object>) null);
    }

    @Override
    public String getString(String uri, Map<String, Object> args) throws URLException, OpenConnectionException {
        URL url;
        try {
            url = new URL(uri+ HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        try {
            return Request.Get(url.toURI()).execute().returnContent().asString();
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public String getString(String url) throws URLException, OpenConnectionException {
        return getString(url, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException {
        URL url;
        try {
            url = new URL(uri+HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        List<NameValuePair> params = new ArrayList<>();
        if(postArgs != null) {
            List<NameValuePair> finalParams = params;
            postArgs.forEach((name, value) ->{
                finalParams.add(new BasicNameValuePair(name, (String) value));
            });
            params = finalParams;
        }
        try {
            return Request.Post(url.toURI()).bodyForm(params, Charset.defaultCharset()).execute().returnContent().asStream(); //TODO: fix error with 301 HTTP code
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException {
        return post(uri, postArgs , (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri) throws URLException, OpenConnectionException {
        return post(uri, null, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException {
        InputStream stream = post(url, postArgs, getArgs);
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs) throws URLException, OpenConnectionException {
        return postString(url, postArgs, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url) throws URLException, OpenConnectionException {
        return postString(url, null, (Map<String, Object>) null);
    }

    @Override
    public File downloadFile(String uri, String pathToFile, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException {
        URL url;
        try {
            url = new URL(uri + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        File file = new File(pathToFile);
        try {
            Request.Get(url.toURI()).execute().saveContent(file);
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
        return file;
    }

    @Override
    public File downloadFile(String uri, String pathToFile) throws URLException, OpenConnectionException, HttpProtocolException {
        return downloadFile(uri, pathToFile, null);
    }
}
