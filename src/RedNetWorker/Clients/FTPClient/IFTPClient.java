package RedNetWorker.Clients.FTPClient;

import RedNetWorker.Clients.FTPClient.FTPExceptions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface IFTPClient {
    public void Connect(String host,int port) throws OpenConnectionException;

    public void Connect(String host) throws OpenConnectionException;

    public void Login(String login, String password) throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    public void LoginAnonymous(String email) throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    public void LoginAnonymous() throws AuthorizationException, ConnectionException, UnknownServerErrorException;

    public void setWorkingDirectory(String workingDirectory) throws ConnectionException, AuthorizationException, UnknownServerErrorException;

    public String getWorkingDirectory() throws ConnectionException;

    public ArrayList<FTPFile> list(String path) throws ConnectionException, AbortedException, UnknownServerErrorException;

    public ArrayList<FTPFile> list() throws ConnectionException, AbortedException, UnknownServerErrorException;

    public void uploadFile(String localPath, String remotePath) throws ConnectionException, UnknownServerErrorException, AbortedException, FileNotFoundException;

    public void downloadFile(String localPath,String remotePath) throws FTPFileNotFoundException, ConnectionException, UnknownServerErrorException, AbortedException;

    public void rmdir(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void mkdir(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void delete(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void cd(String remotePath) throws ConnectionException, UnknownServerErrorException;

    public void logout() throws ConnectionException, UnknownServerErrorException;

    public void rename(String oldPath, String newPath) throws ConnectionException, UnknownServerErrorException;

    public void disconnect() throws ConnectionException, UnknownServerErrorException;
}
