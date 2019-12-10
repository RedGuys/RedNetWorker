package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.Utils.NotImplementedException;
import ru.redguy.rednetworker.clients.ftp.exceptions.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface IFTPClient {
    public void connect(String host,int port) throws OpenConnectionException;

    public void connect(String host) throws OpenConnectionException;

    public void login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    public void loginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    public void loginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    public void setWorkingDirectory(String workingDirectory) throws ConnectionException;

    public String getWorkingDirectory() throws ConnectionException;

    public FTPFile[] list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException;

    public FTPFile[] list() throws ConnectionException, AbortedException, UnknownServerErrorException;

    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException;

    public void downloadFile(String localPath,String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException;

    public void rmdir(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void delete(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void cd(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void logout() throws ConnectionException, UnknownServerErrorException;

    public void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException;

    public void disconnect() throws ConnectionException, UnknownServerErrorException;

    public void appendFile(String remoteFile, InputStream inputStream, boolean async) throws ConnectionException, UnknownServerErrorException, AbortedException;

    public void changeAccount(String account) throws ConnectionException, UnknownServerErrorException;

    public String[] runCommand(String command, String args) throws ConnectionException, UnknownServerErrorException;

    public void changeLocalTransferMode(TransferMode transferMode);

    public String getStatus() throws ConnectionException, UnknownServerErrorException;

    public default String getSystemType() throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    public String getServerHelp() throws ConnectionException, UnknownServerErrorException;

    public default FTPFile[] listDirs(String path) throws ConnectionException, AbortedException, UnknownServerErrorException, NotImplementedException {
        throw new NotImplementedException();
    }

    public default FTPFile[] listDirs() throws ConnectionException, AbortedException, UnknownServerErrorException, NotImplementedException {
        throw new NotImplementedException();
    }

    public default FTPFile mtdmFile(String file) throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    public default FTPFile[] mlist(String path) throws ConnectionException, AbortedException, UnknownServerErrorException, NotImplementedException {
        throw new NotImplementedException();
    }

    public default FTPFile[] mlist() throws ConnectionException, AbortedException, UnknownServerErrorException, NotImplementedException {
        throw new NotImplementedException();
    }
}
