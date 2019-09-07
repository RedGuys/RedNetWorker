package RedNetWorker.Clients;

import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Utils.Url;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

public class HttpClient {
    public int connectionTimeout = 5000;
    public int readTimeout = 5000;
    private HttpLibrary library;
    private Proxy proxy = null;

    public HttpClient(HttpLibrary library) {
        this.library = library;
        switch (library) {
            case JavaNet:

                break;
        }
    }
    public HttpClient(HttpLibrary library, Proxy proxy) {
        this.proxy = proxy;
        this.library = library;
        switch (library) {
            case JavaNet:

                break;
        }
    }

    //gets
    public InputStream get(String uri, Map<String,String> args) throws IOException {
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
        }
        return null;
    }

    public InputStream get(String url) throws IOException {
        return get(url,null);
    }

    public String getString(String url, Map<String,String> args) throws IOException {
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

    public String getString(String url) throws IOException {
        return getString(url,null);
    }

    //post's
    public InputStream post(String uri, Map<String,String> args) throws IOException {
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
        }
        return null;
    }

    public InputStream post(String uri) throws IOException {
        return post(uri,null);
    }

    public String postString(String url, Map<String,String> args) throws IOException {
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

    public String postString(String url) throws IOException {
        return getString(url,null);
    }
}
