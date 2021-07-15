package ru.redguy.rednetworker.clients.ftp;

import org.apache.commons.net.ftp.FTPClient;
import ru.redguy.rednetworker.clients.ftp.enums.ApacheFTPFileType;
import ru.redguy.rednetworker.clients.ftp.enums.TransferMode;
import ru.redguy.rednetworker.utils.DataTime;

import java.io.*;
import java.util.ArrayList;

public class ApacheFTPSession implements IFTPSession {

    FTPClient client;

    public ApacheFTPSession(FTPClient client) {
        this.client = client;
    }

    @Override
    public void cd(String path) throws IOException {
        client.changeWorkingDirectory(path);
    }

    @Override
    public String pwd() throws IOException {
        return client.printWorkingDirectory();
    }

    @Override
    public FTPFile[] list() throws IOException {
        ArrayList<FTPFile> files = new ArrayList<>();
        for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listFiles()) {
            ApacheFTPFile myftpfile = new ApacheFTPFile();
            myftpfile.link = ftpFile.getLink();
            myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
            myftpfile.name = ftpFile.getName();
            myftpfile.path = ftpFile.getLink();
            myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
            myftpfile.size = ftpFile.getSize();
            myftpfile.owner = ftpFile.getUser();
            myftpfile.group = ftpFile.getGroup();
            myftpfile.isDirectory = ftpFile.isDirectory();
            myftpfile.isFile = ftpFile.isFile();
            myftpfile.isUnknown = ftpFile.isUnknown();
            myftpfile.isValid = ftpFile.isValid();
            myftpfile.isLink = ftpFile.isSymbolicLink();
            switch (ftpFile.getType()) {
                case 0:
                    myftpfile.type = ApacheFTPFileType.FILE;
                    break;
                case 1:
                    myftpfile.type = ApacheFTPFileType.DIRECTORY;
                    break;
                case 2:
                    myftpfile.type = ApacheFTPFileType.SYMBOLIC_LINK;
                    break;
                default:
                    myftpfile.type = ApacheFTPFileType.UNKNOWN;
                    break;
            }
            files.add(myftpfile);
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] list(String path) throws IOException {
        ArrayList<FTPFile> files = new ArrayList<>();
        for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listFiles(path)) {
            ApacheFTPFile myftpfile = new ApacheFTPFile();
            myftpfile.link = ftpFile.getLink();
            myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
            myftpfile.name = ftpFile.getName();
            myftpfile.path = ftpFile.getLink();
            myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
            myftpfile.size = ftpFile.getSize();
            myftpfile.owner = ftpFile.getUser();
            myftpfile.group = ftpFile.getGroup();
            myftpfile.isDirectory = ftpFile.isDirectory();
            myftpfile.isFile = ftpFile.isFile();
            myftpfile.isUnknown = ftpFile.isUnknown();
            myftpfile.isValid = ftpFile.isValid();
            myftpfile.isLink = ftpFile.isSymbolicLink();
            switch (ftpFile.getType()) {
                case 0:
                    myftpfile.type = ApacheFTPFileType.FILE;
                    break;
                case 1:
                    myftpfile.type = ApacheFTPFileType.DIRECTORY;
                    break;
                case 2:
                    myftpfile.type = ApacheFTPFileType.SYMBOLIC_LINK;
                    break;
                default:
                    myftpfile.type = ApacheFTPFileType.UNKNOWN;
                    break;
            }
            files.add(myftpfile);
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws IOException {
        FileInputStream fis;
        File file = new File(remotePath);
        client.allocate((int) file.length());
        fis = new FileInputStream(localPath);
        client.storeFile(remotePath, fis);
        fis.close();
    }

    @Override
    public void downloadFile(String localPath, String remotePath) throws IOException {
        FileOutputStream fos;
        fos = new FileOutputStream(localPath);
        client.retrieveFile(remotePath, fos);
        fos.close();
    }

    @Override
    public void rmdir(String remotePath) throws IOException {
        client.removeDirectory(remotePath);
    }

    @Override
    public void mkdir(String remotePath) throws IOException {
        client.makeDirectory(remotePath);
    }

    @Override
    public void delete(String remoteFile) throws IOException {
        client.deleteFile(remoteFile);
    }

    @Override
    public ApacheFTPConnection logout() throws IOException {
        client.logout();
        return new ApacheFTPConnection(client);
    }

    @Override
    public void rename(String oldName, String newName) throws IOException {
        client.rename(oldName, newName);
    }

    @Override
    public void disconnect() throws IOException {
        client.disconnect();
    }

    @Override
    public void appendFile(String remoteFile, InputStream inputStream) throws IOException {
        client.appendFile(remoteFile, inputStream);
    }

    @Override
    public String[] runCommand(String command, String args) throws IOException {
        return client.doCommandAsStrings(command,args);
    }

    @Override
    public void changeLocalTransferMode(TransferMode transferMode) {
        if (transferMode == TransferMode.ACTIVE) {
            client.enterLocalActiveMode();
        } else {
            client.enterLocalPassiveMode();
        }
    }

    @Override
    public String getStatus() throws IOException {
        return client.getStatus();
    }

    @Override
    public String getSystemType() throws IOException {
        return client.getSystemType();
    }

    @Override
    public String getServerHelp() throws IOException {
        return client.listHelp();
    }

    @Override
    public FTPFile[] listDirs(String path) throws IOException {
        ArrayList<FTPFile> files = new ArrayList<>();
        for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories(path)) {
            ApacheFTPFile myftpfile = new ApacheFTPFile();
            myftpfile.link = ftpFile.getLink();
            myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
            myftpfile.name = ftpFile.getName();
            myftpfile.path = ftpFile.getLink();
            myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
            myftpfile.size = ftpFile.getSize();
            myftpfile.owner = ftpFile.getUser();
            myftpfile.group = ftpFile.getGroup();
            myftpfile.isDirectory = ftpFile.isDirectory();
            myftpfile.isFile = ftpFile.isFile();
            myftpfile.isUnknown = ftpFile.isUnknown();
            myftpfile.isValid = ftpFile.isValid();
            myftpfile.isLink = ftpFile.isSymbolicLink();
            switch (ftpFile.getType()) {
                case 0:
                    myftpfile.type = ApacheFTPFileType.FILE;
                    break;
                case 1:
                    myftpfile.type = ApacheFTPFileType.DIRECTORY;
                    break;
                case 2:
                    myftpfile.type = ApacheFTPFileType.SYMBOLIC_LINK;
                    break;
                case 3:
                    myftpfile.type = ApacheFTPFileType.UNKNOWN;
                    break;
            }
            files.add(myftpfile);
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] listDirs() throws IOException {
        ArrayList<FTPFile> files = new ArrayList<>();
        for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories()) {
            ApacheFTPFile myftpfile = new ApacheFTPFile();
            myftpfile.link = ftpFile.getLink();
            myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
            myftpfile.name = ftpFile.getName();
            myftpfile.path = ftpFile.getLink();
            myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
            myftpfile.size = ftpFile.getSize();
            myftpfile.owner = ftpFile.getUser();
            myftpfile.group = ftpFile.getGroup();
            myftpfile.isDirectory = ftpFile.isDirectory();
            myftpfile.isFile = ftpFile.isFile();
            myftpfile.isUnknown = ftpFile.isUnknown();
            myftpfile.isValid = ftpFile.isValid();
            myftpfile.isLink = ftpFile.isSymbolicLink();
            switch (ftpFile.getType()) {
                case 0:
                    myftpfile.type = ApacheFTPFileType.FILE;
                    break;
                case 1:
                    myftpfile.type = ApacheFTPFileType.DIRECTORY;
                    break;
                case 2:
                    myftpfile.type = ApacheFTPFileType.SYMBOLIC_LINK;
                    break;
                case 3:
                    myftpfile.type = ApacheFTPFileType.UNKNOWN;
                    break;
            }
            files.add(myftpfile);
        }
        return files.toArray(new FTPFile[0]);
    }


}
