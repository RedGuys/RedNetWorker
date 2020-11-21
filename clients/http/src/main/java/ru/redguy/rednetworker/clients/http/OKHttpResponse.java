package ru.redguy.rednetworker.clients.http;

import okhttp3.ResponseBody;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OKHttpResponse implements IHttpResponse {

    ResponseBody response;

    OKHttpResponse(ResponseBody response) {
        this.response = response;
    }

    @Override
    public String getString() throws HttpProtocolException, InputStreamException, IOException {
        return response.string();
    }

    @Override
    public InputStream getInputStream() throws HttpProtocolException, InputStreamException {
        return response.byteStream();
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        return saveToFile(new File(pathToFile));
    }

    @Override
    public File saveToFile(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while (response.byteStream().available() != 0) {
            fileOutputStream.write(response.byteStream().read());
        }
        fileOutputStream.close();
        return file;
    }
}
