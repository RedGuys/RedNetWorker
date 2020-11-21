import ru.redguy.rednetworker.clients.http.ApacheHttpClient;
import ru.redguy.rednetworker.clients.http.IHttpClient;

public class Apache {
    public static void main(String[] args) throws Exception {
        IHttpClient client = new ApacheHttpClient();
        System.out.println(client.get("https://tests.redguy.ru/robots.txt").getString());
        System.out.println(client.post("https://tests.redguy.ru/robots.txt").getString());
    }
}
