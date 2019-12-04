package RedNetWorker.Clients.FTPClient;

import RedNetWorker.Clients.FTPClient.FTPExceptions.*;
import RedNetWorker.Utils.DataTime;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ApacheFTPClient implements IFTPClient {
    private org.apache.commons.net.ftp.FTPClient client;
    private String host;
    private int port;
    private String user;

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
        connect(host,22);
    }

    @Override
    public void login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        this.user = login;
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
    public void setWorkingDirectory(String workingDirectory) throws ConnectionException, AuthorizationException, UnknownServerErrorException {
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
    public ArrayList<FTPFile> list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<FTPFile>();
        try {
            for (org.apache.commons.net.ftp.FTPFile ftpFile : client.listFiles(path)) {
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
            client.storeFile(remotePath, fis);
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
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }
}
