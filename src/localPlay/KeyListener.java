package localPlay;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

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
	    		all += e.getKeyText(key) + " ";
	    	}
	    	Main.this.keys.setText("");
	    	main.keys.setText(all);
	    	System.out.println(all);
	    }
	    else
	    {
	    	main.keys.setText("");
	    	main.keys.setText(e.getKeyText(e.getKeyCode()));
	    	System.out.println(e.getKeyText(e.getKeyCode()));
	    }
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e)
	{
		pressed.remove(e.getKeyCode());
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		/* Not used */
	}
}