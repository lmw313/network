package kr.ac.sungkyul.network.chat;

import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer{
	private final static int PORT = 9101;
	static List<Writer> listWriters = new ArrayList<Writer>();
	
	public static void chatLog(String message) {
		System.out.println(message);
	}

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();

			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			chatLog("연결 기다림 " + hostAddress + ":" + PORT);

			while (true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread( socket, listWriters ).start();
				//new ChatServerThread(socket).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
