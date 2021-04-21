package ru.redguy.rednetworker.clients.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IHttpResponse {
    String getString();

    InputStream getInputStream() throws IOException;

    File saveToFile(String pathToFile) throws IOException;

    File saveToFile(File file) throws IOException;
}
