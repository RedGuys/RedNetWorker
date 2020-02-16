import ru.redguy.rednetworker.Utils.Logger;
import ru.redguy.rednetworker.clients.sftp.JschSFTPClient;
import ru.redguy.rednetworker.clients.sftp.exceptions.OpenConnectionException;
import ru.redguy.rednetworker.clients.sftp.exceptions.ServerMethodErrorException;

public class JschSFTPClientTest {
    public static void main(String[] args) throws OpenConnectionException, ServerMethodErrorException {
        JschSFTPClient jschSFTPClient = new JschSFTPClient();
        jschSFTPClient.connect(System.getenv("host"),Integer.parseInt(System.getenv("port")),System.getenv("user"),new java.io.File(System.getenv("keyPath")), System.getenv("known_hosts"));
        jschSFTPClient.cd("RNetTests");
        jschSFTPClient.ls();
    }
}
