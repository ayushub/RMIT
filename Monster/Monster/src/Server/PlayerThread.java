package Server;
/*COMPLETED BY AYUSH S3391854*/
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class PlayerThread extends Thread {
	private static final int MONNSTER = 5;

	private static final int FOURTHPLAYER = 4;

	private static final int THIRDPLAYER = 3;

	private static final int SECONDPLAYER = 2;

	private static final int INVALIDPOSITION = 1000;

	private static final int MAXQSIZE = 1000;

	private static final int INCREMENT = 1;

	private static final int DECREMENT = 1;

	private static final int LEFT = 40;

	private static final int DOWN = 30;

	private static final int RIGHT = 20;

	private static final int UP = 10;

	private static final int YMINDEADZONE = 2;

	private static final int YMAXDEADZONE = 7;

	private static final int XMAXDEADZONE = 7;

	private static final int XMINDEADZONE = 2;

	private static final int FIRSTPLAYER = 1;

	private static final int CURRENTPLAYER = 1;

	private static final int NIL = 0;

	private static final int MONSTERR = 0;

	private static final int YMID = 4;

	private static final int XMID = 4;

	private static final int YMAX = 8;

	private static final int XMAX = 8;

	private static final int YMIN = 0;

	private static final int XMIN = 0;

	private static final int HARD = 100;

	private static final int EASY = 250;

	private static final int MEDIUM = 500;

	private static final int XCOORD = 0;

	private static final int YCOORD = 1;

	public Socket soc;
	public int player, monster, newX, newY, players;
	public boolean isPlayer;
	public static int[][] positions = new int[6][2];
	public static int[][] oldPositions = new int[6][2];
	public static int connectedPlayers, deadCount;
	BufferedReader in, brin;
	PrintWriter out, brout;
	// Monster Required Variables
	public int[][] mapArray = new int[10][10];
	// Mode of play: Easy MEDIUM Difficult. Default: MEDIUM
	public int mode = MEDIUM;
	public MonsterServer mon;

	public PlayerThread(MonsterServer m, Socket s, int num, int pos, int pcount) throws IOException {
		System.out.println("Hello! from the thread! " + pos + " " + pcount);
		this.soc = s;
		this.player = num;
		this.players = pcount;
		this.mon = m;
		switch (pos) {
		case 1:
			positions[this.player][XCOORD] = XMIN;
			positions[this.player][YCOORD] = YMIN;
			oldPositions[this.player][XCOORD] = XMIN;
			oldPositions[this.player][YCOORD] = YMIN;
			connectedPlayers++;
			break;
		case 2:
			positions[this.player][XCOORD] = XMAX;
			positions[this.player][YCOORD] = YMIN;
			oldPositions[this.player][XCOORD] = XMAX;
			oldPositions[this.player][YCOORD] = XMIN;
			connectedPlayers++;

			break;
		case 3:
			positions[this.player][XCOORD] = XMIN;
			positions[this.player][YCOORD] = YMAX;
			oldPositions[this.player][XCOORD] = XMIN;
			oldPositions[this.player][YCOORD] = YMAX;
			connectedPlayers++;
			break;
		case 4:
			positions[this.player][XCOORD] = XMAX;
			positions[this.player][YCOORD] = YMAX;
			oldPositions[player][XCOORD] = XMAX;
			oldPositions[player][YCOORD] = YMAX;
			connectedPlayers++;
			break;
		}

		positions[MONSTERR][XCOORD] = XMID;
		positions[MONSTERR][YCOORD] = YMID;
		oldPositions[MONSTERR][XCOORD] = XMID;
		oldPositions[MONSTERR][YCOORD] = YMID;
		deadCount = NIL;
		this.isPlayer = true;

		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		out = new PrintWriter(new BufferedOutputStream(soc.getOutputStream()), true);
		System.out.println("Thread: End of Constructor for player: " + player);
	}

	public PlayerThread(MonsterServer m, int pcount) throws IOException {
		this.mon = m;
		this.monster = MONSTERR;
		this.players = pcount;
		positions[this.monster][XCOORD] = XMID;
		positions[this.monster][YCOORD] = YMID;
		oldPositions[this.monster][XCOORD] = XMID;
		oldPositions[this.monster][YCOORD] = YMID;
		deadCount = NIL;
		this.isPlayer = false;
		if (mon.level == 2)
			mode = EASY;
		else if (mon.level == 3)
			mode = HARD;
		refreshGrid();
	}

	public void run() {

		if (isPlayer) {
			// Player Code here
			//Wait for all players to join
			while (connectedPlayers != players) {
				System.out.println("Thread: Waiting for players @ player"+ player);
			}

			// Communicate the other players positions
			initializePlayerPositions();

			loadPlayersPositions();
			while (true) {
				System.out.println("Thread: Loaded positions");
				if (!isDead(player)) {
					getRequest();
					System.out.println("Thread: Got request");
					// move checks for isEmpty isValid and notifies change
					move();
					System.out.println("Thread: moving");
				}
				// Receiving notifications of loses or wins
				if (hasWon()) {
					out.println("3-WON");
					break;
				}
			}
			try {
				in.close();
				out.close();
				soc.close();

			} catch (IOException e) {
				System.exit(NIL);
			}

		} else {
			// Monster Code here
			while (deadCount < (players - CURRENTPLAYER)) {

				refreshGrid();
				move();
				if (reachedPlayerYet(positions[monster][XCOORD],
						positions[monster][YCOORD])) {
					atePlayer();
				}

				System.out.println("Monster: " + positions[monster][XCOORD]	+ "-" + positions[monster][YCOORD]);
			}
		}
	}

	// Player Required Methods
	public void initializePlayerPositions() {

		out.println(players);
		out.println(player);		
		for (int i = MONSTERR; i <= players; i++) {
			out.println("1-" + positions[i][XCOORD] + "-" + positions[i][YCOORD]);
		}
	}

	public boolean hasWon() {
		if (isDead(player)) {
			//Inform that the player LOST
			out.println("9");
			return false;
		}
		if (deadCount == (players - CURRENTPLAYER)) {
			return true;
		}
		return false;
	}

	public boolean isDead(int i) {
		if (positions[i][XCOORD] == XMINDEADZONE || positions[i][XCOORD] == XMAXDEADZONE) {
			if (positions[i][YCOORD] == YMINDEADZONE || positions[i][YCOORD] == YMAXDEADZONE) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty(int x, int y) {
		for (int i = FIRSTPLAYER; i <= players; i++) {
			if (i != player) {
				if (positions[i][XCOORD] == x)
					if (positions[i][YCOORD] == y)
						return false;
			}

		}
		// Not Checking for the monster
		return true;
	}

	public void getRequest() {
		try {
			String message;
			message = in.readLine();
			System.out.println("Message from Client: " + message);
			if (isDead(player)) {
				out.println("9");
				return;
			}
			StringTokenizer t = new StringTokenizer(message, "-");
			String token = t.nextToken();
			if (token.equals("1")) {
				this.newX = Integer.parseInt(t.nextToken());
				this.newY = Integer.parseInt(t.nextToken());
			}
//			else if (token.equals("4")) {
//				checkOthersPositions();
//				out.println("6");
//			}
		} catch (IOException e) {
			System.err.println("Error receiving Request");
		}
	}

	public void broadcast(String str) {
		try {
			for (int i = FIRSTPLAYER; i <= players; i++) {
				PrintWriter brout = new PrintWriter(new BufferedOutputStream(mon.player[i].getOutputStream()), true);
				brout.println(str);
			}
		} catch (IOException e) {
			System.out.println("Error: Error in Broadcast Output\n" + e.getMessage());
		}
	}

	public synchronized void move() {
		if (isPlayer) {
			if (isEmpty(newX, newY) && isValid(newX, newY)) {
				positions[player][XCOORD] = newX;
				positions[player][YCOORD] = newY;
				broadcast("2-" + player + "-" + positions[player][XCOORD] + "-"	+ positions[player][YCOORD]);
			}
		} else {
			//If its for monster's thread 
			int direction = calculateShortestPath();
			try {
				Thread.sleep(mode);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			switch (direction) {
			case UP:
				positions[monster][YCOORD] -= DECREMENT;
				break;
			case RIGHT:
				positions[monster][XCOORD] += INCREMENT;
				break;
			case DOWN:
				positions[monster][YCOORD] += INCREMENT;
				break;
			case LEFT:
				positions[monster][XCOORD] -= DECREMENT;
				break;
			}
			broadcast("2-" + monster + "-" + positions[monster][XCOORD] + "-"+ positions[monster][YCOORD]);
		}
	}

	public void loadPlayersPositions() {
		for (int i = monster; i <= players; i++) {
			oldPositions[i][XCOORD] = positions[i][XCOORD];
			oldPositions[i][YCOORD] = positions[i][YCOORD];
		}
	}

	public void checkOthersPositions() {
		for (int i = monster; i <= players; i++) {
			if (oldPositions[i][YCOORD] != positions[i][YCOORD]	|| oldPositions[i][XCOORD] != positions[i][XCOORD]) {
				out.println("2-" + i + "-" + positions[i][XCOORD] + "-"	+ positions[i][YCOORD]);
			}
		}
		// End of changed positions. Now load them.
		loadPlayersPositions();
	}

	// Monster Required Methods
	public boolean isValid(int x, int y) {
		if (x >= XMIN && x <= XMAX) {
			if (y >= YMIN && y <= YMAX){
				return true;
			}
		}
		return false;
	}

	public int calculateShortestPath() {
		int f = NIL, l = NIL, dir;
		// Direction in general to be random
		dir = ((((int) (Math.random() * 10)) * 10) % 40) + 10;
		// implement queue with each node taking (x,y)
		queue[] que = new queue[MAXQSIZE];
		boolean weDidntFindAPlayer = true;
		int i, j, newi, newj;

		try {
			Thread.sleep(mode);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		i = positions[monster][XCOORD];
		j = positions[monster][YCOORD];
		mapArray[i][j] = INVALIDPOSITION;
		// Initialising the que

		newi = i + INCREMENT;
		newj = j;
		if ((newi <= XMAX && newi >= XMIN)) {
			if (mapArray[newi][newj] != INVALIDPOSITION) {
				// find player
				if (!reachedPlayerYet(newi, newj)) {
					if (mapArray[newi][newj] == NIL) {
						mapArray[newi][newj] = RIGHT;
						que[l] = new queue(newi, newj, RIGHT);
						l++;
					}
				} else {
					return RIGHT;
				}
			}
		}
		// Check Bottom
		newi = i;
		newj = j + INCREMENT;
		if ((newj <= YMAX && newj >= YMIN)) {
			if (mapArray[newi][newj] != INVALIDPOSITION) {
				// find player
				if (!reachedPlayerYet(newi, newj)) {
					if (mapArray[newi][newj] == NIL) {
						mapArray[newi][newj] = DOWN;
						que[l] = new queue(newi, newj, DOWN);
						l++;
					}
				} else {
					return DOWN;
				}
			}
		}
		// Check Left
		newi = i - DECREMENT;
		newj = j;
		if ((newi <= XMAX && newi >= XMIN)) {
			if (mapArray[newi][newj] != INVALIDPOSITION) {
				// find player
				if (!reachedPlayerYet(newi, newj)) {
					if (mapArray[newi][newj] == NIL) {
						mapArray[newi][newj] = LEFT;
						que[l] = new queue(newi, newj, LEFT);
						l++;
					}
				} else {
					return LEFT;
				}
			}
		}
		// Check Top
		newi = i;
		newj = j - DECREMENT;
		if ((newj <= YMAX && newj >= YMIN)) {
			if (mapArray[newi][newj] != INVALIDPOSITION) {
				// find player
				if (!reachedPlayerYet(newi, newj)) {
					if (mapArray[newi][newj] == NIL) {
						mapArray[newi][newj] = UP;
						que[l] = new queue(newi, newj, UP);
						l++;
					}
				} else {
					return UP;
				}
			}
		}

		while (weDidntFindAPlayer) {
			if (f < l) {
				i = que[f].x;
				j = que[f].y;
				dir = que[f].dir;
				f++;
			}
			// Check Right
			newi = i + INCREMENT;
			newj = j;
			if ((newi <= XMAX && newi >= XMIN)) {
				if (mapArray[newi][newj] != INVALIDPOSITION) {
					// find player
					if (!reachedPlayerYet(newi, newj)) {
						if (mapArray[newi][newj] == NIL) {
							mapArray[newi][newj] = RIGHT;
							que[l] = new queue(newi, newj, dir);
							l++;
						}
					} else {
						weDidntFindAPlayer = false;
						if (mapArray[i][j] == MONNSTER) {
							return RIGHT;
						}
						System.out.println("Thread: Direction " + dir);
						return dir;
					}
				}
			}
			// Check Bottom
			newi = i;
			newj = j + INCREMENT;
			if ((newj <= YMAX && newj >= YMIN)) {
				if (mapArray[newi][newj] != INVALIDPOSITION) {
					// find player
					if (!reachedPlayerYet(newi, newj)) {
						if (mapArray[newi][newj] == NIL) {
							mapArray[newi][newj] = DOWN;
							que[l] = new queue(newi, newj, dir);
							l++;
						}
					} else {
						weDidntFindAPlayer = false;
						if (mapArray[i][j] == MONNSTER) {
							return DOWN;
						}
						System.out.println("Thread: Direction " + dir);
						return dir;
					}
				}
			}
			// Check Left
			newi = i - DECREMENT;
			newj = j;
			if ((newi <= XMAX && newi >= XMIN)) {
				System.out.println(newi + " " + newj);
				if (mapArray[newi][newj] != INVALIDPOSITION) {
					// find player
					if (!reachedPlayerYet(newi, newj)) {
						if (mapArray[newi][newj] == NIL) {
							mapArray[newi][newj] = LEFT;
							que[l] = new queue(newi, newj, dir);
							l++;
						}
					} else {
						weDidntFindAPlayer = false;
						if (mapArray[i][j] == MONNSTER) {
							return LEFT;
						}
						System.out.println("Thread: Direction " + dir);
						return dir;
					}
				}
			}
			// Check Top
			newi = i;
			newj = j - DECREMENT;
			if ((newj <= YMAX && newj >= YMIN)) {
				if (mapArray[newi][newj] != INVALIDPOSITION) {
					// find player
					if (!reachedPlayerYet(newi, newj)) {
						if (mapArray[newi][newj] == NIL) {
							mapArray[newi][newj] = UP;
							que[l] = new queue(newi, newj, dir);
							l++;
						}
					} else {
						weDidntFindAPlayer = false;
						if (mapArray[i][j] == MONNSTER) {
							return UP;
						}
						System.out.println("Thread: Direction " + dir);
						return dir;
					}
				}
			}
		}
		System.out.println("Thread: Direction " + dir);
		return dir;
	}

	public boolean reachedPlayerYet(int x, int y) {
		if (mapArray[x][y] == FIRSTPLAYER || mapArray[x][y] == SECONDPLAYER || mapArray[x][y] == THIRDPLAYER || mapArray[x][y] == FOURTHPLAYER) {
			return true;
		}
		return false;
	}

	public void refreshGrid() {
		// Erase the Grid
		for (int i = XMIN; i <= XMAX; i++) {
			for (int j = YMIN; j <= YMAX; j++) {
				mapArray[i][j] = NIL;
			}
		}
		// Set the blocks not Allowed
		initializeGrid();
		// map the positions from player 1 to 4 and Monster
		for (int i = monster; i <= players; i++) {
			if (i != MONSTERR && !isDead(i))
				mapArray[positions[i][XCOORD]][positions[i][YCOORD]] = i;
		}
	}

	public void initializeGrid() {
		for (int i = 1; i < XMAX; i++) {
			if (i != XMID)
				for (int j = 1; j < YMAX; j++) {
					if (j != YMID) {
						mapArray[i][j] = INVALIDPOSITION;
					}
				}
		}

	}

	public void atePlayer() {
		int x = positions[monster][XCOORD];
		int y = positions[monster][YCOORD];
		if (mapArray[x][y] == FIRSTPLAYER) {
			positions[FIRSTPLAYER][XCOORD] = XMINDEADZONE;
			positions[FIRSTPLAYER][YCOORD] = YMINDEADZONE;
			broadcast("2-" + FIRSTPLAYER + "-" + positions[FIRSTPLAYER][XCOORD] + "-"
					+ positions[FIRSTPLAYER][YCOORD]);
		} else if (mapArray[x][y] == SECONDPLAYER) {
			positions[SECONDPLAYER][XCOORD] = XMAXDEADZONE;
			positions[SECONDPLAYER][YCOORD] = YMINDEADZONE;
			broadcast("2-" + SECONDPLAYER + "-" + positions[SECONDPLAYER][XCOORD] + "-"
					+ positions[SECONDPLAYER][YCOORD]);
		} else if (mapArray[x][y] == THIRDPLAYER) {
			positions[THIRDPLAYER][XCOORD] = XMINDEADZONE;
			positions[THIRDPLAYER][YCOORD] = YMAXDEADZONE;
			broadcast("2-" + THIRDPLAYER + "-" + positions[THIRDPLAYER][XCOORD] + "-"
					+ positions[THIRDPLAYER][YCOORD]);
		} else if (mapArray[x][y] == FOURTHPLAYER) {
			positions[FOURTHPLAYER][XCOORD] = XMAXDEADZONE;
			positions[FOURTHPLAYER][YCOORD] = YMAXDEADZONE;
			broadcast("2-" + FOURTHPLAYER + "-" + positions[FOURTHPLAYER][XCOORD] + "-"
					+ positions[FOURTHPLAYER][YCOORD]);
		}
		try {
			Thread.sleep(mode * FOURTHPLAYER);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		deadCount += INCREMENT;
	}

}