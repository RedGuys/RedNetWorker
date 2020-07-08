package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.utils.DataTime;
import ru.redguy.rednetworker.clients.sftp.exceptions.ServerMethodErrorException;
import com.jcraft.jsch.*;
import ru.redguy.rednetworker.clients.sftp.utils.Passphrase;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("unused")
public class JschSFTPClient implements ISFTPClient {
    private String host;
    private String user;
    private int port;

    private final JSch jsch;
    private Session jschSession;
    private ChannelSftp channelSftp;

    public JschSFTPClient() {
        this.jsch = new JSch();
    }

    public void connect(String host, int port, String user, String password, String knownHostsFile) throws OpenConnectionException {
        this.host = host;
        this.user = user;
        this.port = port;
        try {
            this.jsch.setKnownHosts(knownHostsFile);
            this.jschSession = jsch.getSession(user, host, port);
            this.jschSession.setPassword(password);
            this.jschSession.connect();
            this.channelSftp = (ChannelSftp)jschSession.openChannel("sftp");
            this.channelSftp.connect();
        } catch (JSchException e) {
            throw new OpenConnectionException(e.getMessage(),this.host,this.port,e.getCause());
        }
    }

    @Override
    public void connect(String host, int port, String user, File keyFile, String knownHostsFile) throws OpenConnectionException {
        this.host = host;
        this.user = user;
        this.port = port;
        try {
            this.jsch.setKnownHosts(knownHostsFile);
            this.jsch.addIdentity(keyFile.getAbsolutePath());
            this.jschSession = jsch.getSession(user, host, port);
            this.jschSession.connect();
            this.channelSftp = (ChannelSftp)jschSession.openChannel("sftp");
            this.channelSftp.connect();
        } catch (JSchException e) {
            throw new OpenConnectionException(e.getMessage(),this.host,this.port,e.getCause());
        }
    }

    @Override
    public void connect(String host, int port, String user, File keyFile, Passphrase passphrase, String knownHostsFile) throws OpenConnectionException {
        this.host = host;
        this.user = user;
        this.port = port;
        try {
            this.jsch.setKnownHosts(knownHostsFile);
            this.jsch.addIdentity(keyFile.getAbsolutePath(), passphrase.getPassphrase());
            this.jschSession = jsch.getSession(user, host, port);
            this.jschSession.connect();
            this.channelSftp = (ChannelSftp)jschSession.openChannel("sftp");
            this.channelSftp.connect();
        } catch (JSchException e) {
            throw new OpenConnectionException(e.getMessage(),this.host,this.port,e.getCause());
        }
    }

    public void setWorkingDirectory(String workingDirectory) throws ServerMethodErrorException {
        try {
            channelSftp.cd(workingDirectory);
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(),this.host,this.port,e.getCause());
        }
    }

    public String getWorkingDirectory() throws ServerMethodErrorException {
        try {
            return channelSftp.pwd();
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(),this.host,this.port,e.getCause());
        }
    }

    public SFTPFile[] ls() throws ServerMethodErrorException {
        return ls(getWorkingDirectory());
    }

    public SFTPFile[] ls(String path) throws ServerMethodErrorException {
        try {
            ArrayList<SFTPFile> result = new ArrayList<>();
            @SuppressWarnings("rawtypes") Vector files = channelSftp.ls(path);
            for (Object rawFile : files) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)rawFile;
                JschSFTPFile sftpFile = new JschSFTPFile();
                sftpFile.name = file.getFilename();
                sftpFile.path = getWorkingDirectory()+"/"+file.getFilename();
                sftpFile.lastAccessDate = new DataTime(file.getAttrs().getATime());
                sftpFile.lastEditDate = new DataTime(file.getAttrs().getMTime());
                sftpFile.size = file.getAttrs().getSize();
                sftpFile.server = this.host+":"+this.port;
                result.add(sftpFile);
            }
            return result.toArray(new SFTPFile[0]);
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }

    public void mkdir(String name) throws ServerMethodErrorException {
        try {
            channelSftp.mkdir(name);
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }

    @Override
    public void rename(String oldPath, String newPath) throws ServerMethodErrorException {
        try {
            channelSftp.rename(oldPath,newPath);
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }

    @Override
    public void cd(String path) throws ServerMethodErrorException {
        try {
            channelSftp.cd(path);
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }
}
