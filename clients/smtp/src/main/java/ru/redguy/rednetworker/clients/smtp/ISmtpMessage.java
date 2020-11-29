package ru.redguy.rednetworker.clients.smtp;

import ru.redguy.rednetworker.clients.smtp.exceptions.IncorrectAddressException;
import ru.redguy.rednetworker.clients.smtp.exceptions.MessagingException;

public interface ISmtpMessage {
    ISmtpMessage from(String from);

    ISmtpMessage to(String to);

    ISmtpMessage subject(String subject);

    ISmtpMessage text(String text);

    void send() throws IncorrectAddressException, MessagingException;
}
