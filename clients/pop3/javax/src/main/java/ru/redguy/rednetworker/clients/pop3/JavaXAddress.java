package ru.redguy.rednetworker.clients.pop3;

import javax.mail.Address;

public class JavaXAddress implements IPOP3Address {

    public Address address;

    public JavaXAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address.toString();
    }

    @Override
    public String getType() {
        return address.getType();
    }

    @Override
    public boolean equals(Object address) {
        if(address instanceof JavaXAddress) {
            return this.address.equals(((JavaXAddress) address).address);
        } else {
            return this.address.equals(address);
        }
    }

    @Override
    public Object getOriginal() {
        return address;
    }

}
