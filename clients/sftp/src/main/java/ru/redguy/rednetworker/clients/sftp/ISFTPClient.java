package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.clients.sftp.exceptions.ServerMethodErrorException;
import ru.redguy.rednetworker.clients.sftp.utils.Passphrase;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.File;

@SuppressWarnings("unused")
public interface ISFTPClient {
    default void connect(String host, String user, String password) throws OpenConnectionException
    {
        connect(host, 22, user, password);
    }
    default void connect(String host, String user, String password, String knownHostsFile) throws OpenConnectionException {
        connect(host,22,user,password,knownHostsFile);
    }
    default void connect(String host, int port, String user, String password) throws OpenConnectionException {
        connect(host, port, user, password,"~/.ssh/known_hosts");
    }
    void connect(String host, int port, String user, String password, String knownHostsFile) throws OpenConnectionException;
    default void connect(String host, String user, File keyFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile);
    }
    default void connect(String host, String user, File keyFile, String knownHostsFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile, knownHostsFile);
    }
    default void connect(String host, int port, String user, File keyFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile, "~/.ssh/known_hosts");
    }
    void connect(String host, int port, String user, File keyFile, String knownHostsFile) throws OpenConnectionException;
    default void connect(String host, String user, File keyFile, Passphrase passphrase) throws OpenConnectionException {
        connect(host, 22, user, keyFile, passphrase);
    }
    default void connect(String host, String user, File keyFile, Passphrase passphrase, String knownHostsFile) throws OpenConnectionException {
        connect(host, 22, user, keyFile, passphrase, knownHostsFile);
    }
    default void connect(String host, int port, String user, File keyFile, Passphrase passphrase) throws OpenConnectionException {
        connect(host, port, user, keyFile, passphrase, "~/.ssh/known_hosts");
    }
    void connect(String host, int port, String user, File keyFile, Passphrase passphrase, String knownHostsFile) throws OpenConnectionException;
    void setWorkingDirectory(String workingDirectory) throws ServerMethodErrorException;
    String getWorkingDirectory() throws ServerMethodErrorException;
    void cd(String path) throws ServerMethodErrorException;
    SFTPFile[] ls() throws ServerMethodErrorException;
    SFTPFile[] ls(String path) throws ServerMethodErrorException;
    void mkdir(String name) throws ServerMethodErrorException;
    void rename(String oldPath, String newPath) throws ServerMethodErrorException;
}
