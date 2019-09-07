import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Clients.HttpClient;
import RedNetWorker.Utils.Logger;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.URISyntaxException;

public class main {
    public static void main(String[] args) {
        /*HttpClient client = new HttpClient(HttpLibrary.JavaNet);
        try {
            System.out.print(client.getString("https://google.com"));
            client.DownloadFile("https://google.com","index.html");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }*/
        HttpClient client2 = new HttpClient(HttpLibrary.apacheHttpClient);
        try {
            System.out.print(client2.getString("https://google.com"));
            client2.DownloadFile("https://google.com","index2.html");
        } catch (IOException | URISyntaxException e) {
            System.out.print(e.getMessage());
            System.out.print(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
