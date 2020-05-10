import ru.redguy.rednetworker.clients.http.ApacheFluentAPI;
import ru.redguy.rednetworker.clients.http.IHttpClient;

public class Fluent {
    public static void main(String[] args) throws Exception {
        IHttpClient client = new ApacheFluentAPI();
        System.out.println(client.getString("tests.redguy.ru/robots.txt"));
        System.out.println(client.postString("https://tests.redguy.ru/robots.txt"));
    }
}
