import ru.redguy.rednetworker.clients.http.OKHttp;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;
import ru.redguy.rednetworker.clients.http.exceptions.InputStreamException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InputStreamException {
        String s = null;
        try {
            s = new OKHttp().url("https://static.redguy.ru/rsbor/sbors/5.json").execute().getString();
        } catch (HttpProtocolException e) {
            System.out.println(e.getResponse().getString());
        }
        System.out.println(s);
    }
}
