package ru.redguy.rednetworker.clients.smtp;

import ru.redguy.rednetworker.clients.smtp.exceptions.IncorrectAddressException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaxMessage implements ISmtpMessage {
    Message message;
    String from,to,subject,text = null;

    public JavaxMessage(Session session) {
        message = new MimeMessage(session);
    }

    @Override
    public ISmtpMessage from(String from) {
        this.from = from;
        return this;
    }

    @Override
    public ISmtpMessage to(String to) {
        this.to = to;
        return this;
    }

    @Override
    public ISmtpMessage subject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public ISmtpMessage text(String text) {
        this.text = text;
        return this;
    }

    @Override
    public void send() throws IncorrectAddressException, ru.redguy.rednetworker.clients.smtp.exceptions.MessagingException {
        try {
            message.setFrom(new InternetAddress(from));
        } catch (MessagingException e) {
            throw new IncorrectAddressException(e.getMessage(),e.getCause());
        }
        try {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        } catch (MessagingException e) {
            throw new IncorrectAddressException(e.getMessage(),e.getCause());
        }
        try {
            message.setSubject(subject);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.smtp.exceptions.MessagingException(e.getMessage(),e.getCause());
        }
        try {
            message.setText(text);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.smtp.exceptions.MessagingException(e.getMessage(),e.getCause());
        }
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.smtp.exceptions.MessagingException(e.getMessage(),e.getCause());
        }
    }
}
