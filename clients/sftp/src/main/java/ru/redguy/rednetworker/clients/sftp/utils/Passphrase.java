package ru.redguy.rednetworker.clients.sftp.utils;

@SuppressWarnings("unused")
public class Passphrase {
    private final String passphrase;
    public Passphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public String getPassphrase() {
        return passphrase;
    }
}
