package RedNetWorker.Clients.FTPClient;

import RedNetWorker.Clients.FTPClient.FTPExceptions.*;
import RedNetWorker.Utils.DataTime;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class apacheFTPClient implements IFTPClient {
    private org.apache.commons.net.ftp.FTPClient FTPClient;
    private String host;
    private int port;
    private String user;

    public apacheFTPClient() {
        FTPClient = new FTPClient();
    }

    @Override
    public void Connect(String host, int port) throws OpenConnectionException {
        this.host = host;
        this.port = port;
        try {
            FTPClient.connect(host, port);
        } catch (IOException e) {
            throw new OpenConnectionException(e.getMessage(), host, port, e.getCause());
        }
    }

    @Override
    public void Connect(String host) throws OpenConnectionException {
        Connect(host,22);
    }

    @Override
    public void Login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        this.user = login;
        try {
            FTPClient.login(login, password);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public void LoginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        this.user = "anonymous";
        try {
            FTPClient.login("anonymous",email);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }

    @Override
    public void LoginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        LoginAnonymous("anonymous@anonymous.com");
    }

    @Override
    public void setWorkingDirectory(String workingDirectory) throws ConnectionException, AuthorizationException, UnknownServerErrorException {
        try {
            FTPClient.changeWorkingDirectory(workingDirectory);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public String getWorkingDirectory() throws ConnectionException {
        try {
            return FTPClient.printWorkingDirectory();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public ArrayList<FTPFile> list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : FTPClient.listFiles(path)) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files;
    }

    @Override
    public ArrayList<FTPFile> list() throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : FTPClient.listFiles()) {
                FTPFile myftpfile = new FTPFile();
                myftpfile.server = this.host+":"+this.port;
                myftpfile.name = ftpFile.getName();
                myftpfile.path = ftpFile.getLink();
                myftpfile.lastEditDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.createDate = new DataTime(ftpFile.getTimestamp());
                myftpfile.size = ftpFile.getSize();
                myftpfile.owner = ftpFile.getUser();
                myftpfile.group = ftpFile.getGroup();
                files.add(myftpfile);
            }
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files;
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(localPath);
            FTPClient.storeFile(remotePath, fis);
            fis.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void downloadFile(String localPath, String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(localPath);
            FTPClient.retrieveFile(remotePath, fos);
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
            FTPClient.removeDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            FTPClient.makeDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void delete(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            FTPClient.deleteFile(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void cd(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            FTPClient.changeWorkingDirectory(remotePath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void logout() throws ConnectionException, UnknownServerErrorException {
        try {
            FTPClient.logout();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException {
        try {
            FTPClient.rename(oldPath, newPath);
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void disconnect() throws ConnectionException, UnknownServerErrorException {
        try {
            FTPClient.disconnect();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }
}
