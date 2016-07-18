package kr.ac.sungkyul.network.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
private final static int SERVER_PORT = 2003;
	public static void main(String[] args) {

		try {
			// 1. 서버 소켓 생성
			ServerSocket serverSocket = new ServerSocket();

			// 2. 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, SERVER_PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[server] bind : " + serverAddress + " : " + SERVER_PORT);
			
			//3. accept 클라이언트로 부터 연결(요청) 대기
			Socket socket = serverSocket.accept(); // blocing
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			System.out.println("[server] accept(연결 성공) from " + remoteAddress.getAddress().getHostAddress() + " : " + remoteAddress.getPort());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
