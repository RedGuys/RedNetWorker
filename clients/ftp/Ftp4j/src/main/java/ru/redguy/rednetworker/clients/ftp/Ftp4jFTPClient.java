package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.enums.Ftp4jFTPFileType;
import ru.redguy.rednetworker.clients.ftp.enums.TransferMode;
import ru.redguy.rednetworker.utils.DataTime;
import it.sauronsoftware.ftp4j.*;
import ru.redguy.rednetworker.clients.ftp.exceptions.*;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
