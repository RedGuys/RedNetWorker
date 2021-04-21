package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.exceptions.UnknownServerErrorException;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.IOException;

public interface IFTPClient {

    IFTPClient host(String host);

    IFTPClient port(int port);

    IFTPConnection connect() throws OpenConnectionException, IOException, UnknownServerErrorException;
}
