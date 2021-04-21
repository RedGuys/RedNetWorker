package ru.redguy.rednetworker.clients.pop3;

import ru.redguy.rednetworker.clients.pop3.enums.RecipientType;
import ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException;
import ru.redguy.rednetworker.utils.Header;

import javax.activation.DataHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;

public interface IPOP3Message {
    IPOP3Address[] getFrom() throws MessagingException;

    void setFrom() throws MessagingException;
    void setFrom(IPOP3Address address) throws MessagingException;
    void addFrom(IPOP3Address[] addresses) throws MessagingException;

    IPOP3Address[] getRecipients(RecipientType type) throws MessagingException;

    void setRecipients(RecipientType type, IPOP3Address[] addresses) throws MessagingException;
    void addRecipients(RecipientType type, IPOP3Address[] addresses) throws MessagingException;

    String getSubject() throws MessagingException;

    void setSubject(String subject) throws MessagingException;

    Date getSentDate() throws MessagingException;

    void setSentDate(Date date) throws MessagingException;

    Date getReceivedDate() throws MessagingException;

    IPOP3Flags getFlags() throws MessagingException;

    void setFlags(IPOP3Flags flag, boolean set) throws MessagingException;

    IPOP3Message reply(boolean replyToAll) throws MessagingException;

    void saveChanges() throws MessagingException;

    int getSize() throws MessagingException;
    int getLineCount() throws MessagingException;

    String getContentType() throws MessagingException;

    boolean isMimeType(String mimeType) throws MessagingException;

    String getDisposition() throws MessagingException;

    void setDisposition(String disposition) throws MessagingException;

    String getDescription() throws MessagingException;

    void setDescription(String description) throws MessagingException;

    String getFileName() throws MessagingException;

    void setFileName(String filename) throws MessagingException;

    InputStream getInputStream() throws IOException, MessagingException;

    DataHandler getDataHandler() throws MessagingException;

    Object getContent() throws MessagingException, IOException;

    void setDataHandler(DataHandler dh) throws MessagingException;
    void setContent(Object obj, String type) throws MessagingException;
    void setText(String text) throws MessagingException;
    void setContent(IPOP3Multipart mp) throws MessagingException;
    void writeTo(OutputStream os) throws IOException, MessagingException;

    String[] getHeader(String header_name) throws MessagingException;

    void setHeader(String header_name, String header_value) throws MessagingException;
    void addHeader(String header_name, String header_value) throws MessagingException;
    void removeHeader(String header_name) throws MessagingException;

    Enumeration<Header> getAllHeaders() throws MessagingException;
    Enumeration<Header> getMatchingHeaders(String[] header_names) throws MessagingException;
    Enumeration<Header> getNonMatchingHeaders(String[] header_names) throws MessagingException;
}
