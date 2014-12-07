package Client;
/*COMLETED BY ERIKA S3391464*/
import java.awt.*;

import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.InetAddress;
import java.net.*;

public class ClientGUI extends JFrame implements KeyListener {

	private BufferedImage loadGrid = ImageUtil
			.loadImage("Monster/src/images/grid.gif");
	private BufferedImage loadMons = ImageUtil
			.loadImage("Monster/src/images/mnst.gif");
	private BufferedImage loadImg1 = ImageUtil
			.loadImage("Monster/src/images/p1.gif");
	private BufferedImage loadImg2 = ImageUtil
			.loadImage("Monster/src/images/p2.gif");
	private BufferedImage loadImg3 = ImageUtil
			.loadImage("Monster/src/images/p3.gif");
	private BufferedImage loadImg4 = ImageUtil
			.loadImage("Monster/src/images/p4.gif");
	private BufferedImage loadImgy = ImageUtil
			.loadImage("Monster/src/images/you.gif");
	private BufferedImage[] dloadImg = new BufferedImage[6];
	private JImagePanel[] dpanel = new JImagePanel[6];
	private JImagePanel panelg = new JImagePanel(loadGrid, 0, 0);
	private JImagePanel panelm = new JImagePanel(loadMons, 0, 0);
	private JImagePanel panel1 = new JImagePanel(loadImg1, 0, 0);
	private JImagePanel panel2 = new JImagePanel(loadImg2, 0, 0);
	private JImagePanel panel3 = new JImagePanel(loadImg3, 0, 0);
	private JImagePanel panel4 = new JImagePanel(loadImg4, 0, 0);
	private JImagePanel panely = new JImagePanel(loadImgy, 0, 0);
	private JImagePanel panelp;
	private JLayeredPane lypane = new JLayeredPane();

	public ClientListener cl;

	private int margen = 10, chances = 2;

	public int x1, y1, px1, py1, record = 0;
	public int[][] positions = new int[6][5];
	private int players, player;
	private int portNo = 10997;
	public Socket client;
	public BufferedReader in;
	public PrintWriter out;

	public Socket receiver;
	public ServerSocket rv;
	public BufferedReader entrada;
	public PrintWriter salida;

	private String inString;
	private String outString = "4";

	private BufferedReader archivoIn;
	private PrintWriter archivoOut;
	public int level;

	public int inttopix(int j) {
		int pix;
		pix = (j * 45) + margen;
		return pix;
	}

	public int pixtoint(int j) {
		int integ;
		integ = (j * 45) + margen;
		return integ;
	}

	public ClientGUI() {
		super("Ultimate Monster Game 2012");

		// Establish Communication with the Server here
		//
		String message;
		try {
			// this.client = new Socket(InetAddress.getByAddress(new byte[] {
			// (byte)131, (byte)170, (byte)50, (byte)224}),portNo);
			String host = JOptionPane.showInputDialog("Hostname: ");
			this.client = new Socket(host, portNo);
			this.in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			this.out = new PrintWriter(new BufferedOutputStream(
					client.getOutputStream()), true);

			try {
				message = in.readLine();
				System.out.println(message);
				// handles first player
				if (message.equals("p1")) {
					String players = JOptionPane
							.showInputDialog("Nice! You are player one, indicate number of players: ");
					if (players == null) {
						System.exit(0);
					}
					out.println(players);
					level = Integer
							.parseInt(JOptionPane
									.showInputDialog("Choose the Level:\n1.Easy\n2.Medium\n3.Hard "));
					out.println(level);
				} else {
					JOptionPane
							.showMessageDialog(null, "Thanks! you have join");
				}
				// handles positions
				int leftSpace = Integer.parseInt(in.readLine());
				String msg = "Please enter where do you want to start: \n";
				int[] posi = new int[10];
				for (int i = 1; i <= leftSpace; i++) {
					switch ((posi[i] = Integer.parseInt(in.readLine()))) {
					case 1:
						msg += "1.Top-Left\n";
						break;
					case 2:
						msg += "2.Top-Right\n";
						break;
					case 3:
						msg += "3.Bottom-Left\n";
						break;
					case 4:
						msg += "4.Bottom-Right\n";
						break;
					}

				}
				int wrongPosition = 0;
				String position = "";
				do {
					position = JOptionPane.showInputDialog(msg);
					int p = Integer.parseInt(position);
					for (int i = 1; i <= leftSpace; i++) {
						if (p == posi[i]) {
							wrongPosition = 1;
							break;
						}

					}
				} while (wrongPosition == 0);
				out.println(position);
				// String position = JOptionPane.showInputDialog("Posi");

				// waits or not until everybody else starts
				String status = in.readLine();
				if (status.equals("w")) {
					JOptionPane.showMessageDialog(null,
							"Wait for others to join");
				} else
					System.out.println("You are the last player");

				// for(int i=1; i<=3; i++)
				// {
				// System.out.println(in.readLine());
				// }

				// Initialize the players positions----receive all players pos
				System.out.println("Waiting to read no of players");
				players = Integer.parseInt(in.readLine());
				System.out.println("Waiting to read player no.");
				player = Integer.parseInt(in.readLine());
				for (int i = 0; i <= players; i++) {
					System.out.println("Has entered up to here: " + i);
					try {
						message = in.readLine();
						System.out.println("Ok, lets see what you sent! "
								+ message);
						StringTokenizer t = new StringTokenizer(message, "-");
						String token = t.nextToken();
						// Escape not yet implemented
						if (token.equals("1")) { // Initializing the player
													// positions
							positions[i][0] = Integer.parseInt(t.nextToken());
							positions[i][2] = inttopix(positions[i][0]);
							positions[i][1] = Integer.parseInt(t.nextToken());
							positions[i][3] = inttopix(positions[i][1]);
							System.out
									.println("Client: Positions taken: \n Player "
											+ i
											+ "x "
											+ positions[i][0]
											+ "y "
											+ positions[i][1]);
						}
					} catch (IOException e) {
						System.out
								.println("Client: Error:Initialising player positions"
										+ e.getMessage());
					}
				}
			} catch (IOException e) {
				System.err.println("Error receiving Message");
			}

		} catch (IOException e) {
			System.out.println("Error in establishing Server communication\n"
					+ e.getMessage());
			System.exit(0);
		}
		
		initializePanels();
		cl = new ClientListener(this, client);
		cl.start();
		
	}

	public void writeFile(String input) {
		try {
			archivoOut = new PrintWriter(new FileWriter(
					"Monster/src/Client.txt", true));
			// archivoOut.println(datos);
			archivoOut.println(input);
			archivoOut.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	public void initializePanels() {
		dpanel[0] = panelm;
		dpanel[1] = panel1;
		dpanel[2] = panel2;
		dpanel[3] = panel3;
		dpanel[4] = panel4;

		dloadImg[0] = loadMons;
		dloadImg[1] = loadImg1;
		dloadImg[2] = loadImg2;
		dloadImg[3] = loadImg3;
		dloadImg[4] = loadImg4;

		for (int i = 0; i <= players; i++) {
			dpanel[i].setBounds(positions[i][2], positions[i][3], 45, 45);
			lypane.add(dpanel[i]);
		}
		panelp = new JImagePanel(dloadImg[player], 0, 0);
		panelp.setBounds(250, 420, 45, 45);
		panely.setBounds(160, 420, 90, 45);
		lypane.add(panelp);
		lypane.add(panely);
		panelg.setBounds(margen, margen, 405, 405);
		lypane.add(panelg);

		add(lypane);
		addKeyListener(this);

		setSize(447, 510);
		setVisible(true);
	}

	public void moveRequests() throws IOException {
		System.out.println("Entering moveRequests!");
		while ((inString = entrada.readLine()) != null) {
			// writeFile(record+" Start:   "+inString);
			System.out.println("Entering whileeeee!");
			System.out.println("Server input: " + inString);
			handleUpdate(inString);

			if (inString.equals("Bye."))
				break;
		}
	}

	public void handleUpdate(String instr) {
		System.out.println("Handling update");
		int pl;
		StringTokenizer t = new StringTokenizer(instr, "-");
		String token = t.nextToken();
		if (token.equals("2")) // 2 means movement from one player or monster
		{
			pl = Integer.parseInt(t.nextToken()); // takes the number of the
													// player
			// updating positions
			positions[pl][0] = Integer.parseInt(t.nextToken());
			positions[pl][2] = inttopix(positions[pl][0]);
			positions[pl][1] = Integer.parseInt(t.nextToken());
			positions[pl][3] = inttopix(positions[pl][1]);
			for (int i = 0; i <= 3; i++) {
				// System.in.read();
				System.out.println(positions[pl][i]);
			}
			// updating place in the user interface
			dpanel[pl].setBounds(positions[pl][2], positions[pl][3], 45, 45);
		}
		if (token.equals("3")) {
			// means Won
			JOptionPane.showMessageDialog(null, "Congrats! you've WON!!");
			try {
				in.close();
				out.close();
				client.close();
			} catch (IOException e) {
				System.exit(0);
			}

		}
		if (token.equals("9")) {
			// means Won
			JOptionPane.showMessageDialog(null, "Sorry!! You're Dead!!");
			try {
				in.close();
				out.close();
				client.close();
			} catch (IOException e) {
				System.exit(0);
			}
		}
		if (token.equals("6")) {
			return;
		}
		// }
	}

	public String toStringRequest(int x, int y) {
		return ("1-" + x + "-" + y); // 1 means request on moving
	}

	public void keyTyped(KeyEvent evt) {
		// The user has typed a character, while the
		// apple thas the input focus. If it is one
		// of the keys that represents a color, change
		// the color of the square and redraw the applet.

		char ch = evt.getKeyChar(); // The character typed

	} // end keyTyped()

	public boolean askThread(int x, int y, int k) {
		String message;
		try {
			if (k == 4) {
				out.println("4");
				message = in.readLine();
				while (!message.equals("6")) {
					System.out.println(message);
					StringTokenizer t = new StringTokenizer(message, "-");
					String token = t.nextToken();
					// Escape not yet implemented
					if (token.equals("2")) { // Initializing the player
												// positions
						int p = Integer.parseInt(t.nextToken());
						positions[p][0] = Integer.parseInt(t.nextToken());
						positions[p][2] = inttopix(positions[p][0]);
						positions[p][1] = Integer.parseInt(t.nextToken());
						positions[p][3] = inttopix(positions[p][1]);
						System.out.println("Client:" + p + " Positions taken");
					}
				}
			} else {
				out.println("1-" + x + "-" + y);
				message = in.readLine();
				StringTokenizer t = new StringTokenizer(message, "-");
				String token = t.nextToken();
				// Escape not yet implemented
				if (token.equals("1")) { // Initializing the player positions
					if (t.nextToken().equals("OK")) {
						System.out.println("Client: Positions taken");
						return true;
					}
					return false;
				}
			}
		} catch (IOException e) {

		}
		return false;
	}

	public void keyPressed(KeyEvent evt) {
		// Called when the user has pressed a key, which can be
		// a special key such as an arrow key. If the key pressed
		// was one of the arrow keys, move the square (but make sure
		// that it doesn't move off the edge, allowing for a
		// 3-pixel border all around the applet).

		int key = evt.getKeyCode(); // keyboard code for the key that was
									// pressed
		System.out.println("You pressed a key");
		String outstr = "";
		if (key == KeyEvent.VK_LEFT) {
			if (positions[player][0] > 0) {
				if (positions[player][1] == 0 || positions[player][1] == 4 || positions[player][1] == 8) {
					x1 = positions[player][0] - 1;
					outString = toStringRequest(x1, positions[player][1]);
					out.println(outString);
					// try{
					// outstr=in.readLine();} catch (IOException ioe){
					// }
					// if(outstr.equals("OK")){
					// positions[player][0] = x1;
					// positions[player][2] = inttopix(x1);
					//
					// dpanel[player].setBounds(positions[player][2],
					// positions[player][3], 45, 45);
					// }

					System.out.println(outString);
				}
			} else
				System.out.println("Not allowed");

			System.out.println("Left");
		} else if (key == KeyEvent.VK_RIGHT) {
			if (positions[player][0] < 8) {
				if (positions[player][1] == 0 || positions[player][1] == 4
						|| positions[player][1] == 8) {
					x1 = positions[player][0] + 1;
					outString = toStringRequest(x1, positions[player][1]);
					out.println(outString);
					// try{
					// outstr=in.readLine();} catch (IOException ioe){
					// }
					// if(outstr.equals("OK")){
					// positions[player][0] = x1;
					// positions[player][2] = inttopix(x1);
					//
					// dpanel[player].setBounds(positions[player][2],
					// positions[player][3], 45, 45);
					// }

					System.out.println(outString);
				}
			} else
				System.out.println("Not allowed");
			System.out.println("Right");
		} else if (key == KeyEvent.VK_UP) {
			if (positions[player][1] > 0) {
				if (positions[player][0] == 0 || positions[player][0] == 4
						|| positions[player][0] == 8) {
					y1 = positions[player][1] - 1;
					outString = toStringRequest(positions[player][0], y1);
					out.println(outString);
					// try{
					// outstr=in.readLine();} catch (IOException ioe){
					// }
					// if(outstr.equals("OK")){
					// positions[player][1] = y1;
					// positions[player][3] = inttopix(y1);
					//
					// dpanel[player].setBounds(positions[player][2],
					// positions[player][3], 45, 45);
					// }

					System.out.println(outString);
				}
			} else
				System.out.println("Not allowed");
			System.out.println("Up");
		} else if (key == KeyEvent.VK_DOWN) {
			if (positions[player][1] < 8) {
				if (positions[player][0] == 0 || positions[player][0] == 4
						|| positions[player][0] == 8) {
					y1 = positions[player][1] + 1;
					outString = toStringRequest(positions[player][0], y1);
					out.println(outString);
					// try{
					// outstr=in.readLine();} catch (IOException ioe){
					// }
					// if(outstr.equals("OK")){
					// positions[player][1] = y1;
					// positions[player][3] = inttopix(y1);
					//
					// dpanel[player].setBounds(positions[player][2],
					// positions[player][3], 45, 45);
					// }

					System.out.println(outString);
				}
			} else
				System.out.println("Not allowed");

			System.out.println("Down");

		}

		else if (key == 27) {
			if (chances > 0) {
				chances = chances - 1;
				y1 = 4;
				x1 = 4;
				outString = toStringRequest(x1, y1);
				out.println(outString);
				// try{
				// outstr=in.readLine();} catch (IOException ioe){
				// }
				// if(outstr.equals("OK")){
				// positions[player][0] = 4;
				// positions[player][2] = inttopix(4);
				// positions[player][1] = 4;
				// positions[player][3] = inttopix(4);
				//
				// dpanel[player].setBounds(positions[player][2],
				// positions[player][3], 45, 45);
				// }

				System.out.println(outString);
			} else
				JOptionPane
						.showMessageDialog(null, "You-ve ran out of chances");
		}

	} // end keyPressed()

	public void keyReleased(KeyEvent evt) {
		// empty method, required by the KeyListener Interface
	}

	public static void main(String[] args) {
		// if(args.length != 1){
		// System.err.println("Error: Host Address Not Provided.");
		// System.exit(0);
		// }
		ClientGUI iu = new ClientGUI();
	}

}
