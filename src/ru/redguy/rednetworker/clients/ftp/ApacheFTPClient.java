package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.exceptions.*;
import ru.redguy.rednetworker.Utils.DataTime;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.ArrayList;

public class ApacheFTPClient implements IFTPClient {
    private org.apache.commons.net.ftp.FTPClient client;
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
    public void login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        this.user = login;
        this.pass = password;
        try {
            client.login(login, password);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void loginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        this.user = "anonymous";
        try {
            client.login("anonymous",email);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }

    @Override
    public void loginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException {
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
    public FTPFile[] list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listFiles(path)) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = null;
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                myftpfile.isDirectory = ftpFile.isDirectory();
                myftpfile.isFile = ftpFile.isFile();
                myftpfile.isLink = ftpFile.isSymbolicLink();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] list() throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listFiles()) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                myftpfile.isDirectory = ftpFile.isDirectory();
                myftpfile.isFile = ftpFile.isFile();
                myftpfile.isLink = ftpFile.isSymbolicLink();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException {
        FileInputStream fis = null;
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
    public void downloadFile(String localPath, String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException {
        FileOutputStream fos = null;
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
    public void rmdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.removeDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.makeDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void delete(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.deleteFile(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void cd(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.changeWorkingDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void logout() throws ConnectionException, UnknownServerErrorException {
        try {
            client.logout();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.rename(oldPath, newPath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void disconnect() throws ConnectionException, UnknownServerErrorException {
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
    public void changeAccount(String account) throws ConnectionException, UnknownServerErrorException {
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
    public FTPFile[] listDirs(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories(path)) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = null;
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                myftpfile.isDirectory = ftpFile.isDirectory();
                myftpfile.isFile = ftpFile.isFile();
                myftpfile.isLink = ftpFile.isSymbolicLink();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] listDirs() throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories()) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                myftpfile.isDirectory = ftpFile.isDirectory();
                myftpfile.isFile = ftpFile.isFile();
                myftpfile.isLink = ftpFile.isSymbolicLink();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile mtdmFile(String file) throws ConnectionException {
        try {
            FTPFile myFtpFile = new FTPFile();
            org.apache.commons.net.ftp.FTPFile remoteFtpFile = client.mdtmFile(file);
            myFtpFile.server = this.host+":"+this.port;
            myFtpFile.name = remoteFtpFile.getName();
            myFtpFile.path = remoteFtpFile.getLink();
            myFtpFile.lastEditDate = new DataTime(remoteFtpFile.getTimestamp());
            myFtpFile.createDate = new DataTime(remoteFtpFile.getTimestamp());
            myFtpFile.size = remoteFtpFile.getSize();
            myFtpFile.owner = remoteFtpFile.getUser();
            myFtpFile.group = remoteFtpFile.getGroup();
            myFtpFile.isDirectory = remoteFtpFile.isDirectory();
            myFtpFile.isFile = remoteFtpFile.isFile();
            myFtpFile.isLink = remoteFtpFile.isSymbolicLink();
            return myFtpFile;
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public FTPFile[] mlist(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories(path)) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = null;
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                myftpfile.isDirectory = ftpFile.isDirectory();
                myftpfile.isFile = ftpFile.isFile();
                myftpfile.isLink = ftpFile.isSymbolicLink();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] mlist() throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listDirectories()) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                myftpfile.isDirectory = ftpFile.isDirectory();
                myftpfile.isFile = ftpFile.isFile();
                myftpfile.isLink = ftpFile.isSymbolicLink();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }
}
