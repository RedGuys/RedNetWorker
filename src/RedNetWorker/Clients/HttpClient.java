package RedNetWorker.Clients;

import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Clients.HttpClientAdditions.FileDownloadResponseHandler;
import RedNetWorker.Utils.Url;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClient {
    public int connectionTimeout = 5000;
    public int readTimeout = 5000;
    private HttpLibrary library;
    private Proxy proxy = null;
    public ArrayList<Header> headers = new ArrayList<Header>();

    public HttpClient(HttpLibrary library) {
        this.library = library;
    }
    public HttpClient(HttpLibrary library, Proxy proxy) {
        this.proxy = proxy;
        this.library = library;
    }

    //gets
    public InputStream get(String uri, Map<String,String> args) throws HttpExceptions.URLException, HttpExceptions.OpenConnectionException, HttpExceptions.InputStreamException, HttpExceptions.HttpProtocolException {
        if(args != null) {
            StringBuilder stringBuilder = new StringBuilder(uri);
            stringBuilder.append("?");
            stringBuilder.append(Url.getParamsString(args));
            uri = stringBuilder.toString();
        }
        URL url;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
        }
        switch (library) {
            case JavaNet:
                HttpURLConnection connection;
                if(proxy != null) {
                    try {
                        connection = (HttpURLConnection) url.openConnection(this.proxy);
                    } catch (IOException e) {
                        throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                    }
                } else {
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                    }
                }
                try {
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    throw new HttpExceptions.HttpProtocolException(e.getMessage(),uri,e.getCause());
                }
                connection.setConnectTimeout(this.connectionTimeout);
                connection.setReadTimeout(this.readTimeout);
                try {
                    return connection.getInputStream();
                } catch (IOException e) {
                    throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                }
            case apacheHttpClient:
                HttpGet req;
                try {
                    req = new HttpGet(url.toURI());
                } catch (URISyntaxException e) {
                    throw new HttpExceptions.URLException(e.getMessage(),url.toString(),e.getCause());
                }
                req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
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
                        throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                    }
                } catch (ClientProtocolException e) {
                    throw new HttpExceptions.HttpProtocolException(e.getMessage(),uri,e.getCause());
                } catch (IOException e) {
                    throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                }
            case apacheFluentAPI:
                try {
                    return Request.Get(url.toURI()).execute().returnContent().asStream();
                } catch (URISyntaxException e) {
                    throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
                } catch (IOException e) {
                    throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                }
        }
        return null;
    }

    public InputStream get(String url) throws HttpExceptions.URLException, HttpExceptions.HttpProtocolException, HttpExceptions.OpenConnectionException, HttpExceptions.InputStreamException {
        return get(url,null);
    }

    public String getString(String url, Map<String,String> args) throws HttpExceptions.URLException, HttpExceptions.HttpProtocolException, HttpExceptions.OpenConnectionException, HttpExceptions.InputStreamException {
        InputStream stream = get(url, args);
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

    public String getString(String url) throws HttpExceptions.URLException, HttpExceptions.HttpProtocolException, HttpExceptions.OpenConnectionException, HttpExceptions.InputStreamException {
        return getString(url,null);
    }

    //post's
    public InputStream post(String uri, Map<String,String> args) throws HttpExceptions.URLException, HttpExceptions.OpenConnectionException, HttpExceptions.HttpProtocolException, HttpExceptions.OutputStreamException, HttpExceptions.InputStreamException, HttpExceptions.EncodingException {
        URL url;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
        }
        switch (library) {
            case JavaNet:
                HttpURLConnection connection;
                if(proxy != null) {
                    try {
                        connection = (HttpURLConnection) url.openConnection(this.proxy);
                    } catch (IOException e) {
                        throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                    }
                } else {
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                    }
                }
                try {
                    connection.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    throw new HttpExceptions.HttpProtocolException(e.getMessage(),uri,e.getCause());
                }
                connection.setConnectTimeout(this.connectionTimeout);
                connection.setReadTimeout(this.readTimeout);
                try {
                    if (args != null) {
                        connection.setDoOutput(true);
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(Url.getParamsString(args));
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    throw new HttpExceptions.OutputStreamException(e.getMessage(),uri,e.getCause());
                }
                try {
                    return connection.getInputStream();
                } catch (IOException e) {
                    throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                }
            case apacheHttpClient:
                HttpPost req;
                try {
                    req = new HttpPost(url.toURI());
                } catch (URISyntaxException e) {
                    throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
                }
                if(headers.size() > 0) {
                    headers.forEach((value) -> {
                        req.setHeader(value);
                    });
                }
                List<NameValuePair> params = new ArrayList<>();
                if(args != null) {
                    List<NameValuePair> finalParams1 = params;
                    args.forEach((name, value) ->{
                        finalParams1.add(new BasicNameValuePair(name,value));
                    });
                    params = finalParams1;
                }
                try {
                    req.setEntity(new UrlEncodedFormEntity(params));
                } catch (UnsupportedEncodingException e) {
                    throw new HttpExceptions.EncodingException(e.getMessage(),uri,e.getCause());
                }
                try (CloseableHttpClient client = HttpClients.createDefault();
                     CloseableHttpResponse response = client.execute(req) ) {
                    try {
                        return response.getEntity().getContent();
                    } catch (IOException e) {
                        throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                    }
                } catch (ClientProtocolException e) {
                    throw new HttpExceptions.HttpProtocolException(e.getMessage(),uri,e.getCause());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case apacheFluentAPI:
                params = new ArrayList<>();
                if(args != null) {
                    List<NameValuePair> finalParams = params;
                    args.forEach((name, value) ->{
                        finalParams.add(new BasicNameValuePair(name,value));
                    });
                    params = finalParams;
                }
                try {
                    return Request.Post(url.toURI()).bodyForm(params, Charset.defaultCharset()).execute().returnContent().asStream();
                } catch (URISyntaxException e) {
                    throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
                } catch (IOException e) {
                    throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                }
        }
        return null;
    }

    public InputStream post(String uri) throws HttpExceptions.EncodingException, HttpExceptions.InputStreamException, HttpExceptions.HttpProtocolException, HttpExceptions.OutputStreamException, HttpExceptions.OpenConnectionException, HttpExceptions.URLException {
        return post(uri,null);
    }

    public String postString(String url, Map<String,String> args) throws HttpExceptions.EncodingException, HttpExceptions.InputStreamException, HttpExceptions.HttpProtocolException, HttpExceptions.OutputStreamException, HttpExceptions.OpenConnectionException, HttpExceptions.URLException {
        InputStream stream = post(url, args);
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

    public String postString(String url) throws HttpExceptions.URLException, HttpExceptions.HttpProtocolException, HttpExceptions.OpenConnectionException, HttpExceptions.InputStreamException {
        return getString(url,null);
    }

    public File DownloadFile(String uri, String pathToFile, Map<String,String> args) throws HttpExceptions.URLException, HttpExceptions.OpenConnectionException, IOException, HttpExceptions.InputStreamException, HttpExceptions.OutputStreamException, HttpExceptions.HttpProtocolException {
        URL url;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
        }
        switch (library) {
            case JavaNet:
                BufferedInputStream bis;
                try {
                    bis = new BufferedInputStream(url.openStream());
                } catch (IOException e) {
                    throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                }
                File file = new File(pathToFile);
                FileOutputStream fis = null;
                try {
                    fis = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    file.getParentFile().mkdirs();
                    fis = new FileOutputStream(file);
                }
                byte[] buffer = new byte[1024];
                int count=0;
                try {
                    while ((count = bis.read(buffer, 0, 1024)) != -1) {
                        fis.write(buffer, 0, count);
                    }
                } catch (IOException e) {
                    throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new HttpExceptions.OutputStreamException(e.getMessage(),uri,e.getCause());
                }
                return file;
            case apacheHttpClient:
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
                        throw new HttpExceptions.InputStreamException(e.getMessage(),uri,e.getCause());
                    }
                }
            case apacheFluentAPI:
                file = new File(pathToFile);
                try {
                    Request.Get(url.toURI()).execute().saveContent(file);
                } catch (URISyntaxException e) {
                    throw new HttpExceptions.URLException(e.getMessage(),uri,e.getCause());
                } catch (ClientProtocolException e) {
                    throw new HttpExceptions.HttpProtocolException(e.getMessage(),uri,e.getCause());
                } catch (IOException e) {
                    throw new HttpExceptions.OpenConnectionException(e.getMessage(),uri,e.getCause());
                }
                return file;
        }
        return null;
    }

    public File DownloadFile(String uri, String pathToFile) throws IOException, HttpExceptions.URLException, HttpExceptions.OutputStreamException, HttpExceptions.OpenConnectionException, HttpExceptions.InputStreamException, HttpExceptions.HttpProtocolException {
        return DownloadFile(uri,pathToFile,null);
    }
}
