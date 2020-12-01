package ru.redguy.rednetworker.clients.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class ApacheFTPConnection implements IFTPConnection {

    FTPClient client;

    public ApacheFTPConnection(FTPClient client) {
        this.client = client;
    }

    @Override
    public ApacheFTPSession login(String login, String password) {
        try {
            client.login(login, password);
        } catch (IOException e) {
            return null;
        }
        return new ApacheFTPSession(client);
    }

    @Override
    public ApacheFTPSession login(String login, String password, String account) {
        try {
            client.login(login, password, account);
        } catch (IOException e) {
            return null;
        }
        return new ApacheFTPSession(client);
    }

    @Override
    public IFTPSession loginAnonymous(String email) {
        try {
            client.login("anonymous",email);
        } catch (IOException e) {
            return null;
        }
        return new ApacheFTPSession(client);
    }

    @Override
    public IFTPSession loginAnonymous() {
        try {
            client.login("anonymous","anonymous@anonymous.com");
        } catch (IOException e) {
            return null;
        }
        return new ApacheFTPSession(client);
    }
}
