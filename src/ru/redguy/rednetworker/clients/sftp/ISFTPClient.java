package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.clients.sftp.exceptions.OpenConnectionException;
import ru.redguy.rednetworker.clients.sftp.exceptions.ServerMethodErrorException;

public interface ISFTPClient {
    public void connect(String host, String user, String password) throws OpenConnectionException;
    public void connect(String host, String user, String password, String knownHostsFile) throws OpenConnectionException;
    public void connect(String host, int port, String user, String password) throws OpenConnectionException;
    public void connect(String host, int port, String user, String password, String knownHostsFile) throws OpenConnectionException;
    public void setWorkingDirectory(String workingDirectory) throws ServerMethodErrorException;
    public String getWorkingDirectory() throws ServerMethodErrorException;
    public SFTPFile ls() throws ServerMethodErrorException;
    public SFTPFile ls(String path) throws ServerMethodErrorException;
}
