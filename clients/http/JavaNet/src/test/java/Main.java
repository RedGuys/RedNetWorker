import ru.redguy.rednetworker.clients.http.JavaNet;
import ru.redguy.rednetworker.clients.http.exceptions.HttpProtocolException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String s = null;
        try {
            s = new JavaNet().url("https://static.redguy.ru/rsbor/sbors/5.json").execute().getString();
        } catch (HttpProtocolException e) {
            System.out.println(e.getResponse());
        }
        System.out.println(s);
    }
}
