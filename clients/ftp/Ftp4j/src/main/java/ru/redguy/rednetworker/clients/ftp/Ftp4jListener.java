package ru.redguy.rednetworker.clients.ftp;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class Ftp4jListener implements FTPDataTransferListener {
    public boolean finish = false;

    @Override
    public void started() {
        finish = false;
    }

    @Override
    public void transferred(int i) {

    }

    @Override
    public void completed() {
        finish = true;
    }

    @Override
    public void aborted() {
        finish = true;
    }

    @Override
    public void failed() {
        finish = true;
    }
}
