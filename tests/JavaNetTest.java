import ru.redguy.rednetworker.clients.http.ApacheHttpClient;
import ru.redguy.rednetworker.clients.http.JavaNet;
import ru.redguy.rednetworker.Utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class JavaNetTest {
    public static void main(String[] args) throws Exception {
        JavaNet javaNet = new JavaNet();
        ApacheHttpClient apacheHttpClient = new ApacheHttpClient();
        Map<String,Object> arg = new HashMap<>();
        arg.put("eng","hi");
        arg.put("rus","ку");
        arg.put("num","19");
        String result;
        result = javaNet.postString("https://api.redguy.ru/tests/post/",arg);
        if(result.equals("rus=ку-num=19-eng=hi-")) {
            Logger.info("post - ok!");
        } else {
            Logger.error("post - error");
            Logger.error("Returned: "+result);
            throw new Exception("Illegal result");
        }
        result = javaNet.getString("https://api.redguy.ru/tests/get/", arg);
        if(result.equals("rus=ку-num=19-eng=hi-")) {
            Logger.info("get - ok!");
        } else {
            Logger.error("get - error");
            Logger.error("Returned: "+result);
            throw new Exception("Illegal result");
        }
        File file = javaNet.downloadFile("https://api.redguy.ru/tests/get/", "JavaNet.test", arg);
        if(new BufferedReader(new FileReader(file)).readLine().equals("rus=ку-num=19-eng=hi-")) {
            Logger.info("download - ok!");
        } else {
            Logger.error("download - error");
            throw new Exception("Illegal result");
        }
    }
}
