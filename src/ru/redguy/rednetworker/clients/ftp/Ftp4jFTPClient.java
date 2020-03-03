package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.Utils.DataTime;
import it.sauronsoftware.ftp4j.*;
import ru.redguy.rednetworker.clients.ftp.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Ftp4jFTPClient implements IFTPClient {
    public boolean remoteRenameBlockFix = false; //Fix problem with upload files to server where blocked file renaming
    private final it.sauronsoftware.ftp4j.FTPClient client;
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
    public FTPFile[] list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<>();
        try {
            for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.client.list(path)) {
                Ftp4jFTPFile file = new Ftp4jFTPFile();
                file.link = ftpFile.getLink();
                file.lastEditDate = new DataTime(ftpFile.getModifiedDate());
                file.name = ftpFile.getName();
                file.size = ftpFile.getSize();
                switch (ftpFile.getType()) {
                    case 0:
                        file.type = Ftp4jFTPFileType.FILE;
                        break;
                    case 1:
                        file.type = Ftp4jFTPFileType.DIRECTORY;
                        break;
                    case 2:
                        file.type = Ftp4jFTPFileType.LINK;
                        break;
                }
                files.add(file);
            }
        } catch (IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPListParseException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public ru.redguy.rednetworker.clients.ftp.FTPFile[] list() throws ConnectionException, AbortedException, UnknownServerErrorException {
        ArrayList<ru.redguy.rednetworker.clients.ftp.FTPFile> files = new ArrayList<>();
        try {
            for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.client.list()) {
                Ftp4jFTPFile file = new Ftp4jFTPFile();
                file.link = ftpFile.getLink();
                file.lastEditDate = new DataTime(ftpFile.getModifiedDate());
                file.name = ftpFile.getName();
                file.size = ftpFile.getSize();
                switch (ftpFile.getType()) {
                    case 0:
                        file.type = Ftp4jFTPFileType.FILE;
                        break;
                    case 1:
                        file.type = Ftp4jFTPFileType.DIRECTORY;
                        break;
                    case 2:
                        file.type = Ftp4jFTPFileType.LINK;
                        break;
                }
                files.add(file);
            }
        } catch (IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPListParseException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException {
        try {
            if(remoteRenameBlockFix) {
                File file = new File(localPath);
                file.renameTo(new File(new File(remotePath).getName()));
                client.upload(file);
                file.renameTo(new File(localPath));
            } else {
                this.client.upload(new File(localPath));
                this.client.rename(getWorkingDirectory() + new File(localPath).getName(), remotePath);
            }
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

    @Override
    public void appendFile(String remoteFile, InputStream inputStream, boolean async) throws ConnectionException, UnknownServerErrorException, AbortedException {
        Ftp4jListener ftp4jListener = new Ftp4jListener();
        try {
            client.append(remoteFile, inputStream, 0,ftp4jListener);
            if(!async) {
                while (!ftp4jListener.finish) {
                    Thread.sleep(100);
                }
            }
        } catch (IOException | FTPException | FTPDataTransferException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void changeAccount(String account) throws ConnectionException, UnknownServerErrorException {
        try {
            client.changeAccount(account);
        } catch (IOException | FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public String[] runCommand(String command, String args) throws ConnectionException, UnknownServerErrorException {
        try {
            return client.sendCustomCommand(command+" "+args).getMessages();
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public void changeLocalTransferMode(TransferMode transferMode) {
        switch (transferMode) {
            case ACTIVE:
                client.setPassive(false);
                break;
            case PASSIVE:
                client.setPassive(true);
                break;
        }
    }

    @Override
    public String getStatus() throws ConnectionException, UnknownServerErrorException {
        try {
            StringBuilder builder = new StringBuilder();
            for(String s : client.serverStatus()) {
                builder.append(s).append("\n");
            }
            return builder.toString();
        } catch (IOException | FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }

    @Override
    public String getServerHelp() throws ConnectionException, UnknownServerErrorException {
        try {
            StringBuilder builder = new StringBuilder();
            for(String s : client.help()) {
                builder.append(s).append("\n");
            }
            return builder.toString();
        } catch (IOException | FTPException e) {
            throw new ConnectionException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),this.host,this.port,this.user,e.getCause());
        }
    }
}
