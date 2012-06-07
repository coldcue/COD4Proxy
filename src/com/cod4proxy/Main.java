package com.cod4proxy;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class Main {

	public static Logger logger;

	public static GameSocket gameSocket;
	public static ServerSocket serverSocket;

	public static final SocketAddress serverAddress = new InetSocketAddress("80.249.169.3", 28960);
	public static final SocketAddress localAddress = new InetSocketAddress("localhost", 28965);

	public static void main(String[] args) throws SocketException, InterruptedException {
		logger = Logger.getLogger("main");
		logger.info("Starting COD4 Proxy...");

		gameSocket = new GameSocket();
		gameSocket.start();
		serverSocket = new ServerSocket();
		serverSocket.start();

		gameSocket.join();

	}

}
