package ru.redguy.rednetworker.clients.http;

import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.*;
import java.nio.charset.Charset;

public class ApacheFluentAPIResponse implements IHttpResponse {

    private final HttpResponse response;
    private Content content;

    ApacheFluentAPIResponse(HttpResponse httpResponse, Charset charset) {
        this.response = httpResponse;
        try {
            this.content = new Content(
                    EntityUtils.toByteArray(httpResponse.getEntity()),
                    ContentType.parse(
                            httpResponse.getEntity().getContentType().getElements()[0].getName()
                    ).withCharset(charset)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString() throws HttpProtocolException, InputStreamException {
        return content.asString();
    }

    @Override
    public InputStream getInputStream() throws HttpProtocolException, InputStreamException {
        return content.asStream();
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            response.getEntity().writeTo(outputStream);
            outputStream.flush();
        }
        return file;
    }

    @Override
    public File saveToFile(File file) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            response.getEntity().writeTo(outputStream);
            outputStream.flush();
        }
        return file;
    }
}
