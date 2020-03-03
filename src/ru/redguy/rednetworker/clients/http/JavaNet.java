package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.Utils.HttpUtils;
import ru.redguy.rednetworker.clients.http.exceptions.*;

import java.io.*;
import java.net.*;
import java.util.Map;

public class JavaNet implements IHttpClient {
    private Proxy proxy = null;
    public final int connectionTimeout = 5000;
    public final int readTimeout = 5000;

    public JavaNet() {

    }

    public JavaNet(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public InputStream get(String uri, Map<String, Object> args) throws URLException, OpenConnectionException, HttpProtocolException, InputStreamException {
        URL url;
        try {
            url = new URL(uri+ HttpUtils.buildGet(args));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpURLConnection connection;
        if(proxy != null) {
            try {
                connection = (HttpURLConnection) url.openConnection(this.proxy);
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        } else {
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        }
        connection.setConnectTimeout(this.connectionTimeout);
        connection.setReadTimeout(this.readTimeout);
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public InputStream get(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return get(url, (Map<String, Object>) null);
    }

    @Override
    public String getString(String url, Map<String, Object> args) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
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

    @Override
    public String getString(String url) throws URLException, HttpProtocolException, OpenConnectionException, InputStreamException {
        return getString(url, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException {
        URL url;
        try {
            url = new URL(uri+HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        HttpURLConnection connection;
        if(proxy != null) {
            try {
                connection = (HttpURLConnection) url.openConnection(this.proxy);
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        } else {
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
            }
        }
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),uri,e.getCause());
        }
        connection.setConnectTimeout(this.connectionTimeout);
        connection.setReadTimeout(this.readTimeout);
        try {
            if (postArgs != null) {
                connection.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(HttpUtils.buildPost(postArgs));
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            throw new OutputStreamException(e.getMessage(),uri,e.getCause());
        }
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),uri,e.getCause());
        }
    }

    @Override
    public InputStream post(String uri, Map<String, Object> postArgs) throws URLException, OpenConnectionException, HttpProtocolException, OutputStreamException, InputStreamException {
        return post(uri, postArgs, (Map<String, Object>) null);
    }

    @Override
    public InputStream post(String uri) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException {
        return post(uri, null, (Map<String, Object>) null);
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs, Map<String, Object> getArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException {
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
            return "";
        }
    }

    @Override
    public String postString(String url, Map<String, Object> postArgs) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException {
        return postString(url, postArgs, (Map<String, Object>) null);
    }


    @Override
    public String postString(String url) throws URLException, HttpProtocolException, OutputStreamException, OpenConnectionException, InputStreamException {
        return postString(url, null, (Map<String, Object>) null);
    }

    @Override
    public File downloadFile(String uri, String pathToFile, Map<String, Object> getArgs) throws URLException, OpenConnectionException, FileNotFoundException, InputStreamException, OutputStreamException {
        URL url;
        try {
            url = new URL(uri+HttpUtils.buildGet(getArgs));
        } catch (MalformedURLException e) {
            throw new URLException(e.getMessage(),uri,e.getCause());
        }
        BufferedInputStream bis;
        try {
            bis = new BufferedInputStream(url.openStream());
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(),uri,e.getCause());
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
            throw new InputStreamException(e.getMessage(),uri,e.getCause());
        }
        try {
            fis.close();
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),uri,e.getCause());
        }
        try {
            bis.close();
        } catch (IOException e) {
            throw new OutputStreamException(e.getMessage(),uri,e.getCause());
        }
        return file;
    }

    @Override
    public File downloadFile(String uri, String pathToFile) throws FileNotFoundException, URLException, OutputStreamException, OpenConnectionException, InputStreamException {
        return downloadFile(uri, pathToFile, null);
    }
}
