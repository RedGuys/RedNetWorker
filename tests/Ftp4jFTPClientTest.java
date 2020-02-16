import ru.redguy.rednetworker.Utils.Logger;
import ru.redguy.rednetworker.clients.ftp.FTPFile;
import ru.redguy.rednetworker.clients.ftp.Ftp4jFTPClient;

import java.io.File;


public class Ftp4jFTPClientTest {
    public static void main(String[] args) throws Exception {
        Ftp4jFTPClient ftp4jFTPClient = new Ftp4jFTPClient();
        ftp4jFTPClient.remoteRenameBlockFix = true;
        ftp4jFTPClient.connect("speedtest.tele2.net");
        ftp4jFTPClient.loginAnonymous();
        FTPFile[] files = ftp4jFTPClient.list();
        if(files.length == 18) {
            Logger.info("list - ok!");
        } else {
            Logger.error("list - error");
            throw new Exception("Illegal result");
        }
        ftp4jFTPClient.downloadFile("Ftp4jFTPClientTest.test","/100MB.zip");
        if(new File("Ftp4jFTPClientTest.test").length() == 104857600L)  {
            Logger.info("download - ok!");
        } else {
            Logger.error("download - error");
            throw new Exception("Illegal result");
        }
        ftp4jFTPClient.cd("upload");
        if(ftp4jFTPClient.getWorkingDirectory().equals("/upload")) {
            Logger.info("cd - ok!");
        } else {
            Logger.error("cd - error");
            throw new Exception("Illegal result");
        }
        ftp4jFTPClient.uploadFile("Ftp4jFTPClientTest.test","downloaded.zip");
        Logger.info("upload - ok!");
    }
}
