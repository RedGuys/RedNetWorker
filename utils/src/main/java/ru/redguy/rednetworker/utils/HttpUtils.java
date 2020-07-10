package ru.redguy.rednetworker.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {
    public static String buildGet(Map<String,Object> args) {
        if(args == null) {
            return "";
        } else {
            return ("?" + buildPost(args));
        }
    }

    public static String buildPost(Map<String, Object> args) {
        if (args == null) {
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            args.forEach((name, value) -> {
                try {
                    stringBuilder.append(URLEncoder.encode(name, "UTF-8")).append('=').append(URLEncoder.encode(String.valueOf(value), "UTF-8")).append('&');
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
}
