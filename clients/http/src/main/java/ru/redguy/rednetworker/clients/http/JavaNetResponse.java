package ru.redguy.rednetworker.clients.http;

import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.*;

public class JavaNetResponse implements IHttpResponse {

    private InputStream inputStream;

    JavaNetResponse(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String getString() throws HttpProtocolException, InputStreamException {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
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
    public InputStream getInputStream() throws HttpProtocolException, InputStreamException {
        return inputStream;
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        return saveToFile(new File(pathToFile));
    }

    @Override
    public File saveToFile(File file) throws IOException {
        BufferedInputStream bis;
        bis = new BufferedInputStream(inputStream);
        FileOutputStream fis;
        try {
            fis = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            file.getParentFile().mkdirs();
            fis = new FileOutputStream(file);
        }
        byte[] buffer = new byte[1024];
        int count;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
        return file;
    }
}
