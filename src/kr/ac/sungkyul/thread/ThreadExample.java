package kr.ac.sungkyul.thread;

public class ThreadExample {

	public static void main(String[] args) {

		DigtiThread thread1 = new DigtiThread();
		DigtiThread thread2 = new DigtiThread();
		AlphabetThread thread3 = new AlphabetThread();
		Thread thread4 = new Thread ( new UpperCaseAlphabetRunnableimpl());
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}

}
