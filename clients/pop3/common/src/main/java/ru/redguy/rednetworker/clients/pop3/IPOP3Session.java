package ru.redguy.rednetworker.clients.pop3;

import ru.redguy.rednetworker.clients.pop3.exeptions.FolderException;
import ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException;

public interface IPOP3Session {
    IPOP3Message[] getMessagesInFolder(String folderName) throws FolderException, MessagingException;

    void close() throws MessagingException;
}
