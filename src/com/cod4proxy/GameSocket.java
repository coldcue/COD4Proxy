package com.cod4proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Level;

/**
 * This socket thread handles the game's packets.
 * 
 * @author ColdCue
 */
public class GameSocket extends Thread {
	private DatagramSocket socket;
	private int bufSize = 8;
	private SocketAddress remoteAddress = null;

	/**
	 * Creates a GameSocket thread and starts at the given port
	 * 
	 * @throws SocketException
	 * 
	 */
	public GameSocket() throws SocketException {
		socket = new DatagramSocket(Main.localAddress);
		bufSize = socket.getReceiveBufferSize();
		Main.logger.info("Starting GameSocket at port " + socket.getLocalPort() + " ...");
	}

	public void run() {
		Main.logger.info("GameSocket started!");
		byte[] buf = new byte[bufSize];
		while (true) {
			//Creates an empty packet
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				//Writes the data in the previously created packet
				socket.receive(packet);
				//Sends toward the received packet
				remoteAddress = packet.getSocketAddress();
				Main.serverSocket.send(packet.getData(), packet.getLength());
				Main.logger.fine("GAME: " + new String(packet.getData(), 0, packet.getLength()));
			} catch (IOException e) {
				Main.logger.log(Level.WARNING, e.getMessage());
				e.printStackTrace();
			}

		}
	}

	/**
	 * Sends the specified data to the game
	 * 
	 * @param data
	 * @param length
	 * @throws IOException
	 * 
	 */
	public synchronized void send(byte[] data, int length) throws IOException {
		socket.send(new DatagramPacket(data, length, remoteAddress));
	}
}
