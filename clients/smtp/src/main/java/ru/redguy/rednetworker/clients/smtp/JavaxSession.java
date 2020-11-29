package ru.redguy.rednetworker.clients.smtp;

import javax.mail.Session;

public class JavaxSession implements ISmtpSession {

    Session session;

    public JavaxSession(Session session) {
        this.session = session;
    }

    @Override
    public JavaxMessage createMessage() {
        return new JavaxMessage(session);
    }
}
