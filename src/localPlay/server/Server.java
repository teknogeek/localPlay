package localPlay.server;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import localPlay.client.KeyListener;

public class Server
{
	public JLabel keys;
	
	public void server() throws Exception
	{
		JFrame frame = new JFrame("Server");
		
		keys = new JLabel("", SwingConstants.CENTER);
	    keys.setFont(keys.getFont().deriveFont(18.0f));

	    frame.setSize(300, 200);
	    frame.add(keys);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    
		startServerListener();
	}
	
	public void startServerListener() throws Exception
	{
		int portNumber = 6969;
		try
		(
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		)
		{
		    String inputLine, outputLine;
		    
		    // Initiate conversation with client
		    Protocol protocol = new Protocol();
		    outputLine = protocol.processInput(null);
		    outToClient.println(outputLine);
		    
	        // Simulate a key press
		    Robot robot = new Robot();
	        
	        //robot.keyPress(KeyEvent.VK_ALT);
	        
		    while((inputLine = inFromClient.readLine()) != null)
		    {
		    	keys.setText(outputLine);
		        outputLine = protocol.processInput(inputLine);
		        outToClient.println(outputLine);
		        
		        if(!outputLine.equals("none"))
		        {
		        	if(!keys.getText().equals(outputLine) && !keys.getText().equals("none"))
		        	{
		        		System.out.println("not the same");
		        		if(keys.getText().contains(" "))
			        	{
		        			String[] oldArray = keys.getText().split(" ");
			        		for(String k : oldArray)
			        		{
					        	robot.keyRelease(KeyEvent.class.getField(k).getInt(null));
			        		}
			        	}
		        		else
		        		{
		        			robot.keyRelease(KeyEvent.class.getField(keys.getText()).getInt(null));
		        		}
		        		
		        		if(outputLine.contains(" "))
			        	{
		        			String[] newArray = outputLine.split(" ");
			        		for(String k : newArray)
			        		{
					        	robot.keyPress(KeyEvent.class.getField(k).getInt(null));
			        		}
			        	}
		        		else
		        		{
		        			System.out.println("pressing " + outputLine);
		        			robot.keyPress(KeyEvent.class.getField(outputLine).getInt(null));
		        		}
		        	}
		        }
		        
		        if(outputLine.equals("Bye."))
		        {
		            break;
		        }
		    }
		}
	}
	
	public static void main(String args[])
	{
		try
		{
			new Server().server();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
