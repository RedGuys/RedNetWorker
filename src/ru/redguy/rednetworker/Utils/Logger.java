package ru.redguy.rednetworker.Utils;

import java.time.LocalDateTime;

public class Logger {
    public static void info(Object text) {
        System.out.println(LocalDateTime.now()+" [info] "+text);
    }
    public static void warning(Object text) {
        System.out.println(LocalDateTime.now() +" [warning] "+text);
    }
    public static void error(Object text) {
        System.out.println(LocalDateTime.now()+" [error] "+text);
    }
}
