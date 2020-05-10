import ru.redguy.rednetworker.clients.http.ApacheHttpClient;
import ru.redguy.rednetworker.clients.http.IHttpClient;

public class Apache {
    public static void main(String[] args) throws Exception {
        IHttpClient client = new ApacheHttpClient();
        System.out.println(client.getString("https://tests.redguy.ru/robots.txt"));
        System.out.println(client.postString("https://tests.redguy.ru/robots.txt"));
    }
}
