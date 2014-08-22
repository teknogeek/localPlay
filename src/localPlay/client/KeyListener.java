package localPlay.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyListener extends KeyAdapter
{
	String all;
	// Set of currently pressed keys
	private final Map<Integer, String> keyTextToCode  = new HashMap<Integer, String>(256);
	private final Set<Integer> pressed = new HashSet<Integer>();
	
	public void fill()
	{
		Field[] fields = KeyEvent.class.getDeclaredFields();
		for(Field field : fields)
		{
		    String name = field.getName();
		    if(name.startsWith("VK_"))
		    {
		        try
		        {
		        	keyTextToCode.put(field.getInt(null), name.toUpperCase());
				}
		        catch(Exception e)
		        {
					e.printStackTrace();
				}
		    }
		}
	}
	
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
	    		all += keyTextToCode.get(key) + " ";
	    	}
	    	all = all.substring(0, all.length() - 1);
	    	Client.keys.setText(all);
	    }
	    else
	    {
	    	Client.keys.setText(String.valueOf(keyTextToCode.get(e.getKeyCode())));
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