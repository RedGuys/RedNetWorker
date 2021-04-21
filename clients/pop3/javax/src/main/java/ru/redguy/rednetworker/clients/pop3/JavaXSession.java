package ru.redguy.rednetworker.clients.pop3;

import ru.redguy.rednetworker.clients.pop3.exeptions.FolderException;

import javax.mail.*;

public class JavaXSession implements IPOP3Session {

    private Store store;

    public JavaXSession(Store store) {
        this.store = store;
    }

    @Override
    public IPOP3Message[] getMessagesInFolder(String folderName) throws FolderException, ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        Folder folder;
        try {
            folder = store.getFolder(folderName);
        } catch (MessagingException e) {
            throw new FolderException(e.getMessage(),e);
        }
        try {
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException e) {
            throw new FolderException(e.getMessage(),e);
        }

        Message[] messages;
        try {
            messages = folder.getMessages();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
        try {
            folder.close(false);
        } catch (MessagingException e) {
            throw new FolderException(e.getMessage(), e);
        }

        JavaXMessage[] javaXMessages =new JavaXMessage[messages.length];
        for (int i = 0; i < messages.length; i++) {
            JavaXMessage message = new JavaXMessage(messages[i]);
            javaXMessages[i] = message;
        }

        return javaXMessages;
    }

    @Override
    public void close() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            store.close();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }
}
