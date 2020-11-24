package ru.redguy.rednetworker.clients.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.*;

public class ApacheHttpClientResponse implements IHttpResponse {

    CloseableHttpResponse response;

    ApacheHttpClientResponse(CloseableHttpResponse response) {
        this.response = response;
    }

    @Override
    public String getString() throws HttpProtocolException, InputStreamException {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
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
    public InputStream getInputStream() throws HttpProtocolException, InputStreamException, IOException {
        return response.getEntity().getContent();
    }

    @Override
    public File saveToFile(String pathToFile) throws IOException {
        return saveToFile(new File(pathToFile));
    }

    @Override
    public File saveToFile(File file) throws IOException {
        FileOutputStream fis;
        try {
            fis = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            file.getParentFile().mkdirs();
            fis = new FileOutputStream(file);
        }
        response.getEntity().writeTo(fis);
        fis.close();
        return file;
    }
}
