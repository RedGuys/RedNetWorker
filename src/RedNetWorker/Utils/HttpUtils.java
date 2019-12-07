package RedNetWorker.Utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {
    @NotNull
    @Contract(pure = true)
    public static String buildGet(Map<String,Object> args) {
        return ("?"+buildGet(args));

    }

    public static String buildPost(Map<String, Object> args) {
        StringBuilder stringBuilder = new StringBuilder();
        args.forEach((name, value) -> {
            try {
                stringBuilder.append(URLEncoder.encode(name, "UTF-8")).append('=').append(URLEncoder.encode((String) value,"UTF-8")).append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        String strResult = stringBuilder.toString();
        return !strResult.isEmpty()
                ? stringBuilder.substring(0, stringBuilder.length() - 1)
                : strResult;
    }
}
