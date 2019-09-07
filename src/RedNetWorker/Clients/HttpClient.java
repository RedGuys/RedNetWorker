package RedNetWorker.Clients;

import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Clients.HttpClientAdditions.FileDownloadResponseHandler;
import RedNetWorker.Utils.Url;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
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
    public InputStream get(String uri, Map<String,String> args) throws IOException, URISyntaxException {
        if(args != null) {
            StringBuilder stringBuilder = new StringBuilder(uri);
            stringBuilder.append("?");
            stringBuilder.append(Url.getParamsString(args));
            uri = stringBuilder.toString();
        }
        URL url = new URL(uri);
        switch (library) {
            case JavaNet:
                HttpURLConnection connection;
                if(proxy != null) {
                    connection = (HttpURLConnection) url.openConnection(this.proxy);
                } else {
                    connection = (HttpURLConnection) url.openConnection();
                }
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(this.connectionTimeout);
                connection.setReadTimeout(this.readTimeout);
                return connection.getInputStream();
            case apacheHttpClient:
                HttpGet req = new HttpGet(url.toURI());
                req.setConfig(RequestConfig.custom().setConnectTimeout(this.connectionTimeout).setSocketTimeout(this.connectionTimeout).setConnectionRequestTimeout(this.connectionTimeout).build());
                if(headers.size() > 0) {
                    headers.forEach((value) -> {
                        req.setHeader(value);
                    });
                }
                try (CloseableHttpClient client = HttpClients.createDefault();
                     CloseableHttpResponse response = client.execute(req) ) {
                    return response.getEntity().getContent();
                }
            case apacheFluentAPI:
                return Request.Get(url.toURI()).execute().returnContent().asStream();
        }
        return null;
    }

    public InputStream get(String url) throws URISyntaxException, IOException {
        return get(url,null);
    }

    public String getString(String url, Map<String,String> args) throws IOException, URISyntaxException {
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

    public String getString(String url) throws IOException, URISyntaxException {
        return getString(url,null);
    }

    //post's
    public InputStream post(String uri, Map<String,String> args) throws IOException, URISyntaxException {
        URL url = new URL(uri);
        switch (library) {
            case JavaNet:
                HttpURLConnection connection;
                if(proxy != null) {
                    connection = (HttpURLConnection) url.openConnection(this.proxy);
                } else {
                    connection = (HttpURLConnection) url.openConnection();
                }
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(this.connectionTimeout);
                connection.setReadTimeout(this.readTimeout);
                if(args != null) {
                    connection.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(Url.getParamsString(args));
                    out.flush();
                    out.close();
                }
                return connection.getInputStream();
            case apacheHttpClient: //TODO: Fix Socket is closed
                HttpPost req = new HttpPost(url.toURI());
                if(headers.size() > 0) {
                    headers.forEach((value) -> {
                        req.setHeader(value);
                    });
                }
                List<NameValuePair> params = new ArrayList<>();
                if(args != null) {
                    args.forEach((name,value) ->{
                        params.add(new BasicNameValuePair(name,value));
                    });
                }
                req.setEntity(new UrlEncodedFormEntity(params));
                try (CloseableHttpClient client = HttpClients.createDefault();
                     CloseableHttpResponse response = client.execute(req) ) {
                    return response.getEntity().getContent();
                }
            case apacheFluentAPI:
                params = new ArrayList<>();
                if(args != null) {
                    args.forEach((name,value) ->{
                        params.add(new BasicNameValuePair(name,value));
                    });
                }
                return Request.Post(url.toURI()).bodyForm(params, Charset.defaultCharset()).execute().returnContent().asStream();
        }
        return null;
    }

    public InputStream post(String uri) throws IOException, URISyntaxException {
        return post(uri,null);
    }

    public String postString(String url, Map<String,String> args) throws IOException, URISyntaxException {
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

    public String postString(String url) throws IOException, URISyntaxException {
        return getString(url,null);
    }

    public File DownloadFile(String uri, String pathToFile, Map<String,String> args) throws IOException {
        URL url = new URL(uri);
        switch (library) {
            case JavaNet:
                BufferedInputStream bis = new BufferedInputStream(url.openStream());
                File file = new File(pathToFile);
                FileOutputStream fis = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int count=0;
                while((count = bis.read(buffer,0,1024)) != -1)
                {
                    fis.write(buffer, 0, count);
                }
                fis.close();
                bis.close();
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
                    httpclient.close();
                }
        }
        return null;
    }

    public File DownloadFile(String uri, String pathToFile) throws IOException {
        return DownloadFile(uri,pathToFile,null);
    }
}
