package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickName;
	private Socket socket;
	List<Writer> listWriters;
	// new ChatServerTread( socket, listWriters ).start();

	public static void chatLog(String message) {
		System.out.println(message);
	}

	public ChatServerThread() {
	}

	// public ChatServerThread(Socket socket) {
	// this.socket = socket;
	// }

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	private void doJoin(String nickName, Writer writer) throws IOException {
		this.nickName = nickName;

		String data = nickName + "님이 참여하였습니다.";
		broadcast(data);

		/* writer pool에 저장 */
		addWriter(writer);

		// ack
		((PrintWriter) writer).println("join:ok");
		writer.flush();

	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void broadcast(String data) {

		synchronized (listWriters) {

			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(nickName + " : " + data);
				printWriter.flush();
			}

		}

	}

	private void doMessage(String message) {

		/* 잘 구현 해 보기 */
		broadcast(message);

	}

	private void doQuit(Writer writer) {
		removeWriter(writer);

		String data = nickName + "님이 퇴장 하였습니다.";
		broadcast(data);
	}

	private void removeWriter(Writer writer) {
		/* 잘 구현 해보기 */
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), 
					StandardCharsets.UTF_8),true);

			while (true) {
				String request = br.readLine();
				if (request == null) {
					chatLog("클라이언트로 부터 연결 끊김");
					break;
				}
				String[] tokens = request.split(":");

				if ("JOIN".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("MESSAGE".equals(tokens[0])) {
					doMessage(tokens[1]);

				} else if ("QUIT".equals(tokens[0])) {
					doQuit(pw);

				} else if (request == null) {
					ChatServer.chatLog("클라이언트로 부터 연결 끊김");
					doQuit(pw);
					break;

				} else {
					ChatServer.chatLog("에러:알수 없는 요청(" + tokens[0] + ")");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
