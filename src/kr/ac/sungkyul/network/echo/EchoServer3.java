package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * 다중처리 Echo Server
 * 
 * @author notebook
 *
 */
public class EchoServer3 {
	private static final int SERVER_PORT = 1995;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localHostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress, SERVER_PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[echo server] binding " + localHostAddress + ":" + SERVER_PORT);

			// accept: 연결 요청 대기
			while (true) {
				Socket socket = serverSocket.accept();

				EchoServer3ReceiveThread thread = new EchoServer3ReceiveThread(socket);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 서버 소켓 닫기
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}