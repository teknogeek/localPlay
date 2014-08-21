package localPlay.client;

import java.net.*;
import java.io.*;
 
public class KnockKnockProtocol
{
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
 
    private int state = WAITING;
 
    public String processInput(String theInput)
    {
        String theOutput = "";
        
        if(state == WAITING)
        {
            theOutput = "Waiting for input";
            state = SENTKNOCKKNOCK;
        }
        else if(state == SENTKNOCKKNOCK)
        {
        	if(theInput.equals("Ctrl + Alt + A"))
        	{
               	theOutput = "Bye.";
        	}
        	else if(theInput.equals("Waiting for input..."))
        	{
        		theOutput = "";
        	}
        	else
        	{
        		theOutput = theInput;
        	}
        }
        return theOutput;
    }
}