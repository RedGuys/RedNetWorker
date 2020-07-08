package ru.redguy.rednetworker.clients.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.*;

public class FileDownloadResponseHandler implements ResponseHandler<File> {
    private final File target;

    public FileDownloadResponseHandler(File target) {
        this.target = target;
    }

    public File handleResponse(HttpResponse response) throws IOException {
        BufferedInputStream source = new BufferedInputStream(response.getEntity().getContent());
        FileOutputStream fis = new FileOutputStream(this.target);
        byte[] buffer = new byte[1024];
        int count;
        while((count = source.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        source.close();
        return this.target;
    }
}