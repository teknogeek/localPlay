package localPlay.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

public class KeyListener extends KeyAdapter
{
	String all;
	// Set of currently pressed keys
	private final Set<Integer> pressed = new HashSet<Integer>();
	
	@Override
	public synchronized void keyPressed(KeyEvent e)
	{
	    pressed.add(e.getKeyCode());
	    if(pressed.size() > 1)
	    {
	    	all = "";
	        // More than one key is currently pressed.
	        // Iterate over pressed to get the keys.
	    	for(Integer key : pressed)
	    	{
	    		all += e.getExtendedKeyCodeForChar(key) + ",";
	    	}
	    	all = all.substring(0, all.length() - 1);
	    	Client.keys.setText(all);
	    }
	    else
	    {
	    	Client.keys.setText(String.valueOf(e.getExtendedKeyCodeForChar(e.getKeyCode())));
	    }
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e)
	{
		pressed.remove(e.getKeyCode());
		Client.keys.setText("Waiting for input...");
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		/* Not used */
	}
}