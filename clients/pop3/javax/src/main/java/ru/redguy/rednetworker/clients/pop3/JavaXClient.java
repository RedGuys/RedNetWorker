package ru.redguy.rednetworker.clients.pop3;

import javax.mail.*;
import java.util.Properties;

public class JavaXClient implements IPOP3Client {

    Properties props;
    Authenticator auth;
    String host;
    int port;
    String login;
    String password;

    public JavaXClient() {

    }

    @Override
    public void connect(String host, int port) {
        props = new Properties();
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.fallback", "false");
        props.put("mail.pop3.socketFactory.port", String.valueOf(port));
        props.put("mail.pop3.port", String.valueOf(port));
        props.put("mail.pop3.host", host);
        props.put("mail.store.protocol", "pop3");
        this.host = host;
        this.port = port;
    }

    @Override
    public void login(String login, String password) {
        props.put("mail.pop3.user", login);
        auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        };
        this.login = login;
        this.password = password;
    }

    @Override
    public IPOP3Session getSession() throws ru.redguy.rednetworker.clients.pop3.exeptions.NoSuchProviderException, ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        Session session = Session.getDefaultInstance(props, auth);

        Store store = null;
        try {
            store = session.getStore("pop3");
        } catch (NoSuchProviderException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.NoSuchProviderException(e.getMessage(),host,port,e);
        }
        try {
            store.connect(host, login, password);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),host,port,e);
        }
        return new JavaXSession(store);
    }
}
