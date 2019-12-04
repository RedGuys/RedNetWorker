package RedNetWorker.Clients.FTPClient;

import RedNetWorker.Clients.Enums.FTPLibrary;
import RedNetWorker.Clients.FTPClient.FTPExceptions.*;
import it.sauronsoftware.ftp4j.*;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.ArrayList;

public class FTPClient {
    private org.apache.commons.net.ftp.FTPClient ftpClient; //For apacheFTPClient
    private FTPLibrary library;
    private it.sauronsoftware.ftp4j.FTPClient ftp4j; //For ftp4j
    private String workingDirectory = "/";
    private String host;
    private int port;
    private String user;
    public FTPClient(FTPLibrary library) {
        this.library = library;
        switch (library) {
            case apacheFTPClient:
                this.ftpClient = new org.apache.commons.net.ftp.FTPClient();
                break;
            case ftp4jFTPClient:
                this.ftp4j = new it.sauronsoftware.ftp4j.FTPClient();
        }
    }

    public void Connect(String host,int port) throws OpenConnectionException {
        this.host = host;
        this.port = port;
        switch (library) {
            case apacheFTPClient:
                try {
                    this.ftpClient.connect(host, port);
                } catch (IOException e) {
                    throw new OpenConnectionException(e.getMessage(), host, port, e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    this.ftp4j.connect(host, port);
                } catch (IOException | FTPIllegalReplyException | FTPException e) {
                    throw new OpenConnectionException(e.getMessage(), host, port, e.getCause());
                }
                break;
        }
    }

    public void Connect(String host) throws OpenConnectionException {
        Connect(host, 21);
    }

    public void Login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        this.user = login;
        switch (library) {
            case apacheFTPClient:
                try {
                    this.ftpClient.login(login, password);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    this.ftp4j.login(login, password);
                } catch (FTPException e) {
                    throw new AuthorizationException(e.getMessage(), this.host, this.user, e.getCause());
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void LoginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    this.ftpClient.login("anonymous",email);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(), this.host, this.port, e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    this.ftp4j.login("anonymous", email);
                } catch (FTPException e) {
                    throw new AuthorizationException(e.getMessage(), this.host, this.user, e.getCause());
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void LoginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException {
        LoginAnonymous("anonymous@anonymous.com");
    }

    public void setWorkingDirectory(String workingDirectory) throws ConnectionException, AuthorizationException, UnknownServerErrorException {
        this.workingDirectory = workingDirectory;
        switch (library) {
            case apacheFTPClient:
                try {
                    this.ftpClient.changeWorkingDirectory(this.workingDirectory);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    this.ftp4j.changeDirectory(this.workingDirectory);
                } catch (FTPException e) {
                    throw new AuthorizationException(e.getMessage(), this.host, this.user, e.getCause());
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(), this.host, this.port, this.user, e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    public ArrayList<FTPFile> list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                ArrayList<FTPFile> files = new ArrayList<FTPFile>();
                try {
                    for (FTPFile ftpFile : this.ftpClient.listFiles(path)) {
                        files.add(ftpFile);
                    }
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                return files;
            case ftp4jFTPClient:
                files = new ArrayList<FTPFile>();
                try {
                    for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.ftp4j.list(path)) {
                        FTPFile file = new FTPFile();
                        file.setLink(ftpFile.getLink());
                        file.setName(ftpFile.getName());
                        file.setSize(ftpFile.getSize());
                        file.setType(ftpFile.getType());
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
        return null;
    }

    public ArrayList<FTPFile> list() throws ConnectionException, AbortedException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                ArrayList<FTPFile> files = new ArrayList<FTPFile>();
                try {
                    for (FTPFile ftpFile : this.ftpClient.listFiles()) {
                        files.add(ftpFile);
                    }
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                return files;
            case ftp4jFTPClient:
                files = new ArrayList<FTPFile>();
                try {
                    for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.ftp4j.list()) {
                        FTPFile file = new FTPFile();
                        file.setLink(ftpFile.getLink());
                        file.setName(ftpFile.getName());
                        file.setSize(ftpFile.getSize());
                        file.setType(ftpFile.getType());
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
        return null;
    }

    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException {
        switch (library) {
            case apacheFTPClient:
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(localPath);
                    this.ftpClient.storeFile(remotePath, fis);
                    fis.close();
                } catch (FileNotFoundException e) {
                    throw e;
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    this.ftp4j.upload(new File(localPath));
                    this.ftp4j.rename(this.workingDirectory+new File(localPath).getName(),remotePath);
                } catch (IOException|FTPException|FTPDataTransferException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPAbortedException e) {
                    throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void downloadFile(String localPath,String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException {
        switch (library) {
            case apacheFTPClient:
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(localPath);
                    ftpClient.retrieveFile(remotePath, fos);
                    fos.close();
                } catch (FileNotFoundException e) {
                    throw new FTPFileNotFoundException(e.getMessage(),this.host,this.port,this.user,remotePath,e.getCause());
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    this.ftp4j.download(remotePath,new File(localPath));
                } catch (FileNotFoundException e) {
                    throw new FTPFileNotFoundException(e.getMessage(),this.host,this.port,this.user,remotePath,e.getCause());
                } catch (IOException|FTPException|FTPDataTransferException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPAbortedException e) {
                    throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void rmdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.removeDirectory(remotePath);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.deleteDirectory(remotePath);
                } catch (FTPException|IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }  catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.makeDirectory(remotePath);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.createDirectory(remotePath);
                } catch (IOException|FTPException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void delete(String remotePath) throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.deleteFile(remotePath);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.deleteFile(remotePath);
                } catch (IOException|FTPException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void cd(String remotePath) throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.changeWorkingDirectory(remotePath);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.changeDirectory(remotePath);
                } catch (IOException|FTPException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void logout() throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.logout();
                } catch (IOException|FTPException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.rename(oldPath, newPath);
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.rename(oldPath, newPath);
                } catch (IOException|FTPException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }

    public void disconnect() throws ConnectionException, UnknownServerErrorException {
        switch (library) {
            case apacheFTPClient:
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
            case ftp4jFTPClient:
                try {
                    ftp4j.disconnect(true);
                } catch (IOException|FTPException e) {
                    throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                } catch (FTPIllegalReplyException e) {
                    throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
                }
                break;
        }
    }
}
