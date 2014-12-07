package Server;
/*COMPLETED AS A TEAM*/
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class MonsterServer {
	private static final int CURRENTPLAYERPOSITION = 1;
	private static final int TOTALSPACES = 4;
	private static final int SECOND = 2;
	private static final int FIRST = 1;
	private static final int PORTNO = 10997;
	private static final int MAXCOUNT = 10;
	public Socket[] player;
	public Thread[] playerThread;
	// Instead of having a different class of monster thread have the same
	// player thread
	// class with an if clause
	public Thread monster;
	public int playerCount;
	public int[] playerPos = new int[MAXCOUNT];
	ServerSocket playerListener;
	int level;

	public MonsterServer() {
		System.out.println("Server: Preparing system");
		try {
			System.out.println("Server: Starting Listener:\n");
			// Create ServerSocket to listen to the player
			playerListener = new ServerSocket(PORTNO);
			playerCount = FIRST;
			// Testing ServerSocket connection
			System.out.println("Server: Please start player file now.");
			// When the first player tries to connect
			// following socket and thread are connected
			// with the player
			player = new Socket[MAXCOUNT];
			playerThread = new Thread[MAXCOUNT];
			addPlayer(playerCount);
			for (int i = SECOND; i <= playerCount; i++) {
				addPlayer(i);
			}

			// Trigger the Game by starting the Threads
			System.out.println("Server: The Game Begins");
			monster = new PlayerThread(this, playerCount);
			monster.start();

		} catch (IOException io) {
			System.out.println("Server: Sorry: Connection Unsuccessful. "+ io.getMessage());
			System.exit(0);
		}
	}

	public static void main(String args[]) {
		MonsterServer ms = new MonsterServer();

	}

	public void addPlayer(int playerNumber) throws IOException,
			NullPointerException {
		// Add new players in the GAME. Attach with threads.
		System.out.println("Server: See player number: " + playerNumber);
		Socket play = playerListener.accept();
		player[playerNumber] = play;

		BufferedReader in = new BufferedReader(new InputStreamReader(player[playerNumber].getInputStream()));
		PrintWriter out = new PrintWriter(new BufferedOutputStream(player[playerNumber].getOutputStream()), true);

		// Handle first player
		if (playerNumber == FIRST) {
			out.println("p1");
			playerCount = Integer.parseInt(in.readLine());
			System.out.println("Server: Number of players: " + playerCount);
			// Get the level : Easy Medium Hard
			level = Integer.parseInt(in.readLine());
		} else
			out.println("pn");
		
		// Handle new position
		int remainingPlaces = (TOTALSPACES - playerNumber)
				+ CURRENTPLAYERPOSITION;
		out.println(remainingPlaces);
		for (int i = FIRST; i <= TOTALSPACES; i++) {
			boolean taken = false;
			for (int j = FIRST; j <= playerCount; j++) {
				if (playerPos[j] == i) {
					taken = true;
					break;
				}
			}
			if (taken == false) {
				out.println(i);
			}
		}
		playerPos[playerNumber] = Integer.parseInt(in.readLine());
		System.out.println("Server: The value of incoming position:"
				+ playerPos[playerNumber]);

		if (playerNumber != playerCount) {
			out.println("w");
			System.out.println(playerNumber);
		} else {
			out.println("last");
			System.out
					.println("Server: The last Player joined!" + playerNumber);
		}
		
		//Associate the appropriate Thread
		playerThread[playerNumber] = new PlayerThread(this, player[playerNumber],playerNumber, playerPos[playerNumber], playerCount);
		playerThread[playerNumber].start();
	}
}
