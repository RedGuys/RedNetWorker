package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IHttpResponse {
    public String getString() throws HttpProtocolException, InputStreamException, IOException;

    public InputStream getInputStream() throws HttpProtocolException, InputStreamException;

    public File saveToFile(String pathToFile) throws IOException;

    public File saveToFile(File file) throws IOException;
}
