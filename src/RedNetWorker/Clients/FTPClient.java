package RedNetWorker.Clients;

import RedNetWorker.Clients.Enums.FTPLibrary;
import it.sauronsoftware.ftp4j.*;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.ArrayList;

public class FTPClient {
    //For apacheFTPClient
    private org.apache.commons.net.ftp.FTPClient ftpClient;
    //For apacheFTPClient
    private FTPLibrary library;
    //For ftp4j
    private it.sauronsoftware.ftp4j.FTPClient ftp4j;
    //For ftp4j
    private String workingDirectory = "/";
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

    public boolean Connect(String host,int port) throws FTPException, IOException, FTPIllegalReplyException {
        switch (library) {
            case apacheFTPClient:
                boolean sucses = true;
                try {
                    this.ftpClient.connect(host, port);
                } catch (IOException e) {
                    sucses = false;
                }
                return sucses;
            case ftp4jFTPClient:
                this.ftp4j.connect(host, port);
        }
        return false;
    }

    public boolean Connect(String host) throws FTPException, IOException, FTPIllegalReplyException {
        return Connect(host,21);
    }

    public boolean Login(String login, String password) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return this.ftpClient.login(login, password);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    this.ftp4j.login(login, password);
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
        }
        return false;
    }

    public boolean LoginAnonymous(String email) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return this.ftpClient.login("anonymous",email);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    this.ftp4j.login("anonymous", email);
                } catch (FTPException e) {
                    sucses = false;
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean LoginAnonymous() throws IOException {
        return LoginAnonymous("anonymous@anonymous.com");
    }

    public void setWorkingDirectory(String workingDirectory) throws IOException, FTPIllegalReplyException, FTPException {
        this.workingDirectory = workingDirectory;
        switch (library) {
            case apacheFTPClient:
                this.ftpClient.changeWorkingDirectory(this.workingDirectory);
                break;
            case ftp4jFTPClient:
                this.ftp4j.changeDirectory(this.workingDirectory);
        }
    }

    public String getWorkingDirectory() {
        return this.workingDirectory;
    }

    public ArrayList<FTPFile> list(String path) throws IOException, FTPAbortedException, FTPDataTransferException, FTPException, FTPListParseException, FTPIllegalReplyException {
        switch (library) {
            case apacheFTPClient:
                ArrayList<FTPFile> files = new ArrayList<FTPFile>();
                for (FTPFile ftpFile : this.ftpClient.listFiles(path)) {
                    files.add(ftpFile);
                }
                return files;
            case ftp4jFTPClient:
                files = new ArrayList<FTPFile>();
                for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.ftp4j.list(path)) {
                    FTPFile file = new FTPFile();
                    file.setLink(ftpFile.getLink());
                    file.setName(ftpFile.getName());
                    file.setSize(ftpFile.getSize());
                    file.setType(ftpFile.getType());
                    files.add(file);
                }
                return files;
        }
        return null;
    }

    public ArrayList<FTPFile> list() throws IOException, FTPAbortedException, FTPDataTransferException, FTPException, FTPListParseException, FTPIllegalReplyException {
        switch (library) {
            case apacheFTPClient:
                ArrayList<FTPFile> files = new ArrayList<FTPFile>();
                for (FTPFile ftpFile : this.ftpClient.listFiles()) {
                    files.add(ftpFile);
                }
                return files;
            case ftp4jFTPClient:
                files = new ArrayList<FTPFile>();
                for (it.sauronsoftware.ftp4j.FTPFile ftpFile : this.ftp4j.list()) {
                    FTPFile file = new FTPFile();
                    file.setLink(ftpFile.getLink());
                    file.setName(ftpFile.getName());
                    file.setSize(ftpFile.getSize());
                    file.setType(ftpFile.getType());
                    files.add(file);
                }
                return files;
        }
        return null;
    }

    public boolean uploadFile(String localPath, String remotePath) {
        switch (library) {
            case apacheFTPClient:
                FileInputStream fis = null;
                boolean status = true;
                try {
                    fis = new FileInputStream(localPath);
                    status = this.ftpClient.storeFile(remotePath, fis);
                    fis.close();
                } catch (FileNotFoundException e) {
                    status = false;
                } catch (IOException e) {
                    status = false;
                }
                return status;
            case ftp4jFTPClient:
                status = true;
                try {
                    this.ftp4j.upload(new File(localPath));
                    this.ftp4j.rename(this.workingDirectory+new File(localPath).getName(),remotePath);
                } catch (IOException e) {
                    status = false;
                } catch (FTPIllegalReplyException e) {
                    status = false;
                } catch (FTPException e) {
                    status = false;
                } catch (FTPDataTransferException e) {
                    status = false;
                } catch (FTPAbortedException e) {
                    status = false;
                }
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
            case ftp4jFTPClient:
                status = true;
                try {
                    this.ftp4j.download(remotePath,new File(localPath));
                } catch (FTPIllegalReplyException e) {
                    status = false;
                } catch (FTPException e) {
                    status = false;
                } catch (FTPDataTransferException e) {
                    status = false;
                } catch (FTPAbortedException e) {
                    status = false;
                }
                return status;
        }
        return false;
    }

    public boolean rmdir(String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.removeDirectory(remotePath);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    ftp4j.deleteDirectory(remotePath);
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean mkdir(String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.makeDirectory(remotePath);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    ftp4j.createDirectory(remotePath);
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean delete(String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.deleteFile(remotePath);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    ftp4j.deleteFile(remotePath);
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean cd(String remotePath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.changeWorkingDirectory(remotePath);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    ftp4j.changeDirectory(remotePath);
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean logout() throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.logout();
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    ftp4j.logout();
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }

    public boolean rename(String oldPath, String newPath) throws IOException {
        switch (library) {
            case apacheFTPClient:
                return ftpClient.rename(oldPath, newPath);
            case ftp4jFTPClient:
                boolean sucses = true;
                try {
                    ftp4j.rename(oldPath, newPath);
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                }
                return sucses;
        }
        return null;
    }

    public boolean disconnect() {
        switch (library) {
            case apacheFTPClient:
                boolean sucses = true;
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    sucses = false;
                }
                return sucses;
            case ftp4jFTPClient:
                sucses = true;
                try {
                    ftp4j.disconnect(true);
                } catch (IOException e) {
                    sucses = false;
                } catch (FTPException e) {
                    sucses = false;
                } catch (FTPIllegalReplyException e) {
                    sucses = false;
                }
                return sucses;
        }
        return false;
    }
}
