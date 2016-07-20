package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import kr.ac.sungkyul.thread.UpperCaseAlphabetRunnableimpl;

public class ChatClient {
	// private static final String SERVER_IP = "172.16.106.112";
	private static final String SERVER_IP = "172.16.106.112";
	private static final int SERVER_PORT = 9101;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			// 4. reader/writer 생성
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			pw.println("JOIN:" + nickname);
			pw.flush();

			// 6. ChatClientReceiveThread 시작
			Thread thread = new ChatClientReceiveThread(br);
			thread.start();

			// 7. 키보드 입력 처리
			while (true) {
				// System.out.print(">>");
				String input = scanner.nextLine();

				if ("QUIT".equals(input) == true) {
					// 8. quit 프로토콜 처리
					pw.println("QUIT");
					pw.flush();
					break;
				} else {
					// 9. 메시지 처리
					// System.out.println("메시지 전송 중 오류가 발생했습니다.");
					pw.println("MESSAGE:" + input);
					pw.flush();
				}
			}

		} catch (IOException ex) {
			chatLog("error:" + ex);
		} finally {
			// 10. 자원정리
			if (socket != null && socket.isClosed() == false) {
				try {
					if (scanner != null) {
						scanner.close();
					}
					if (br != null) {
						br.close();
					}
					if (pw != null) {
						pw.close();
					}
					if (socket != null && socket.isClosed() == false) {
						socket.close();
					}

				} catch (IOException e) {
					chatLog("Error : " + e);
				}
			}
		}
	}

	public static void chatLog(String message) {
		System.out.println(message);
	}
}
