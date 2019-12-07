package RedNetWorker.Clients.FTPClient;

import RedNetWorker.Clients.FTPClient.FTPExceptions.*;
import RedNetWorker.Utils.DataTime;
import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Ftp4jFTPClient implements IFTPClient {
    private it.sauronsoftware.ftp4j.FTPClient client;
    private String host;
    private int port;
    private String user;

    public Ftp4jFTPClient() {
        this.client = new it.sauronsoftware.ftp4j.FTPClient();
    }

    @Override
    public void connect(String host, int port) throws OpenConnectionException {
        try {
            this.client.connect(host, port);
        } catch (IOException | FTPIllegalReplyException | FTPException e) {
            throw new OpenConnectionException(e.getMessage(), host, port, e.getCause());
        }
    }

    @Override
    public void connect(String host) throws OpenConnectionException {
        connect(host,21);
    }

    @Override
    public void login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        try {
            this.client.login(login, password);
        } catch (FTPException e) {
            throw new AuthorizationException(e.getMessage(), this.host, this.user, e.getCause());
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void loginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        try {
            this.client.login("anonymous", email);
        } catch (FTPException e) {
            throw new AuthorizationException(e.getMessage(), this.host, this.user, e.getCause());
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void loginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        loginAnonymous("anonymous@anonymous.com");
    }

    @Override
    public void setWorkingDirectory(String workingDirectory) throws ConnectionException {
        try {
            this.client.changeDirectory(workingDirectory);
        } catch (FTPException | IOException | FTPIllegalReplyException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public String getWorkingDirectory() throws ConnectionException {
        try {
            return client.currentDirectory();
        } catch (IOException | FTPException | FTPIllegalReplyException e) {
            throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
        }
    }

    @Override
    public ArrayList<FTPFile> list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.client.list(path)) {
                FTPFile file = new FTPFile();
                file.group = null;
                file.owner = null;
                file.size = ftpFile.getSize();
                file.lastEditDate = new DataTime(ftpFile.getModifiedDate().getTime() / 1000L);
                file.createDate = null;
                file.path = ftpFile.getLink();
                file.name = ftpFile.getName();
                file.server = this.host;
                files.add(file);
            }
        } catch (IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPListParseException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files;
    }

    @Override
    public ArrayList<FTPFile> list() throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.client.list()) {
                FTPFile file = new FTPFile();
                file.group = null;
                file.owner = null;
                file.size = ftpFile.getSize();
                file.lastEditDate = new DataTime(ftpFile.getModifiedDate().getTime() / 1000L);
                file.createDate = null;
                file.path = ftpFile.getLink();
                file.name = ftpFile.getName();
                file.server = this.host;
                files.add(file);
            }
        } catch (IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPListParseException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files;
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException {
        try {
            this.client.upload(new File(localPath));
            this.client.rename(getWorkingDirectory()+new File(localPath).getName(),remotePath);
        } catch (IOException|FTPException|FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void downloadFile(String localPath, String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException {
        try {
            this.client.download(remotePath,new File(localPath));
        } catch (FileNotFoundException e) {
            throw new FTPFileNotFoundException(e.getMessage(),this.host,this.port,this.user,remotePath,e.getCause());
        } catch (IOException|FTPException|FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void rmdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.deleteDirectory(remotePath);
        } catch (FTPException|IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }  catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.createDirectory(remotePath);
        } catch (IOException|FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void delete(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.deleteFile(remotePath);
        } catch (IOException|FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void cd(String remotePath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.changeDirectory(remotePath);
        } catch (IOException|FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void logout() throws ConnectionException, UnknownServerErrorException {
        try {
            client.logout();
        } catch (IOException|FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException {
        try {
            client.rename(oldPath, newPath);
        } catch (IOException|FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void disconnect() throws ConnectionException, UnknownServerErrorException {
        try {
            client.disconnect(true);
        } catch (IOException|FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }
}
