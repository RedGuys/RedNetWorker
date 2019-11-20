import RedNetWorker.Clients.Enums.FTPLibrary;
import RedNetWorker.Clients.FTPClient.FTPClient;
import RedNetWorker.Utils.Logger;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;

public class FTPTest {
    public static void main(String[] args) {
        Logger.info("apacheFTPClient");
        FTPClient ftpClient = new FTPClient(FTPLibrary.apacheFTPClient);
        try {
            ftpClient.Connect("speedtest.tele2.net");
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
        try {
            ftpClient.LoginAnonymous();
            Logger.info("Downloading");
            if(ftpClient.downloadFile("tests/apacheFTPClient.zip","10MB.zip")) {
                Logger.info("Uploading");
                ftpClient.uploadFile("tests/apacheFTPClient.zip","/upload/apacheFTPClient.zip");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.info("ftp4jLibrary");
        ftpClient = new FTPClient(FTPLibrary.ftp4jFTPClient);
        try {
            ftpClient.Connect("speedtest.tele2.net");
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
        try {
            ftpClient.LoginAnonymous();
            Logger.info("Downloading");
            if(ftpClient.downloadFile("tests/ftp4jFTPClient.zip","10MB.zip")) {
                Logger.info("Uploading");
                ftpClient.uploadFile("tests/ftp4jFTPClient.zip","/upload/ftp4jFTPClient.zip");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
