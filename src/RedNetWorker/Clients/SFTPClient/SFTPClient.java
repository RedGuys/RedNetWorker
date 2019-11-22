package RedNetWorker.Clients.SFTPClient;

import RedNetWorker.Clients.Enums.SFTPLibrary;
import RedNetWorker.Clients.SFTPClient.SFTPExceptions.OpenConnectionException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPClient {
    private SFTPLibrary library;
    private String host;
    private String user;
    private int port;
    private JSch jsch;
    private Session jschSession;

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
                    jschSession.setPassword(password);
                    jschSession.connect();
                } catch (JSchException e) {
                    throw new OpenConnectionException(e.getMessage(),this.host,this.port,e.getCause());
                }
                break;
        }
    }
}
