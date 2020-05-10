import ru.redguy.rednetworker.clients.http.ApacheFluentAPI;

public class Fluent {
    public static void main(String[] args) throws Exception {
        ApacheFluentAPI fluentAPI = new ApacheFluentAPI();
        System.out.println(fluentAPI.getString("tests.redguy.ru/robots.txt"));
        System.out.println(fluentAPI.postString("https://tests.redguy.ru/robots.txt"));
    }
}
