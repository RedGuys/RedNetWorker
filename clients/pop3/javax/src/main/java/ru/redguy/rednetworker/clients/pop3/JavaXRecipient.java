package ru.redguy.rednetworker.clients.pop3;

import javax.mail.Address;

public class JavaXRecipient implements IPOP3Address {

    Address address;

    public JavaXRecipient(Address address) {
        this.address = address;
    }

    @Override
    public String getType() {
        return address.getType();
    }

    @Override
    public Object getOriginal() {
        return address;
    }
}
