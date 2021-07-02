package ru.redguy.rednetworker.servers.tftp;

import ru.redguy.tftpserver.TFTPServer;

import java.net.SocketException;
import java.net.UnknownHostException;

public class JamieMZhang implements ITFTPServer {

    TFTPServer server;

    public JamieMZhang() {
        server = new TFTPServer();
    }

    @Override
    public void start() throws SocketException {
        server.start();
    }

    @Override
    public void start(int port) throws SocketException {
        server.start(port);
    }

    @Override
    public void start(String host, int port) throws SocketException, UnknownHostException {
        server.start(host, port);
    }

    @Override
    public void stop() {
        server.stop();
    }
}
