package ru.redguy.rednetworker.utils;

public class Protocols {
    public static String formatUrlString(String url, String protocol) {
        if(url.startsWith(protocol+"://")) {
            return url;
        }
        if(url.startsWith(protocol+"s://")) {
            return url;
        }
        return protocol+"://"+url;
    }
}
