package ru.redguy.rednetworker.clients.http;

import com.google.gson.Gson;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.http.Header;
import ru.redguy.rednetworker.clients.http.exceptions.*;
import ru.redguy.rednetworker.utils.HttpUtils;
import ru.redguy.rednetworker.utils.Protocols;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class OKHttp implements IHttpClient {

    @Override
    public OKHttpResponse get(String uri, Map<String, Object> args) throws OpenConnectionException, HttpProtocolException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(args)).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new HttpProtocolException("Server returned "+response.code()+" code!",uri);
            }
            return new OKHttpResponse(response.body());
            
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public OKHttpResponse get(String uri) throws HttpProtocolException, OpenConnectionException {
        return get(uri, (Map<String, Object>) null);
    }

    @Override
    public OKHttpResponse post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        MediaType json = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(json, gson.toJson(postArgs));
        Request request = new Request.Builder().url(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs)).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new HttpProtocolException("Server returned "+response.code()+" code!",uri);
            }
            return new OKHttpResponse(response.body());

        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public OKHttpResponse post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        return post(uri,postArgs, (Map<String, Object>) null);
    }

    @Override
    public OKHttpResponse post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return post(uri,null, (Map<String, Object>) null);
    }
}
