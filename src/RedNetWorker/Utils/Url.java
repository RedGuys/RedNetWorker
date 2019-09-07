package RedNetWorker.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class Url {
    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        params.forEach((name, value) -> {
            try {
                result.append(URLEncoder.encode(name, "UTF-8"));
                result.append('=');
                result.append(URLEncoder.encode(value, "UTF-8"));
                result.append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
