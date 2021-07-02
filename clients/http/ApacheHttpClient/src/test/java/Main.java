import ru.redguy.rednetworker.clients.http.ApacheHttpClient;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String s = null;
        try {
            s = new ApacheHttpClient().url("https://static.redguy.ru/rsbor/sbors/5.json").execute().getString();
        } catch (HttpProtocolException e) {
            System.out.println(e.getResponse().getString());
        }
        System.out.println(s);
    }
}
