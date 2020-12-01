package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.enums.TransferMode;

import java.io.IOException;
import java.io.InputStream;

public interface IFTPSession {

    IFTPConnection logout() throws IOException;

    String pwd() throws IOException;

    String getStatus() throws IOException;

    String getSystemType() throws IOException;

    String getServerHelp() throws IOException;

    String[] runCommand(String command, String args) throws IOException;

    FTPFile[] list() throws IOException;

    FTPFile[] list(String path) throws IOException;

    FTPFile[] listDirs(String path) throws IOException;

    FTPFile[] listDirs() throws IOException;

    void uploadFile(String localPath, String remotePath) throws IOException;

    void downloadFile(String localPath, String remotePath) throws IOException;

    void rmdir(String remotePath) throws IOException;

    void mkdir(String remotePath) throws IOException;

    void delete(String remoteFile) throws IOException;

    void cd(String path) throws IOException;

    void rename(String oldName, String newName) throws IOException;

    void disconnect() throws IOException;

    void appendFile(String remoteFile, InputStream inputStream) throws IOException;

    void changeLocalTransferMode(TransferMode transferMode);
}
