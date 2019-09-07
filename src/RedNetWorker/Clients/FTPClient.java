package RedNetWorker.Clients;

import RedNetWorker.Clients.Enums.FTPLibrary;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FTPClient {
    //For apacheFTPClient
    private org.apache.commons.net.ftp.FTPClient ftpClient;
    //For apacheFTPClient
    private FTPLibrary library;
    private String workingDirectory = "/";
    public FTPClient(FTPLibrary library) {
        this.library = library;
        switch (library) {
            case apacheFTPClient:
                this.ftpClient = new org.apache.commons.net.ftp.FTPClient();
                break;
        }
    }

    public boolean Connect(String host,int port) {
        switch (library) {
            case apacheFTPClient:
                boolean sucses = true;
                try {
                    this.ftpClient.connect(host, port);
                } catch (IOException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean Connect(String host) {
        switch (library) {
            case apacheFTPClient:
                return Connect(host,21);
        }
        return false;
    }

    public boolean Login(String login, String password) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return this.ftpClient.login(login, password);
        }
        return false;
    }

    public boolean LoginAnonymous(String email) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return this.ftpClient.login("anonymous",email);
        }
        return false;
    }

    public boolean LoginAnonymous() throws IOException {
        return LoginAnonymous("anonymous@anonymous.com");
    }

    public void setWorkingDirectory(String workingDirectory) throws IOException {
        this.workingDirectory = workingDirectory;
        this.ftpClient.changeWorkingDirectory(this.workingDirectory);
    }

    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    public FTPFile[] list(String path) throws IOException {
        switch (library) {
            case apacheFTPClient:
                this.ftpClient.listFiles(path);
        }
        return null;
    }

    public FTPFile[] list() throws IOException {
        switch (library) {
            case apacheFTPClient:
                return this.ftpClient.listFiles();
        }
        return null;
    }

    public boolean uploadFile(String localPath, String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                FileInputStream fis = new FileInputStream(localPath);
                boolean status = this.ftpClient.storeFile(remotePath, fis);
                fis.close();
                return status;
        }
        return false;
    }

    public boolean downloadFile(String localPath,String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                FileOutputStream fos = new FileOutputStream(localPath);
                boolean status = ftpClient.retrieveFile(remotePath, fos);
                fos.close();
                return status;
        }
        return false;
    }

    public boolean rmdir(String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.removeDirectory(remotePath);
        }
        return false;
    }

    public boolean mkdir(String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.makeDirectory(remotePath);
        }
        return false;
    }

    public boolean logout() throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.logout();
        }
        return false;
    }
}
