package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.print(">> ");
				String host = scanner.nextLine();
				if ("quit".equals(host)) {
					System.out.println("종료되었습니다.");
					break;
				}
				InetAddress[] inetAddresses = InetAddress.getAllByName(host);
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}
			} catch (UnknownHostException e) {
				System.out.println("unknown host");
				e.printStackTrace();
			}
		}
		scanner.close();
	}
}
