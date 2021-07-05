package ru.redguy.rednetworker.clients.http;

import java.io.*;
import java.nio.charset.Charset;

public class JavaNetResponse implements IHttpResponse {

    private final InputStream inputStream;
    private final Charset responseCharset;

    JavaNetResponse(InputStream inputStream, Charset responseCharset) {
        this.inputStream = inputStream;
        this.responseCharset = responseCharset;
    }

    @Override
    public String getString() {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,responseCharset))) {
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
    public InputStream getInputStream() {
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
