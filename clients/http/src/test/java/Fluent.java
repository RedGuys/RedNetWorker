import ru.redguy.rednetworker.clients.http.ApacheFluentAPI;
import ru.redguy.rednetworker.clients.http.IHttpClient;

public class Fluent {
    public static void main(String[] args) throws Exception {
        IHttpClient client = new ApacheFluentAPI();
        System.out.println(client.get("tests.redguy.ru/robots.txt").getString());
        System.out.println(client.post("https://tests.redguy.ru/robots.txt").getString());
    }
}
