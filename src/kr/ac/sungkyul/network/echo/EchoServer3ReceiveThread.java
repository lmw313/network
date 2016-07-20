package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer3ReceiveThread extends Thread {

	private Socket socket;

	public void consoleLog(String message) {
		System.out.println("[echo server thread#" + getId() + "]" + message);
	}

	public EchoServer3ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	public EchoServer3ReceiveThread() {
		this.socket = socket;
	}

	@Override
	public void run() {
		// 연결
		InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
		int remoteHostPort = remoteAddress.getPort();
		// System.out.println("[echo server] 연결 from " + remoteHostAddress + ":"
		// + remoteHostPort);
		consoleLog("연결 from " + remoteHostAddress + ":" + remoteHostPort);

		try {
			// IOStream 받아오기

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utr-8"), true);

			while (true) {
				// 데이터 읽기

				String data = br.readLine();
				if (data == null) {
					// 클라이언트로 부터 정상 종료
					// System.out.println("[echo server] closed by client");
					consoleLog("closed by client");
					return;
				}

				// 출력

				// System.out.println("[echo server] received :" + data);
				consoleLog("received :" + data);

				// 데이터 쓰기(echo)

				pw.print(data);
			}
		} catch (SocketException e) {
			// System.out.println("[echo server] 비정상적으로 클라이언트가 연결을 끊었습니다." + e);
			consoleLog("비정상적으로 클라이언트가 연결을 끊었습니다." + e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 데이터 통신 소켓 닫기
			if (socket != null && socket.isClosed() == false) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
