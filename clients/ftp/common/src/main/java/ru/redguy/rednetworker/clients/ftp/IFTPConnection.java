package ru.redguy.rednetworker.clients.ftp;


import java.io.IOException;

public interface IFTPConnection {
    public IFTPSession login(String login, String password) throws IOException;

    public IFTPSession login(String login, String password, String account) throws IOException;

    public IFTPSession loginAnonymous(String email) throws IOException;

    public IFTPSession loginAnonymous() throws IOException;
}
