package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.clients.sftp.exceptions.OpenConnectionException;
import ru.redguy.rednetworker.clients.sftp.exceptions.ServerMethodErrorException;
import ru.redguy.rednetworker.clients.sftp.utils.Passphrase;

import java.io.File;

public interface ISFTPClient {
    public default void connect(String host, String user, String password) throws OpenConnectionException
    {
        connect(host, 22, user, password);
    }
    public default void connect(String host, String user, String password, String knownHostsFile) throws OpenConnectionException {
        connect(host,22,user,password,knownHostsFile);
    }
    public default void connect(String host, int port, String user, String password) throws OpenConnectionException {
        connect(host, port, user, password,"~/.ssh/known_hosts");
    }
    public void connect(String host, int port, String user, String password, String knownHostsFile) throws OpenConnectionException;
    public default void connect(String host, String user, File keyFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile);
    }
    public default void connect(String host, String user, File keyFile, String knownHostsFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile, knownHostsFile);
    }
    public default void connect(String host, int port, String user, File keyFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile, "~/.ssh/known_hosts");
    }
    public void connect(String host, int port, String user, File keyFile, String knownHostsFile) throws OpenConnectionException;
    public default void connect(String host, String user, File keyFile, Passphrase passphrase) throws OpenConnectionException {
        connect(host, 22, user, keyFile, passphrase);
    }
    public default void connect(String host, String user, File keyFile, Passphrase passphrase, String knownHostsFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile, passphrase, knownHostsFile);
    }
    public default void connect(String host, int port, String user, File keyFile, Passphrase passphrase) throws OpenConnectionException {
        connect(host, port, user, keyFile, passphrase, "~/.ssh/known_hosts");
    }
    public void connect(String host, int port, String user, File keyFile, Passphrase passphrase, String knownHostsFile) throws OpenConnectionException;
    public void setWorkingDirectory(String workingDirectory) throws ServerMethodErrorException;
    public String getWorkingDirectory() throws ServerMethodErrorException;
    public SFTPFile ls() throws ServerMethodErrorException;
    public SFTPFile ls(String path) throws ServerMethodErrorException;
}
