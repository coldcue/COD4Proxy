package com.cod4proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;

/**
 * This socket thread handles the server's packets.
 * 
 * @author ColdCue
 * 
 */
public class ServerSocket extends Thread {
	private DatagramSocket socket;
	private int bufSize;

	/**
	 * Creates a ServerSocket thread and starts at the given
	 * port
	 * @see ServerSocket
	 * @throws SocketException
	 * 
	 */
	public ServerSocket() throws SocketException {
		socket = new DatagramSocket();
		socket.setTrafficClass(0x10);
		bufSize = socket.getReceiveBufferSize();
		Main.logger.info("Starting ServerSocket at port " + socket.getLocalPort() + " ...");
	}

	public void run() {
		// PrintWriter file = null;
		// try {
		// file = new PrintWriter(new FileWriter("server.txt"), true);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		Main.logger.info("ServerSocket started!");
		byte[] buf = new byte[bufSize];
		while (true) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				Main.gameSocket.send(packet.getData(), packet.getLength());
				// System.out.println("SERVER: " + new String(packet.getData(),
				// 0, packet.getLength()));
				// file.println(new String(packet.getData(), 0,
				// packet.getLength()));
			} catch (IOException e) {
				Main.logger.log(Level.WARNING, e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public synchronized void send(byte[] data, int length) throws IOException {

		socket.send(new DatagramPacket(data, length, Main.serverAddress));
	}
}
