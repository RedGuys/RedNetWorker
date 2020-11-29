package ru.redguy.rednetworker.clients.smtp;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class Javax implements ISmtpClient {

    String host = "";
    String login = "";
    String password = "";
    int port = 465;
    boolean useSSL = true;

    public Javax() {

    }

    @Override
    public Javax host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public Javax passwordAuth(String login, String password) {
        this.login = login;
        this.password = password;
        return this;
    }

    @Override
    public Javax port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public Javax useSSL(boolean useSSL) {
        this.useSSL = useSSL;
        return this;
    }

    @Override
    public JavaxSession connect() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", String.valueOf(port));
        if(useSSL) {
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        Session session = Session.getDefaultInstance(properties,
                //Аутентификатор - объект, который передает логин и пароль
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });
        return new JavaxSession(session);
    }
}
