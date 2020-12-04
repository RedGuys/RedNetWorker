import ru.redguy.rednetworker.clients.tftp.ApacheTFTPClient;
import ru.redguy.rednetworker.clients.tftp.enums.TransferMode;
import ru.redguy.rednetworker.clients.tftp.exeptions.FileTransferException;
import ru.redguy.rednetworker.clients.tftp.exeptions.UnknownHostException;
import ru.redguy.rednetworker.utils.exceptions.OpenConnectionException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException, FileTransferException, OpenConnectionException {
        ApacheTFTPClient tftpClient = new ApacheTFTPClient();
        tftpClient.connect("localhost",1000);
        tftpClient.receiveFile("build.gradle", TransferMode.BINARY_MODE,"recive.txt");
        tftpClient.sendFile("builder.grad",TransferMode.BINARY_MODE,"recive.txt");
    }
}
