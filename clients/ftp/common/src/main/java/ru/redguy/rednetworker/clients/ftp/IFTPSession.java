package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.enums.TransferMode;
import ru.redguy.rednetworker.clients.ftp.exceptions.AbortedException;
import ru.redguy.rednetworker.clients.ftp.exceptions.UnknownServerErrorException;
import ru.redguy.rednetworker.utils.exceptions.NotImplementedException;

import java.io.IOException;
import java.io.InputStream;

public interface IFTPSession {

    IFTPConnection logout() throws IOException, UnknownServerErrorException;

    String pwd() throws IOException, UnknownServerErrorException;

    String getStatus() throws IOException, UnknownServerErrorException;

    String getSystemType() throws IOException, NotImplementedException;

    String getServerHelp() throws IOException, UnknownServerErrorException;

    String[] runCommand(String command, String args) throws IOException, UnknownServerErrorException;

    FTPFile[] list() throws IOException, AbortedException, UnknownServerErrorException;

    FTPFile[] list(String path) throws IOException, AbortedException, UnknownServerErrorException;

    FTPFile[] listDirs(String path) throws IOException, NotImplementedException;

    FTPFile[] listDirs() throws IOException, NotImplementedException;

    void uploadFile(String localPath, String remotePath) throws IOException, UnknownServerErrorException, AbortedException;

    void downloadFile(String localPath, String remotePath) throws IOException, UnknownServerErrorException, AbortedException;

    void rmdir(String remotePath) throws IOException, UnknownServerErrorException;

    void mkdir(String remotePath) throws IOException, UnknownServerErrorException;

    void delete(String remoteFile) throws IOException, UnknownServerErrorException;

    void cd(String path) throws IOException, UnknownServerErrorException;

    void rename(String oldName, String newName) throws IOException, UnknownServerErrorException;

    void disconnect() throws IOException, UnknownServerErrorException;

    void appendFile(String remoteFile, InputStream inputStream) throws IOException, UnknownServerErrorException, AbortedException, InterruptedException;

    void changeLocalTransferMode(TransferMode transferMode);
}
