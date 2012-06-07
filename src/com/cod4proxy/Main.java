package com.cod4proxy;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * The main thread
 * 
 * @author ColdCue
 * 
 */
public class Main {

	/**
	 * The global logger
	 */
	public static Logger logger;

	public static GameSocket gameSocket;
	public static ServerSocket serverSocket;

	/**
	 * The {java.net.SocketAddress} for the server
	 */
	public static final SocketAddress serverAddress = new InetSocketAddress("80.249.169.3", 28960);
	/**
	 * The {java.net.SocketAddress} for the game (localhost)
	 */
	public static final SocketAddress localAddress = new InetSocketAddress("localhost", 28965);

	/**
	 * Main thread class
	 * 
	 * @param args
	 * @throws SocketException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SocketException, InterruptedException {
		logger = Logger.getLogger("main");
		logger.info("Starting COD4 Proxy...");

		gameSocket = new GameSocket();
		gameSocket.start();
		serverSocket = new ServerSocket();
		serverSocket.start();

		// Forces the main thread to wait for the gamesocket
		gameSocket.join();

	}

}
