package localPlay.server;
 
public class Protocol
{
    private static final int WAITING = 0;
    private static final int SENTWAITING = 1;
 
    private int state = WAITING;
 
    public String processInput(String theInput)
    {
        String theOutput = "";
        
        if(state == WAITING)
        {
            theOutput = "Waiting for input";
            state = SENTWAITING;
        }
        else if(state == SENTWAITING)
        {
        	if(theInput.equals("Waiting for input..."))
        	{
        		theOutput = "none";
        	}
        	else
        	{
        		theOutput = theInput;
        	}
        }
        return theOutput;
    }
}