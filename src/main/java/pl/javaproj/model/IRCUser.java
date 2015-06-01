package pl.javaproj.model;

import pl.javaproj.model.User;
import pl.javaproj.service.ChannelService;
import pl.javaproj.service.ChannelServiceImpl;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public final class IRCUser extends User implements Runnable{
    private Socket clientSocket;
    DataOutputStream outputStream;
    DataInputStream inputStream;
    BufferedReader reader;
    
    String ident;
    String realname;
    
    public IRCUser(Socket socket)
    {
    	try {
			setSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void setSocket(Socket socket) throws IOException
    {
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
        clientSocket = socket;
        reader = new BufferedReader(new InputStreamReader(inputStream));
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

	@Override
	public void receiveNick(String who, String nick) throws IOException {
		String message = ":" + who + " NICK :" + nick + "\r\n";
		outputStream.writeBytes(message);
	}
	
	private void receiveIRC(String raw) throws IOException {
		outputStream.writeBytes(raw);
	}
	
	public void run() {
		while(clientSocket != null)
		{
			try {
				String message = reader.readLine();
				System.out.println(message);

				if (message == null)
				{
					clientSocket.close();
					return;
				}
				
				ArrayList<String> list = parseMessage(message);
				switch(list.get(0))
				{
					case "USER":
					{
						if (list.size() != 5)
						{
							clientSocket.close();
							return;
						}
						
						ident = list.get(1);
						realname = list.get(4);
						
						receiveIRC(":awesome-server 001 " + login + " :foo\r\n");
						receiveIRC(":awesome-server 002 " + login + " :bar\r\n");
						receiveIRC(":awesome-server 003 " + login + " :bazz\r\n");
						receiveIRC(":awesome-server 004 " + login + " :buzz\r\n");
						
						receiveIRC(":awesome-server 251 " + login + " :foobar\r\n");
						receiveIRC(":awesome-server 422 " + login + " :no motd for you\r\n");
						
						break;
					}
					
					case "NICK":
					{
						if (list.size() != 2)
						{
							clientSocket.close();
							return;
						}
						
						login = list.get(1);
						receiveNick(login + "!" + ident + "@our-awesome-server", login);
						
						break;
					}
					
					case "JOIN":
					{
						if (list.size() != 2)
						{
							clientSocket.close();
							return;
						}
						
						Channel ch = ChannelServiceImpl.getInstance().getChannelByName(list.get(1));
						ch.joinUser(this);
						for (User user : ch)
						{
							user.receiveJoin(list.get(1), login + "!" + ident + "@out-awesome-server");
						}
						
						break;
					}
					
					case "PRIVMSG":
					{
						if (list.size() != 3)
						{
							clientSocket.close();
							return;
						}
						
						Channel ch = ChannelServiceImpl.getInstance().getChannelByName(list.get(1));
						for (User user : ch)
						{
							if (user != this)
							{
								user.receiveMessage(list.get(1), login + "!" + ident + "@out-awesome-server", list.get(2));
							}
						}
						
						break;
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<String> parseMessage(String message)
	{
		ArrayList<String> list;
		
		int colon = message.indexOf(':');
		if (colon == -1) {
			list = new ArrayList<String>(Arrays.asList(message.split(" ")));
		}
		else {
			String command = message.substring(0, colon);
			list = new ArrayList<String>(Arrays.asList(command.split(" ")));
			list.add(message.substring(colon+1));
		}
		return list;
	}

};
