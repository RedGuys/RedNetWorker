package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.utils.NotImplementedException;
import ru.redguy.rednetworker.clients.ftp.exceptions.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

@SuppressWarnings("unused")
public interface IFTPClient {
    void connect(String host, int port) throws OpenConnectionException;

    void connect(String host) throws OpenConnectionException;

    void login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    void loginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    void loginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    void setWorkingDirectory(String workingDirectory) throws ConnectionException;

    String getWorkingDirectory() throws ConnectionException;

    FTPFile[] list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException;

    FTPFile[] list() throws ConnectionException, AbortedException, UnknownServerErrorException;

    void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException;

    void downloadFile(String localPath, String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException;

    void rmdir(String remotePath) throws ConnectionException, UnknownServerErrorException;

    void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException;

    void delete(String remotePath) throws ConnectionException, UnknownServerErrorException;

    void cd(String remotePath) throws ConnectionException, UnknownServerErrorException;

    void logout() throws ConnectionException, UnknownServerErrorException;

    void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException;

    void disconnect() throws ConnectionException, UnknownServerErrorException;

    void appendFile(String remoteFile, InputStream inputStream, boolean async) throws ConnectionException, UnknownServerErrorException, AbortedException;

    void changeAccount(String account) throws ConnectionException, UnknownServerErrorException;

    String[] runCommand(String command, String args) throws ConnectionException, UnknownServerErrorException;

    void changeLocalTransferMode(TransferMode transferMode);

    String getStatus() throws ConnectionException, UnknownServerErrorException;

    default String getSystemType() throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    String getServerHelp() throws ConnectionException, UnknownServerErrorException;

    default FTPFile[] listDirs(String path) throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    default FTPFile[] listDirs() throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    default FTPFile mtdmFile(String file) throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    default FTPFile[] mlist(String path) throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }

    default FTPFile[] mlist() throws ConnectionException, NotImplementedException {
        throw new NotImplementedException();
    }
}
