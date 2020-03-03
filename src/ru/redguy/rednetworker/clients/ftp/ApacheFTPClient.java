package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.exceptions.*;
import ru.redguy.rednetworker.utils.DataTime;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class ApacheFTPClient implements IFTPClient {
    private final org.apache.commons.net.ftp.FTPClient client;
    private String host;
    private int port;
    private String user;
    private String pass;

    public ApacheFTPClient() {
        client = new FTPClient();
    }

    @Override
    public void connect(String host, int port) throws OpenConnectionException {
        this.host = host;
        this.port = port;
        try {
            client.connect(host, port);
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(), host, port, e.getCause());
        }
    }

    @Override
    public void connect(String host) throws OpenConnectionException {
        connect(host,21);
    }

    @Override
    public void login(String login, String password) throws ConnectionException {
        this.user = login;
        this.pass = password;
        try {
            client.login(login, password);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void loginAnonymous(String email) throws ConnectionException {
        this.user = "anonymous";
        try {
            client.login("anonymous",email);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }

    @Override
    public void loginAnonymous() throws ConnectionException {
        loginAnonymous("anonymous@anonymous.com");
    }

    @Override
    public void setWorkingDirectory(String workingDirectory) throws ConnectionException {
        try {
            client.changeWorkingDirectory(workingDirectory);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public String getWorkingDirectory() throws ConnectionException {
        try {
            return client.printWorkingDirectory();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public FTPFile[] list(String path) throws ConnectionException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listFiles(path)) {
                ApacheFTPFile myftpfile = new ApacheFTPFile();
                myftpfile.link = ftpFile.getLink();
                myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
                myftpfile.server = this.host+":"+this.port;
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
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] list() throws ConnectionException {
        return list(this.getWorkingDirectory());
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws ConnectionException, FileNotFoundException {
        FileInputStream fis;
        try {
            File file = new File(remotePath);
            client.allocate((int) file.length());
            fis = new FileInputStream(localPath);
            client.storeFile(remotePath, fis);
            fis.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void downloadFile(String localPath, String remotePath) throws FTPFileNotFoundException, ConnectionException {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(localPath);
            client.retrieveFile(remotePath, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new FTPFileNotFoundException(e.getMessage(),this.host,this.port,this.user,remotePath,e.getCause());
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void rmdir(String remotePath) throws ConnectionException {
        try {
            client.removeDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void mkdir(String remotePath) throws ConnectionException {
        try {
            client.makeDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void delete(String remotePath) throws ConnectionException {
        try {
            client.deleteFile(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void cd(String remotePath) throws ConnectionException {
        try {
            client.changeWorkingDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void logout() throws ConnectionException {
        try {
            client.logout();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void rename(String oldPath, String newPath) throws ConnectionException {
        try {
            client.rename(oldPath, newPath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            client.disconnect();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void appendFile(String remoteFile, InputStream inputStream, boolean async) throws ConnectionException {
        try {
            client.appendFile(remoteFile, inputStream);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void changeAccount(String account) throws ConnectionException {
        try {
            client.logout();
            client.login(this.user,this.pass,account);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public String[] runCommand(String command, String args) throws ConnectionException {
        try {
            return client.doCommandAsStrings(command,args);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void changeLocalTransferMode(TransferMode transferMode) {
        switch (transferMode) {
            case ACTIVE:
                client.enterLocalActiveMode();
                break;
            case PASSIVE:
                client.enterLocalPassiveMode();
                break;
        }
    }

    @Override
    public String getStatus() throws ConnectionException {
        try {
            return client.getStatus();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public String getSystemType() throws ConnectionException {
        try {
            return client.getSystemType();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public String getServerHelp() throws ConnectionException {
        try {
            return client.listHelp();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public FTPFile[] listDirs(String path) throws ConnectionException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories(path)) {
                ApacheFTPFile myftpfile = new ApacheFTPFile();
                myftpfile.link = ftpFile.getLink();
                myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
                myftpfile.server = this.host+":"+this.port;
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
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] listDirs() throws ConnectionException {
        return listDirs(this.getWorkingDirectory());
    }

    @Override
    public FTPFile mtdmFile(String file) throws ConnectionException {
        try {
            org.apache.commons.net.ftp.FTPFile ftpFile = client.mdtmFile(file);
            ApacheFTPFile myftpfile = new ApacheFTPFile();
            myftpfile.link = ftpFile.getLink();
            myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
            myftpfile.server = this.host+":"+this.port;
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
            return myftpfile;
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public FTPFile[] mlist(String path) throws ConnectionException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories(path)) {
                ApacheFTPFile myftpfile = new ApacheFTPFile();
                myftpfile.link = ftpFile.getLink();
                myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
                myftpfile.server = this.host+":"+this.port;
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
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] mlist() throws ConnectionException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories()) {
                ApacheFTPFile myftpfile = new ApacheFTPFile();
                myftpfile.link = ftpFile.getLink();
                myftpfile.hardLinkCount = ftpFile.getHardLinkCount();
                myftpfile.server = this.host+":"+this.port;
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
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }
}
