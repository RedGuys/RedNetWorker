package ru.redguy.rednetworker.clients.tftp;

import ru.redguy.rednetworker.clients.tftp.enums.TransferMode;
import ru.redguy.rednetworker.clients.tftp.exeptions.FileTransferException;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ITFTPClient {

    default void connect(String host) throws ru.redguy.rednetworker.clients.tftp.exeptions.UnknownHostException, OpenConnectionException {
        connect(host,69);
    }

    void connect(String host, int port) throws ru.redguy.rednetworker.clients.tftp.exeptions.UnknownHostException, OpenConnectionException;

    void sendFile(String filename, TransferMode mode, InputStream inputStream) throws FileTransferException;

    void sendFile(String remoteFilename, TransferMode mode, String localFilename) throws IOException, FileTransferException;

    void receiveFile(String filename, TransferMode mode, OutputStream outputStream) throws FileTransferException;

    void receiveFile(String remoteFilename, TransferMode mode, String localFilename) throws IOException, FileTransferException;

    void setTimeout(int timeout);

    void close();

    int getTimeout();
}
