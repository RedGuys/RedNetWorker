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
import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class OKHttp implements IHttpClient {

    @Override
    public InputStream get(String uri, Map<String, Object> args) throws OpenConnectionException, HttpProtocolException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(args)).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new HttpProtocolException("Server returned "+response.code()+" code!",uri);
            }
            return response.body().byteStream();
            
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public InputStream get(String uri) throws HttpProtocolException, OpenConnectionException {
        return get(uri, (Map<String, Object>) null);
    }

    @Override
    public String getString(String uri, Map<String, Object> args) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(args)).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new HttpProtocolException("Server returned "+response.code()+" code!",uri);
            }
            return response.body().string();

        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public String getString(String uri) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return getString(uri, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
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
            return response.body().byteStream();

        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException, EncodingException {
        return post(uri,postArgs, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return post(uri,null, (Map<String, Object>) null);
    }

    @Override
    public String postString(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
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
            return response.body().string();

        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public String postString(String uri, Map<String, Object> postArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return postString(uri,postArgs, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException, EncodingException {
        return postString(url,null, (Map<String, Object>) null);
    }

    @Override
    public File downloadFile(String uri, String pathToFile, Map<String, Object> getArgs) throws URLException, OpenConnectionException, FileNotFoundException, InputStreamException, OutputStreamException, HttpProtocolException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Protocols.formatUrlString(uri,"http") + HttpUtils.buildGet(getArgs)).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new HttpProtocolException("Server returned "+response.code()+" code!",uri);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
            while (response.body().byteStream().available() != 0) {
                fileOutputStream.write(response.body().byteStream().read());
            }
            return new File(pathToFile);
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public File downloadFile(String uri, String pathToFile) throws FileNotFoundException, URLException, OutputStreamException, OpenConnectionException, InputStreamException, HttpProtocolException {
        return downloadFile(uri,pathToFile,null);
    }
}
