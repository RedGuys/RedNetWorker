package ru.redguy.rednetworker.servers.tftp;

import java.net.SocketException;
import java.net.UnknownHostException;

public interface ITFTPServer {
    public void start() throws SocketException;
    public void start(int port) throws SocketException;
    public void start(String host, int port) throws SocketException, UnknownHostException;
    public void stop();
}
