package ru.redguy.rednetworker.clients.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import ru.redguy.rednetworker.utils.EmptyInputStream;

import java.io.*;
import java.nio.charset.Charset;

public class ApacheFluentAPIResponse implements IHttpResponse {

    private final HttpResponse response;
    private Content content;

    ApacheFluentAPIResponse(HttpResponse httpResponse, Charset charset) {
        this.response = httpResponse;
        try {
            if(httpResponse.getEntity() != null) {
                this.content = new Content(
                        EntityUtils.toByteArray(httpResponse.getEntity()),
                        ContentType.parse(
                                httpResponse.getEntity().getContentType().getElements()[0].getName()
                        ).withCharset(charset)
                );
            } else {
                content = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString() {
        if(content == null) return ""; else return content.asString();
    }

    @Override
    public InputStream getInputStream() {
        if(content == null) return new EmptyInputStream(); else return content.asStream();
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        if(content != null) {
            try (OutputStream outputStream = new FileOutputStream(file)) {
                response.getEntity().writeTo(outputStream);
                outputStream.flush();
            }
        }
        return file;
    }

    @Override
    public File saveToFile(File file) throws IOException {
        if(content != null) {
            try (OutputStream outputStream = new FileOutputStream(file)) {
                response.getEntity().writeTo(outputStream);
                outputStream.flush();
            }
        }
        return file;
    }
}
