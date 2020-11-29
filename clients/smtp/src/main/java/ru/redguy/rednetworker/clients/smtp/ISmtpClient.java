package ru.redguy.rednetworker.clients.smtp;

public interface ISmtpClient {
    ISmtpClient host(String host);

    ISmtpClient passwordAuth(String login, String password);

    ISmtpClient port(int port);

    ISmtpClient useSSL(boolean useSSL);

    ISmtpSession connect();
}
