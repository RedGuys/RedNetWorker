package ru.redguy.rednetworker.clients.http;

import org.apache.http.entity.ContentType;
import ru.redguy.rednetworker.utils.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.NotImplementedException;
import ru.redguy.rednetworker.utils.Protocols;

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
    public ApacheFluentAPIResponse get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        try {
            return new ApacheFluentAPIResponse(Request.Get(url.toURI()).execute());
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public ApacheFluentAPIResponse get(String uri) throws URLException, OpenConnectionException {
        return get(uri, (Map<String, Object>) null);
    }

    @Override
    public ApacheFluentAPIResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException {
        URL url;
        try {
            url = new URL(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        List<NameValuePair> params = new ArrayList<>();
        if(postArgs != null) {
            List<NameValuePair> finalParams = params;
            postArgs.forEach((name, value) -> finalParams.add(new BasicNameValuePair(name, (String) value)));
            params = finalParams;
        }
        try {
            return new ApacheFluentAPIResponse(Request.Post(url.toURI()).bodyForm(params, Charset.defaultCharset()).execute()); //TODO: fix error with 301 HTTP code
        } catch (URISyntaxException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public ApacheFluentAPIResponse post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException {
        return post(uri, postArgs , (Map<String, Object>) null);
    }

    @Override
    public ApacheFluentAPIResponse post(String uri) throws URLException, OpenConnectionException {
        return post(uri, null, (Map<String, Object>) null);
    }
}
