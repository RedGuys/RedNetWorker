package RedNetWorker.Utils;

import java.time.LocalDateTime;

public class Logger {
    public static void info(String text) {
        System.out.println(LocalDateTime.now()+" [info] "+text);
    }
    public static void warning(String text) {
        System.out.println(LocalDateTime.now() +" [warning] "+text);
    }
    public static void error(String text) {
        System.out.println(LocalDateTime.now()+" [error] "+text);
    }
}
