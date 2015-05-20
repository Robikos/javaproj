package pl.javaproj.model;

import pl.javaproj.model.User;
import java.io.*;
import java.net.*;

public final class IRCUser extends User {
    private Socket clientSocket;
    DataOutputStream outputStream;

    public void setSocket(Socket socket) throws IOException
    {
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientSocket = socket;
    }

    public void receiveMessage(String target, String source, String value) throws IOException
    {
        String message = ":" + source + " PRIVMSG " + target + " :" + value + "\r\n";
        outputStream.writeBytes(message);
    }

    public void receiveNotice(String target, String source, String value) throws IOException
    {
        String message = ":" + source + " NOTICE " + target + " :" + value + "\r\n";
        outputStream.writeBytes(message);
    }

    public void receiveTopic(String author, String channel, String newTopic) throws IOException
    {
        String message = ":" + author + " TOPIC " + channel + " :" + newTopic + "\r\n";
        outputStream.writeBytes(message);
    }

    public void receiveMode(String author, String channel, String modeChange) throws IOException
    {
        String message = ":" + author + " MODE " + channel + " :" + modeChange + "\r\n";
        outputStream.writeBytes(message);
    }

    public void receiveJoin(String channel, String who) throws IOException
    {
        String message = ":" + who + " JOIN " + channel + "\r\n";
        outputStream.writeBytes(message);
    }

    public void receiveQuit(String who, String message) throws IOException
    {
        String msg = ":" + who + " QUIT :" + message + "\r\n";
        outputStream.writeBytes(msg);
    }
};
