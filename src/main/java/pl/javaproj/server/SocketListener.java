package pl.javaproj.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener  implements ServletContextListener {

	private ServerSocket socket;
	private Thread worker;
	
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			socket = new ServerSocket(9999);
			worker = new Thread(new Worker());
			worker.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class Worker implements Runnable
	{

		public void run() {
			while(socket != null)
			{
				try {
					Socket client = socket.accept();
					new Thread(new IRCConnection(client)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
