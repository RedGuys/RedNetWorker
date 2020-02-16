import ru.redguy.rednetworker.Utils.Logger;
import ru.redguy.rednetworker.clients.ftp.ApacheFTPClient;
import ru.redguy.rednetworker.clients.ftp.FTPFile;

import java.io.File;

public class ApacheFTPClientTest {
    public static void main(String[] args) throws Exception {
        ApacheFTPClient apacheFTPClient = new ApacheFTPClient();
        apacheFTPClient.connect("speedtest.tele2.net");
        apacheFTPClient.loginAnonymous();
        FTPFile[] files = apacheFTPClient.list();
        if(files.length == 19) {
            Logger.info("list - ok!");
        } else {
            Logger.error("list - error");
            throw new Exception("Illegal result");
        }
        apacheFTPClient.downloadFile("ApacheFTPClientTest.test","/100MB.zip");
        if(new File("ApacheFTPClientTest.test").length() == 104857600L)  {
            Logger.info("download - ok!");
        } else {
            Logger.error("download - error");
            throw new Exception("Illegal result");
        }
        apacheFTPClient.cd("upload");
        if(apacheFTPClient.getWorkingDirectory().equals("/upload")) {
            Logger.info("cd - ok!");
        } else {
            Logger.error("cd - error");
            throw new Exception("Illegal result");
        }
        apacheFTPClient.uploadFile("ApacheFTPClientTest.test","downloaded.zip");
        Logger.info("upload - ok!");
    }
}
