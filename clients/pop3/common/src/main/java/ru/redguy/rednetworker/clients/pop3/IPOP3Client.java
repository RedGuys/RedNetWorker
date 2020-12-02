package ru.redguy.rednetworker.clients.pop3;

import ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException;
import ru.redguy.rednetworker.clients.pop3.exeptions.NoSuchProviderException;

public interface IPOP3Client {

    default void connect(String host) {
        connect(host,995);
    }

    void connect(String host, int port);

    void login(String login, String password);

    IPOP3Session getSession() throws NoSuchProviderException, MessagingException;
}
