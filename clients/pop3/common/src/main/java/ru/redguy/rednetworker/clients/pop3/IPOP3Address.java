package ru.redguy.rednetworker.clients.pop3;

public interface IPOP3Address {
    String getType();

    String toString();

    boolean equals(Object address);

    Object getOriginal();
}
