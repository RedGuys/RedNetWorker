package ru.redguy.rednetworker.clients.sftp;

import ru.redguy.rednetworker.utils.DataTime;

@SuppressWarnings("unused")
public class JschSFTPFile extends SFTPFile {
    public DataTime lastAccessDate = new DataTime();
}
