package ru.redguy.rednetworker.clients.pop3;

import javax.mail.Multipart;

public class JavaXMultipart implements IPOP3Multipart {

    Multipart multipart;

    public JavaXMultipart(Multipart multipart) {
        this.multipart = multipart;
    }

    @Override
    public Object getOriginal() {
        return multipart;
    }
}
