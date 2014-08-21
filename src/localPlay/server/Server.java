package localPlay.server;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import localPlay.client.KnockKnockProtocol;

public class Server
{
	public void server() throws Exception
	{
		startServerListener();
	}
	
	public void startServerListener() throws Exception
	{
		int portNumber = 6969;
		try
		( 
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		)
		{
		    String inputLine, outputLine;
			            
		    // Initiate conversation with client
		    KnockKnockProtocol kkp = new KnockKnockProtocol();
		    outputLine = kkp.processInput(null);
		    out.println(outputLine);
		    
	        // Simulate a key press
	        
		    Robot robot = new Robot();
	        
	        //robot.keyPress(KeyEvent.VK_ALT);
	        
		    while((inputLine = in.readLine()) != null)
		    {
		        outputLine = kkp.processInput(inputLine);
		        out.println(outputLine);
		        if(!outputLine.equals(""))
		        {
		        	if(inputLine.contains(","))
		        	{
		        		String[] keys = inputLine.split(",");
		        		
		        		for(String k : keys)
		        		{
		        			robot.keyPress(Integer.valueOf(k));
		        		}
		        		
		        		for(String k : keys)
		        		{
				        	robot.keyRelease(Integer.valueOf(k));
		        		}
		        	}
		        	robot.keyPress(Integer.valueOf(inputLine));
		        	robot.keyRelease(Integer.valueOf(inputLine));
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
