package localPlay.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Client
{
	public static JLabel keys;
	public JFrame frame;
	String userIP = "";
	String fromServer, fromUser;
	
	public void start() throws Exception
	{
	    frame = new JFrame("Input Key Presses");
	    
	    getUserIP();
	    
	    keys = new JLabel("Waiting for input...", SwingConstants.CENTER);
	    keys.setFont(keys.getFont().deriveFont(18.0f));
	    
	    JTextArea input = new JTextArea();
	    input.setSize(100, 100);
	    
	    frame.addKeyListener(new KeyListener());
	    frame.setSize(300, 200);
	    frame.add(keys);
	    //frame.add(input);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    
	    startClientConnect();
	}
	
	public void getUserIP()
	{
	    if(!validIP(userIP = JOptionPane.showInputDialog("Please input other user IP")))
	    {
	    	userIP = "";
	    	getUserIP();
	    }
	}
	
	public void startClientConnect() throws Exception
	{
		String hostName = "localhost";
		int portNumber = 6969;

		try
		(
		    Socket kkSocket = new Socket(hostName, portNumber);
		    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
		)
		{
			while((fromServer = in.readLine()) != null)
			{
			    if(fromServer.equals("Bye."))
			    {
			    	break;
			    }
			    	System.out.println("Server: " + fromServer);
			    
			    
			    fromUser = keys.getText();
			    if(fromUser != null)
			    {
			    	if(!fromUser.equals("Waiting for input...")) System.out.println("Client: " + fromUser);
			    	out.println(fromUser);
			    }
			}
		}
	}
	
	public static boolean validIP(String ip)
	{
		if(ip == null || (ip == null && !("".equals(ip))))
		{
		    System.exit(0);
		}
		
	    if(ip == null || ip.isEmpty())
	    {
	    	return false;
	    }
	    
	    ip = ip.trim();
	    if((ip.length() < 7) & (ip.length() > 15))
	    {
	    	return false;
	    }

	    try
	    {
	        Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	        Matcher matcher = pattern.matcher(ip);
	        return matcher.matches();
	    }
	    catch(PatternSyntaxException ex)
	    {
	        return false;
	    }
	}
	
	public static void main(String args[])
	{
		try
		{
			new Client().start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
