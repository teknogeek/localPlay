package localPlay.server;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Server
{
	public static JLabel keys;
	public static Robot robot;
	public Set<String> nowPressed = new HashSet<String>();
	public static String inputLine, outputLine;
	
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
		    // Initiate conversation with client
		    Protocol protocol = new Protocol();
		    outputLine = protocol.processInput(null);
		    outToClient.println(outputLine);
		    
	        // Simulate a key press
		    robot = new Robot();
	        
	        //robot.keyPress(KeyEvent.VK_ALT);
	        
		    while((inputLine = inFromClient.readLine()) != null)
		    {
		        outputLine = protocol.processInput(inputLine);
		        outToClient.println(outputLine);
		        
		        if(!outputLine.equals("none") && !outputLine.contains("VK_UNDEFINED"))
		        {
		        	if(!nowPressed.contains(outputLine))
		        	{
		        		if(outputLine.contains(" "))
		        		{
		        			String[] split = outputLine.split(" ");
		        			pressMultipleKeys(split);
		        		}
		        		else
		        		{
		        			pressKey(outputLine);
		        		}
		        	}
		        }
		        else if(nowPressed.size() > 0)
		        {
		        	String[] nowPressedArray = nowPressed.toArray(new String[nowPressed.size()]);
		        	releaseMultipleKeys(nowPressedArray);
		        }
		        
		    	keys.setText(outputLine);
		        
		        if(outputLine.equals("Bye."))
		        {
		            break;
		        }
		    }
		}
	}
	
	public void pressKey(String key) throws Exception
	{
		robot.keyPress(KeyEvent.class.getField(key).getInt(null));
		nowPressed.add(key);
	}
	
	public void releaseKey(String key) throws Exception
	{
		robot.keyRelease(KeyEvent.class.getField(key).getInt(null));
		nowPressed.remove(key);
	}
	
	public void pressMultipleKeys(String[] keys) throws Exception
	{
		for(String key : keys)
		{
			nowPressed.add(key);
			robot.keyPress(KeyEvent.class.getField(key).getInt(null));
		}
	}
	
	public void releaseMultipleKeys(String[] keys) throws Exception
	{
		for(String key : keys)
		{
			nowPressed.remove(key);
			robot.keyRelease(KeyEvent.class.getField(key).getInt(null));
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
//			StringWriter outError = new StringWriter();
//			e.printStackTrace(new PrintWriter(outError));
//			keys.setText(outError.toString());
		}
	}
}
