import RedNetWorker.Clients.HttpClient.ApacheFluentAPI;
import RedNetWorker.Clients.HttpClient.HttpExceptions.*;
import RedNetWorker.Utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ApacheFluentAPITest {
    public static void main(String[] args) throws Exception {
        ApacheFluentAPI apacheFluentAPI = new ApacheFluentAPI();
        Map<String,Object> arg = new HashMap<>();
        arg.put("eng","hi");
        arg.put("rus","ку");
        arg.put("num","19");
        String result = apacheFluentAPI.postString("https://api.redguy.ru/tests/post/index.php",arg);
        if(result.equals("rus=ку-num=19-eng=hi-")) {
            Logger.info("post - ok!");
        } else {
            Logger.error("post - error");
            Logger.error("Returned: "+result);
            throw new Exception("Illegal result");
        }
        result = apacheFluentAPI.getString("https://api.redguy.ru/tests/get", arg);
        if(result.equals("rus=ку-num=19-eng=hi-")) {
            Logger.info("get - ok!");
        } else {
            Logger.error("get - error");
            Logger.error("Returned: "+result);
            throw new Exception("Illegal result");
        }
        File file = apacheFluentAPI.downloadFile("https://api.redguy.ru/tests/get", "ApacheFluentAPI.test", arg);
        if(new BufferedReader(new FileReader(file)).readLine().equals("rus=ку-num=19-eng=hi-")) {
            Logger.info("download - ok!");
        } else {
            Logger.error("download - error");
            Logger.error("Returned: "+result);
            throw new Exception("Illegal result");
        }
    }
}
