package ru.redguy.rednetworker.clients.pop3;

import javax.mail.Flags;

public class JavaXFlags implements IPOP3Flags {

    Flags flags;

    public JavaXFlags(Flags flags) {
        this.flags = flags;
    }

    @Override
    public Object getOriginal() {
        return flags;
    }
}
