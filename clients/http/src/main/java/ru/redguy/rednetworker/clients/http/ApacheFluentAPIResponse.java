package ru.redguy.rednetworker.clients.http;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Response;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ApacheFluentAPIResponse implements IHttpResponse {

    private final Response response;

    ApacheFluentAPIResponse(Response response) {
        this.response = response;
    }

    @Override
    public String getString() throws HttpProtocolException, InputStreamException {
        try {
            return response.returnContent().asString();
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),e.getCause());
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public InputStream getInputStream() throws HttpProtocolException, InputStreamException {
        try {
            return response.returnContent().asStream();
        } catch (ClientProtocolException e) {
            throw new HttpProtocolException(e.getMessage(),e.getCause());
        } catch (IOException e) {
            throw new InputStreamException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        response.saveContent(file);
        return file;
    }

    @Override
    public File saveToFile(File file) throws IOException {
        response.saveContent(file);
        return file;
    }
}
