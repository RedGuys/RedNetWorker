package RedNetWorker.Clients.SFTPClient;

import RedNetWorker.Clients.Enums.SFTPLibrary;
import RedNetWorker.Clients.SFTPClient.SFTPExceptions.OpenConnectionException;
import RedNetWorker.Clients.SFTPClient.SFTPExceptions.ServerMethodErrorException;
import RedNetWorker.Utils.Logger;
import com.jcraft.jsch.*;

import java.util.Vector;

public class SFTPClient {
    private SFTPLibrary library;
    private String host;
    private String user;
    private int port;
    private String workingDirectory = "/";

    private JSch jsch;
    private Session jschSession;
    private ChannelSftp channelSftp;

    public SFTPClient(SFTPLibrary library) {
        this.library = library;
        switch (library) {
            case jsch:
                this.jsch = new JSch();
                break;
        }
    }

    public void connect(String host, String user, String password) throws OpenConnectionException {
        connect(host, 22, user, password);
    }

    public void connect(String host, String user, String password, String knownHostsFile) throws OpenConnectionException {
        connect(host,22,user,password,knownHostsFile);
    }

    public void connect(String host, int port, String user, String password) throws OpenConnectionException {
        connect(host, port, user, password,"~/.ssh/known_hosts");
    }

    public void connect(String host, int port, String user, String password, String knownHostsFile) throws OpenConnectionException {
        this.host = host;
        this.user = user;
        this.port = port;
        switch (this.library) {
            case jsch:
                try {
                    this.jsch.setKnownHosts(knownHostsFile);
                    this.jschSession = jsch.getSession(user, host);
                    this.jschSession.setPassword(password);
                    this.jschSession.connect(); //TODO: Fix java.net.ConnectException: Connection refused: connect
                    this.channelSftp = (ChannelSftp)jschSession.openChannel("sftp");
                    this.channelSftp.connect();
                } catch (JSchException e) {
                    throw new OpenConnectionException(e.getMessage(),this.host,this.port,e.getCause());
                }
                break;
        }
    }

    public void setWorkingDirectory(String workingDirectory) throws ServerMethodErrorException {
        this.workingDirectory = workingDirectory;
        switch (library) {
            case jsch:
                try {
                    channelSftp.cd(this.workingDirectory);
                } catch (SftpException e) {
                    throw new ServerMethodErrorException(e.getMessage(),this.host,this.port,e.getCause());
                }
                break;
        }
    }

    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    public SFTPFile ls() throws ServerMethodErrorException {
        return ls(this.workingDirectory);
    }

    public SFTPFile ls(String path) throws ServerMethodErrorException {
        switch (library) {
            case jsch:
                try {
                    Vector files = channelSftp.ls(path);
                    for (Object file : files) {
                        Logger.info(file);
                    }
                    return null;
                } catch (SftpException e) {
                    throw new ServerMethodErrorException(e.getMessage(),this.host,this.port,e.getCause());
                }
        }
        return null;
    }
}
