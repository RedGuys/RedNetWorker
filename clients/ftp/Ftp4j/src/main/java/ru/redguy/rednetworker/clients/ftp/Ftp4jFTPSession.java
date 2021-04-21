package ru.redguy.rednetworker.clients.ftp;

import it.sauronsoftware.ftp4j.*;
import ru.redguy.rednetworker.clients.ftp.enums.Ftp4jFTPFileType;
import ru.redguy.rednetworker.clients.ftp.enums.TransferMode;
import ru.redguy.rednetworker.clients.ftp.exceptions.AbortedException;
import ru.redguy.rednetworker.clients.ftp.exceptions.UnknownServerErrorException;
import ru.redguy.rednetworker.utils.DataTime;
import ru.redguy.rednetworker.utils.exceptions.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Ftp4jFTPSession implements IFTPSession {

    public boolean remoteRenameBlockFix = false;
    FTPClient client;

    public Ftp4jFTPSession(FTPClient client) {
        this.client = client;
    }

    @Override
    public Ftp4jFTPConnection logout() throws IOException, UnknownServerErrorException {
        try {
            client.logout();
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
        return new Ftp4jFTPConnection(client);
    }

    @Override
    public String pwd() throws IOException, UnknownServerErrorException {
        try {
            return client.currentDirectory();
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public String getStatus() throws IOException, UnknownServerErrorException {
        StringBuilder builder = new StringBuilder();
        try {
            for(String s : client.serverStatus()) {
                builder.append(s).append("\n");
            }
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
        return builder.toString();
    }

    @Override
    public String getSystemType() throws IOException, NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public String getServerHelp() throws IOException, UnknownServerErrorException {
        StringBuilder builder = new StringBuilder();
        try {
            for(String s : client.help()) {
                builder.append(s).append("\n");
            }
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
        return builder.toString();
    }

    @Override
    public String[] runCommand(String command, String args) throws IOException, UnknownServerErrorException {
        try {
            return client.sendCustomCommand(command+" "+args).getMessages();
        } catch (FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public FTPFile[] list() throws IOException, AbortedException, UnknownServerErrorException {
        ArrayList<FTPFile> files = new ArrayList<>();
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
        } catch (FTPIllegalReplyException | FTPException | FTPDataTransferException | FTPListParseException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] list(String path) throws IOException, AbortedException, UnknownServerErrorException {
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
        } catch (FTPIllegalReplyException | FTPException | FTPDataTransferException | FTPListParseException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),e.getCause());
        }
        return files.toArray(new FTPFile[0]);
    }

    @Override
    public FTPFile[] listDirs(String path) throws IOException, NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public FTPFile[] listDirs() throws IOException, NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public void uploadFile(String localPath, String remotePath) throws IOException, UnknownServerErrorException, AbortedException {
        try {
            if (remoteRenameBlockFix) {
                File file = new File(localPath);
                file.renameTo(new File(new File(remotePath).getName()));
                client.upload(file);
                file.renameTo(new File(localPath));
            } else {
                this.client.upload(new File(localPath));
                this.client.rename(pwd() + new File(localPath).getName(), remotePath);
            }
        } catch (FTPIllegalReplyException | FTPException | FTPDataTransferException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void downloadFile(String localPath, String remotePath) throws IOException, UnknownServerErrorException, AbortedException {
        try {
            this.client.download(remotePath,new File(localPath));
        } catch (FTPIllegalReplyException | FTPException | FTPDataTransferException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void rmdir(String remotePath) throws IOException, UnknownServerErrorException {
        try {
            client.deleteDirectory(remotePath);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void mkdir(String remotePath) throws IOException, UnknownServerErrorException {
        try {
            client.createDirectory(remotePath);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void delete(String remoteFile) throws IOException, UnknownServerErrorException {
        try {
            client.deleteFile(remoteFile);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void cd(String path) throws IOException, UnknownServerErrorException {
        try {
            client.changeDirectory(path);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void rename(String oldName, String newName) throws IOException, UnknownServerErrorException {
        try {
            client.rename(oldName, newName);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void disconnect() throws IOException, UnknownServerErrorException {
        try {
            client.disconnect(true);
        } catch (FTPIllegalReplyException | FTPException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public void appendFile(String remoteFile, InputStream inputStream) throws IOException, UnknownServerErrorException, AbortedException, InterruptedException {
        Ftp4jListener ftp4jListener = new Ftp4jListener();
        try {
            client.append(remoteFile, inputStream, 0,ftp4jListener);
            while (!ftp4jListener.finish) {
                Thread.sleep(100);
            }
        } catch (FTPException | FTPDataTransferException | FTPIllegalReplyException e) {
            throw new UnknownServerErrorException(e.getMessage(),e.getCause());
        } catch (FTPAbortedException e) {
            throw new AbortedException(e.getMessage(),e.getCause());
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
}
