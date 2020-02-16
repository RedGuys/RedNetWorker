package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.clients.sftp.exceptions.OpenConnectionException;
import ru.redguy.rednetworker.clients.sftp.exceptions.ServerMethodErrorException;
import com.jcraft.jsch.*;
import ru.redguy.rednetworker.clients.sftp.utils.Passphrase;

import java.io.File;
import java.util.Vector;

public class JschSFTPClient implements ISFTPClient {
    private String host;
    private String user;
    private int port;

    private JSch jsch;
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

    public SFTPFile ls() throws ServerMethodErrorException {
        return ls();
    }

    public SFTPFile ls(String path) throws ServerMethodErrorException {
        try {
            Vector files = channelSftp.ls(path);
            for (Object file : files) {
                System.out.println(file);
            }
            return null;
        } catch (SftpException e) {
            throw new ServerMethodErrorException(e.getMessage(), this.host, this.port, e.getCause());
        }
    }


}
