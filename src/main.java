import RedNetWorker.Clients.Enums.HttpLibrary;
import RedNetWorker.Clients.HttpClient;

import java.io.IOException;

public class main {
    public static void main(String[] args) {
        HttpClient client = new HttpClient(HttpLibrary.JavaNet);
        try {
            System.out.print(client.getString("https://google.com"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
