package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ChatClientReceiveThread extends Thread {
	private String nickName;
	private Socket socket;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

	public ChatClientReceiveThread() {
	}

	public ChatClientReceiveThread(BufferedReader br) {
		this.bufferedReader = br;
	}

	@Override
	public void run() {

		while (true) {
			try {
				String message = bufferedReader.readLine();

				if (message == null) {
					break;
				}
				System.out.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
