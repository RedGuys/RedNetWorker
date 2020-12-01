package ru.redguy.rednetworker.clients.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;

public class Ftp4jFTPConnection implements IFTPConnection {

    private FTPClient client;

    public Ftp4jFTPConnection(FTPClient client) {
        this.client = client;
    }

    @Override
    public Ftp4jFTPSession login(String login, String password) throws IOException {
        try {
            client.login(login, password);
        } catch (FTPIllegalReplyException | FTPException e) {
            return null;
        }
        return new Ftp4jFTPSession(client);
    }

    @Override
    public Ftp4jFTPSession login(String login, String password, String account) throws IOException {
        try {
            client.login(login, password, account);
        } catch (FTPIllegalReplyException | FTPException e) {
            return null;
        }
        return new Ftp4jFTPSession(client);
    }

    @Override
    public Ftp4jFTPSession loginAnonymous(String email) throws IOException {
        try {
            client.login("anonymous", email);
        } catch (FTPIllegalReplyException | FTPException e) {
            return null;
        }
        return new Ftp4jFTPSession(client);
    }

    @Override
    public Ftp4jFTPSession loginAnonymous() throws IOException {
        try {
            client.login("anonymous", "anonymous@anonymous.com");
        } catch (FTPIllegalReplyException | FTPException e) {
            return null;
        }
        return new Ftp4jFTPSession(client);
    }
}
