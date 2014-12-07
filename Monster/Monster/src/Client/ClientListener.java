package Client;
/* COMPLETED AS A TEAM*/
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class ClientListener extends Thread {
	BufferedReader 	in;
	PrintWriter 	out;
	public ClientListener(ClientGUI c, Socket s){
		try{
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()), true);
			String inString = "";
			while((inString = in.readLine()) != null){
				if(!inString.equals("6"))// 4 means no update
				{
					System.out.println("Server: Updates in positions "+inString);
					c.handleUpdate(inString);
				}
			}
		} catch(IOException e){
			System.out.println("Error in I/O stream: "+ e);
		}
		
	}

}
