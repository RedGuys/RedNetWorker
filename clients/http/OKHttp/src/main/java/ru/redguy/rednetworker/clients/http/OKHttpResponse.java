package ru.redguy.rednetworker.clients.http;

import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OKHttpResponse implements IHttpResponse {

    final Response response;

    OKHttpResponse(Response response) {
        this.response = response;
    }

    @Override
    public String getString() {
        try {
            return response.body().string();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public InputStream getInputStream() {
        return response.body().byteStream();
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        return saveToFile(new File(pathToFile));
    }

    @Override
    public File saveToFile(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while (response.body().byteStream().available() != 0) {
            fileOutputStream.write(response.body().byteStream().read());
        }
        fileOutputStream.close();
        return file;
    }
}
