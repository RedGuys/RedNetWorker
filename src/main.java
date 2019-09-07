import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Clients.HttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class main {
    public static void main(String[] args) {
        HttpClient client = new HttpClient(HttpLibrary.JavaNet);
        try {
            System.out.print(client.getString("https://google.com"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
