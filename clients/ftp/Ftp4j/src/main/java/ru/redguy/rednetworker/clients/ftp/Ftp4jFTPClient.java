package ru.redguy.rednetworker.clients.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import ru.redguy.rednetworker.clients.ftp.exceptions.UnknownServerErrorException;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.IOException;

public class Ftp4jFTPClient implements IFTPClient {
    private String host = "localhost";
    private int port = 21;

    public Ftp4jFTPClient() {
    }

    @Override
    public Ftp4jFTPClient host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public Ftp4jFTPClient port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public Ftp4jFTPConnection connect() throws OpenConnectionException, IOException, UnknownServerErrorException {
        try {
            FTPClient client = new FTPClient();
            client.connect(host,port);
            return new Ftp4jFTPConnection(client);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }
}
