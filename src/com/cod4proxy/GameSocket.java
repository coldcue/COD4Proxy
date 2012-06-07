package com.cod4proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Level;

public class GameSocket extends Thread {
	private DatagramSocket socket;
	private int bufSize = 8;
	private SocketAddress remoteAddress = null;

	public GameSocket() throws SocketException {
		socket = new DatagramSocket(Main.localAddress);
		bufSize = socket.getReceiveBufferSize();
		Main.logger.info("Starting GameSocket at port " + socket.getLocalPort() + " ...");
	}

	public void run() {
//		PrintWriter file = null;
//		try {
//			file = new PrintWriter(new FileWriter("game.txt"), true);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		Main.logger.info("GameSocket started!");
		byte[] buf = new byte[bufSize];
		while (true) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				remoteAddress = packet.getSocketAddress();
				Main.serverSocket.send(packet.getData(), packet.getLength());
				//System.out.println("GAME: " + new String(packet.getData(), 0, packet.getLength()));
				//file.println(new String(packet.getData(), 0, packet.getLength()));
			} catch (IOException e) {
				Main.logger.log(Level.WARNING, e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public synchronized void send(byte[] data, int length) throws IOException {
		socket.send(new DatagramPacket(data, length, remoteAddress));
	}
}
