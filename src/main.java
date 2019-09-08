import RedNetWorker.Clients.Enums.FTPLibrary;
import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Clients.FTPClient;
import RedNetWorker.Clients.HttpClient;
import RedNetWorker.Utils.Logger;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        Logger.info("   HTTP");
        HttpClient client = new HttpClient(HttpLibrary.JavaNet);
        Map<String,String> getArgs = new HashMap<String, String>();
        getArgs.put("userId","1");
        Map<String,String> postArgs = new HashMap<String, String>();
        postArgs.put("title", "foo");
        postArgs.put("body", "bar");
        postArgs.put("userId", "1");
        Map<String,String> fileArgs = new HashMap<String, String>();
        getArgs.put("_limit","10");
        try {
            Logger.info("Built-in Java solution");
            System.out.println(client.getString("https://jsonplaceholder.typicode.com/posts",getArgs));
            System.out.println(client.postString("https://jsonplaceholder.typicode.com/posts",postArgs));
            client.DownloadFile("https://jsonplaceholder.typicode.com/posts","tests/JavaNet.json",fileArgs);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        /*client = new HttpClient(HttpLibrary.apacheHttpClient);
        try {
            Logger.info("Apache HttpClient");
            System.out.println(client.getString("https://jsonplaceholder.typicode.com/posts",getArgs));//TODO: Fix Socket is closed
            System.out.println(client.postString("https://jsonplaceholder.typicode.com/posts",postArgs));//TODO: Fix Socket is closed
            client.DownloadFile("https://jsonplaceholder.typicode.com/posts","tests/apacheHttpClient.json",fileArgs);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }*/
        client = new HttpClient(HttpLibrary.apacheFluentAPI);
        try {
            Logger.info("Apache Fluent API");
            System.out.println(client.getString("https://jsonplaceholder.typicode.com/posts",getArgs));
            System.out.println(client.postString("https://jsonplaceholder.typicode.com/posts",postArgs));
            client.DownloadFile("https://jsonplaceholder.typicode.com/posts","tests/apacheFluentAPI.json",fileArgs);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Logger.info("   FTP");
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
