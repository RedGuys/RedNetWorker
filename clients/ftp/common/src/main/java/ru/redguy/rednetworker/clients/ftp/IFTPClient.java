package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.enums.TransferMode;
import ru.redguy.rednetworker.utils.NotImplementedException;
import ru.redguy.rednetworker.clients.ftp.exceptions.*;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface IFTPClient {

    IFTPClient host(String host);

    IFTPClient port(int port);

    IFTPConnection connect() throws OpenConnectionException, IOException;
}
