package pl.javaproj.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import pl.javaproj.model.Channel;
import pl.javaproj.model.User;
import pl.javaproj.service.ChannelServiceImpl;

public class Connection implements Runnable{
	private Socket clientSocket;
    DataOutputStream outputStream;
    DataInputStream inputStream;
    BufferedReader reader;
    User user;
    
    String ident;
    String realname;
    
    public Connection(Socket socket)
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
        user = new User();
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
						
						receiveIRC(":awesome-server 001 " + user.getLogin() + " :foo\r\n");
						receiveIRC(":awesome-server 002 " + user.getLogin() + " :bar\r\n");
						receiveIRC(":awesome-server 003 " + user.getLogin() + " :bazz\r\n");
						receiveIRC(":awesome-server 004 " + user.getLogin() + " :buzz\r\n");
						
						receiveIRC(":awesome-server 251 " + user.getLogin() + " :foobar\r\n");
						receiveIRC(":awesome-server 422 " + user.getLogin() + " :no motd for you\r\n");
						
						break;
					}
					
					case "NICK":
					{
						if (list.size() != 2)
						{
							clientSocket.close();
							return;
						}
						
						user.setLogin(list.get(1));
						receiveNick(user.getLogin() + "!" + ident + "@our-awesome-server", user.getLogin());
						
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
						ch.joinUser(user);
						for (User user : ch)
						{
							receiveJoin(list.get(1), user.getLogin() + "!" + ident + "@out-awesome-server");
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
							if (user != this.user)
							{
								receiveMessage(list.get(1), user.getLogin() + "!" + ident + "@out-awesome-server", list.get(2));
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
}
