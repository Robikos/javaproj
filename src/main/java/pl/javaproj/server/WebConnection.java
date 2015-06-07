package pl.javaproj.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import pl.javaproj.model.Channel;
import pl.javaproj.model.User;
import pl.javaproj.service.ChannelServiceImpl;

public class WebConnection extends TextWebSocketHandler{
	
	User user;
	String ident;
    String realname;
    public WebSocketSession session;
	
	public WebConnection()
	{
		user = new User();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception
	{
		this.session = session;
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
	{
		super.handleTextMessage(session, message);
		String request = message.getPayload();
		String response = generateResponse(request);
		
		System.out.println("Websocket Odebrano: "+response);
		session.sendMessage(new TextMessage(response));
	}
	
	public String generateResponse(String message) throws IOException
	{
		String response="";
		
		ArrayList<String> list = parseMessage(message);
		switch(list.get(0))
		{
			case "USER":
			{
				if (list.size() != 5)
				{
					response = "Size mismatches";
				}
				
				ident = list.get(1);
				realname = list.get(4);
				
				response = receiveIRC(":awesome-server 001 " + user.getLogin() + " :foo\r\n");
				response += receiveIRC(":awesome-server 002 " + user.getLogin() + " :bar\r\n");
				response += receiveIRC(":awesome-server 003 " + user.getLogin() + " :bazz\r\n");
				response += receiveIRC(":awesome-server 004 " + user.getLogin() + " :buzz\r\n");
				
				response += receiveIRC(":awesome-server 251 " + user.getLogin() + " :foobar\r\n");
				response += receiveIRC(":awesome-server 422 " + user.getLogin() + " :no motd for you\r\n");
				
				break;
			}
			
			case "NICK":
			{
				if (list.size() != 2)
				{
					response = "Size mismatches";
				}
				
				user.setLogin(list.get(1));
				response = receiveNick(user.getLogin() + "!" + ident + "@our-awesome-server", user.getLogin());
				
				break;
			}
			
			case "JOIN":
			{
				if (list.size() != 2)
				{
					response = "Size mismatches";
				}
				
				System.out.println("Channel debug: "+list.get(1));
				Channel ch = ChannelServiceImpl.getInstance().getChannelByName(list.get(1));
				ch.joinWebUser(this);
				System.out.println("Users count: "+ch.usersCount());
				response = "";
				for (WebConnection connection : ch.getWebConnections())
				{
					String res = receiveJoin(list.get(1), connection.user.getLogin() + "!" + ident + "@out-awesome-server");
					response += res;
					connection.session.sendMessage(new TextMessage(res));
				}
				
				break;
			}
			
			case "PRIVMSG":
			{
				if (list.size() != 3)
				{
					response = "Size mismatches";
				}
				
				Channel ch = ChannelServiceImpl.getInstance().getChannelByName(list.get(1));
				response = "";
				System.out.println("Users count privmsg: "+ch.usersCount());
				for (WebConnection connection : ch.getWebConnections())
				{
					if (connection.user != this.user)
					{
						String res = receiveMessage(list.get(1), connection.user.getLogin() + "!" + ident + "@out-awesome-server", list.get(2));
						response += res;
						connection.session.sendMessage(new TextMessage(res));
					}
				}
				
				break;
			}
		}
		return response;
	}
	
	public String receiveMessage(String target, String source, String value)
    {
        String message = ":" + source + " PRIVMSG " + target + " :" + value + "\r\n";
        return message;
    }

    public String receiveNotice(String target, String source, String value) 
    {
        String message = ":" + source + " NOTICE " + target + " :" + value + "\r\n";
        return message;
    }

    public String receiveTopic(String author, String channel, String newTopic) 
    {
        String message = ":" + author + " TOPIC " + channel + " :" + newTopic + "\r\n";
        return message;
    }

    public String receiveMode(String author, String channel, String modeChange) 
    {
        String message = ":" + author + " MODE " + channel + " :" + modeChange + "\r\n";
        return message;
    }

    public String receiveJoin(String channel, String who) 
    {
        String message = ":" + who + " JOIN " + channel + "\r\n";
        return message;
    }

    public String receiveQuit(String who, String message) 
    {
        String msg = ":" + who + " QUIT :" + message + "\r\n";
        return msg;
    }

	public String receiveNick(String who, String nick)  {
		String message = ":" + who + " NICK :" + nick + "\r\n";
		return message;
	}
	
	private String receiveIRC(String raw) {
		return raw;
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
