package ru.redguy.rednetworker.clients.tftp;

import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import ru.redguy.rednetworker.clients.tftp.enums.TransferMode;
import ru.redguy.rednetworker.clients.tftp.exeptions.FileTransferException;
import ru.redguy.rednetworker.clients.tftp.exeptions.OpenConnectionException;

import java.io.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ApacheTFTPClient implements ITFTPClient {

    TFTPClient tftpClient;
    InetAddress inetAddress;
    int port;

    ApacheTFTPClient() {
        tftpClient = new TFTPClient();
    }

    @Override
    public void connect(String host, int port) throws ru.redguy.rednetworker.clients.tftp.exeptions.UnknownHostException, OpenConnectionException {
        try {
            this.inetAddress = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new ru.redguy.rednetworker.clients.tftp.exeptions.UnknownHostException(e.getMessage(),host,port,e.getCause());
        }
        this.port = port;
        try {
            tftpClient.open(port,inetAddress);
        } catch (SocketException e) {
            throw new OpenConnectionException(e.getMessage(),host,port,e.getCause());
        }
    }

    @Override
    public void sendFile(String filename, TransferMode mode, InputStream inputStream) throws FileTransferException {
        switch (mode) {
            case BINARY_MODE:
                try {
                    tftpClient.sendFile(filename,TFTP.BINARY_MODE,inputStream,inetAddress,port);
                } catch (IOException e) {
                    throw new FileTransferException(e.getMessage(),inetAddress.getHostAddress(),port,e.getCause());
                }
                break;
            case ASCII_MODE:
                try {
                    tftpClient.sendFile(filename,TFTP.ASCII_MODE,inputStream,inetAddress,port);
                } catch (IOException e) {
                    throw new FileTransferException(e.getMessage(),inetAddress.getHostAddress(),port,e.getCause());
                }
                break;
        }
    }

    @Override
    public void sendFile(String remoteFilename, TransferMode mode, String localFilename) throws IOException, FileTransferException {
        InputStream inputStream = new FileInputStream(localFilename);
        sendFile(remoteFilename, mode, inputStream);
        inputStream.close();
    }

    @Override
    public void receiveFile(String filename, TransferMode mode, OutputStream outputStream) throws FileTransferException {
        switch (mode) {
            case BINARY_MODE:
                try {
                    tftpClient.receiveFile(filename,TFTP.BINARY_MODE,outputStream,inetAddress,port);
                } catch (IOException e) {
                    throw new FileTransferException(e.getMessage(),inetAddress.getHostAddress(),port,e.getCause());
                }
                break;
            case ASCII_MODE:
                try {
                    tftpClient.receiveFile(filename,TFTP.ASCII_MODE,outputStream,inetAddress,port);
                } catch (IOException e) {
                    throw new FileTransferException(e.getMessage(),inetAddress.getHostAddress(),port,e.getCause());
                }
                break;
        }
    }

    @Override
    public void receiveFile(String remoteFilename, TransferMode mode, String localFilename) throws IOException, FileTransferException {
        OutputStream outputStream = new FileOutputStream(localFilename);
        receiveFile(remoteFilename, mode, outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void setTimeout(int timeout) {
        tftpClient.setMaxTimeouts(timeout);
    }

    @Override
    public void close() {
        tftpClient.close();
    }

    @Override
    public int getTimeout() {
        return tftpClient.getMaxTimeouts();
    }

}
