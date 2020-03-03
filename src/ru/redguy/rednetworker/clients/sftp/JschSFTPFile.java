package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.Utils.DataTime;

@SuppressWarnings("unused")
public class JschSFTPFile extends SFTPFile {
    public DataTime lastAccessDate = new DataTime();
}
