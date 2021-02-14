package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IHttpResponse {
    String getString();

    InputStream getInputStream() throws IOException;

    File saveToFile(String pathToFile) throws IOException;

    File saveToFile(File file) throws IOException;
}
