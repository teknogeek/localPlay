package localPlay;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main
{
	public JLabel keys;
	public void start()
	{
        // Simulate a key press
        /*
        Robot robot = new Robot();
        
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
        */

	    JFrame frame = new JFrame("Input Key Presses");
	    frame.addKeyListener(new KeyListener());
	    frame.setSize(300, 100);
	    
	    keys = new JLabel("<input>");
	    
	    frame.add(keys);
	    frame.setVisible(true);
	}
	
	public static void main(String args[])
	{
		new Main().start();
	}
}
