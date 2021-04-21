package ru.redguy.rednetworker.clients.ftp;

import org.apache.commons.net.ftp.FTPClient;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.IOException;

public class ApacheFTPClient implements IFTPClient {
    private final org.apache.commons.net.ftp.FTPClient client;
    private String host = "localhost";
    private int port = 21;

    public ApacheFTPClient() {
        client = new FTPClient();
    }

    @Override
    public ApacheFTPClient host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public ApacheFTPClient port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public ApacheFTPConnection connect() throws OpenConnectionException, IOException {
        client.connect(host,port);
        return new ApacheFTPConnection(client);
    }
}
