package ru.redguy.rednetworker.utils;

@SuppressWarnings("unused")
public class File {
    public String name;
    public String path;
    public DataTime lastEditDate = new DataTime();
    public long size;
    public String server;
    public String owner;
    public String group;
    public boolean isDirectory;
    public boolean isLink;
    public boolean isFile;
}
