package ru.redguy.rednetworker.clients.ftp;

import ru.redguy.rednetworker.clients.ftp.enums.ApacheFTPFileType;

@SuppressWarnings("unused")
public class ApacheFTPFile extends FTPFile {
    public int hardLinkCount;
    public boolean isUnknown;
    public boolean isValid;
    public ApacheFTPFileType type;
}
