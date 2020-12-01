package ru.redguy.rednetworker.clients.ftp;


public interface IFTPConnection {
    public IFTPSession login(String login, String password);

    public IFTPSession login(String login, String password, String account);

    public IFTPSession loginAnonymous(String email);

    public IFTPSession loginAnonymous();
}
