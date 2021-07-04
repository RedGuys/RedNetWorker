package ru.redguy.rednetworker.clients.http;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.*;
import java.nio.charset.Charset;

public class ApacheHttpClientResponse implements IHttpResponse {

    final CloseableHttpResponse response;
    private Charset responseCharset;

    ApacheHttpClientResponse(CloseableHttpResponse response, Charset responseCharset) {
        this.response = response;
        this.responseCharset = responseCharset;
    }

    @Override
    public String getString() {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),responseCharset))) {
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
    public InputStream getInputStream() throws IOException {
        return response.getEntity().getContent();
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        return saveToFile(new File(pathToFile));
    }

    @Override
    public File saveToFile(File file) throws IOException {
        FileOutputStream fis;
        try {
            fis = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            file.getParentFile().mkdirs();
            fis = new FileOutputStream(file);
        }
        response.getEntity().writeTo(fis);
        fis.close();
        return file;
    }
}
