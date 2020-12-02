package ru.redguy.rednetworker.clients.pop3;

import ru.redguy.rednetworker.clients.pop3.enums.RecipientType;
import ru.redguy.rednetworker.utils.Header;

import javax.activation.DataHandler;
import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class JavaXMessage implements IPOP3Message {

    Message message;

    public JavaXMessage(Message message) {
        this.message = message;
    }

    @Override
    public IPOP3Address[] getFrom() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        Address[] origMessages;
        IPOP3Address[] resMessages;
        try {
            origMessages = message.getFrom();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }

        resMessages = new IPOP3Address[origMessages.length];

        for (int i = 0; i < origMessages.length; i++) {
            resMessages[i] = new JavaXAddress(origMessages[i]);
        }

        return resMessages;
    }

    @Override
    public void setFrom() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setFrom();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setFrom(IPOP3Address address) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setFrom((Address) address.getOriginal());
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void addFrom(IPOP3Address[] addresses) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        Address[] address = convertToOther((JavaXAddress[]) addresses);
        try {
            message.addFrom(address);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public IPOP3Address[] getRecipients(RecipientType type) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        Message.RecipientType recipientType = convertToOther(type);

        Address[] origAddresses;

        try {
            origAddresses = message.getRecipients(recipientType);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }

        return convertToMy(origAddresses);
    }

    @Override
    public void setRecipients(RecipientType type, IPOP3Address[] addresses) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setRecipients(convertToOther(type),convertToOther((JavaXAddress[]) addresses));
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void addRecipients(RecipientType type, IPOP3Address[] addresses) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.addRecipients(convertToOther(type),convertToOther((JavaXAddress[]) addresses));
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public String getSubject() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getSubject();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setSubject(String subject) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setSubject(subject);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public Date getSentDate() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getSentDate();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setSentDate(Date date) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setSentDate(date);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public Date getReceivedDate() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getReceivedDate();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public IPOP3Flags getFlags() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return new JavaXFlags(message.getFlags());
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setFlags(IPOP3Flags flag, boolean set) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setFlags((Flags) flag.getOriginal(),set);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public IPOP3Message reply(boolean replyToAll) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return new JavaXMessage(message.reply(replyToAll));
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void saveChanges() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.saveChanges();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public int getSize() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getSize();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public int getLineCount() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getLineCount();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public String getContentType() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getContentType();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public boolean isMimeType(String mimeType) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.isMimeType(mimeType);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public String getDisposition() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getDisposition();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setDisposition(String disposition) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setDisposition(disposition);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public String getDescription() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getDescription();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setDescription(String description) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setDescription(description);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public String getFileName() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getFileName();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setFileName(String filename) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setFileName(filename);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public InputStream getInputStream() throws IOException, ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getInputStream();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public DataHandler getDataHandler() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getDataHandler();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public Object getContent() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException, IOException {
        try {
            return message.getContent();
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setDataHandler(DataHandler dh) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setDataHandler(dh);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setContent(Object obj, String type) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setContent(obj, type);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setText(String text) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setText(text);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setContent(IPOP3Multipart mp) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setContent((Multipart) mp.getOriginal());
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void writeTo(OutputStream os) throws IOException, ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.writeTo(os);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public String[] getHeader(String header_name) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return message.getHeader(header_name);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void setHeader(String header_name, String header_value) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.setHeader(header_name, header_value);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void addHeader(String header_name, String header_value) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.addHeader(header_name, header_value);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public void removeHeader(String header_name) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            message.removeHeader(header_name);
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public Enumeration<Header> getAllHeaders() throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return convertToMy(message.getAllHeaders());
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public Enumeration<Header> getMatchingHeaders(String[] header_names) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return convertToMy(message.getMatchingHeaders(header_names));
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    @Override
    public Enumeration<Header> getNonMatchingHeaders(String[] header_names) throws ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException {
        try {
            return convertToMy(message.getNonMatchingHeaders(header_names));
        } catch (MessagingException e) {
            throw new ru.redguy.rednetworker.clients.pop3.exeptions.MessagingException(e.getMessage(),e);
        }
    }

    private Message.RecipientType convertToOther(RecipientType recipientType) {
        Message.RecipientType type;

        switch (recipientType) {
            case TO:
                type = Message.RecipientType.TO;
                break;
            case CarbonCopy:
                type = Message.RecipientType.CC;
                break;
            case BlindCarbonCopy:
                type = Message.RecipientType.BCC;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + recipientType);
        }

        return type;
    }

    private RecipientType convertToMy(Message.RecipientType recipientType) {
        RecipientType type = null;

        if (Message.RecipientType.TO.equals(recipientType)) {
            type = RecipientType.TO;
        } else if (Message.RecipientType.CC.equals(recipientType)) {
            type = RecipientType.CarbonCopy;
        } else if (Message.RecipientType.BCC.equals(recipientType)) {
            type = RecipientType.BlindCarbonCopy;
        }

        return type;
    }

    private Address[] convertToOther(JavaXAddress[] addresses) {
        Address[] res = new Address[addresses.length];

        for (int i = 0; i < addresses.length; i++) {
            res[i] = (Address) addresses[i].getOriginal();
        }

        return res;
    }

    private JavaXAddress[] convertToMy(Address[] addresses) {
        JavaXAddress[] res = new JavaXAddress[addresses.length];

        for (int i = 0; i < addresses.length; i++) {
            res[i] = new JavaXAddress(addresses[i]);
        }

        return res;
    }

    private Enumeration<Header> convertToMy(Enumeration<javax.mail.Header> headerEnumeration) {
        Vector<Header> headers = new Vector<>();
        while (headerEnumeration.hasMoreElements()) {
            javax.mail.Header header = headerEnumeration.nextElement();
            headers.add(new Header(header.getName(),header.getValue()));
        }
        return headers.elements();
    }
}
