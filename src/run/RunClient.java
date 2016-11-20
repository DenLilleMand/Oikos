package run;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mediator.Go;
import mediator.Mediator;

/**
 * Main Class to run the application from command line arguments
 * Instantiates Mediator and goes to LoginGui
 * @author Matti, Jon admin
 */
public class RunClient 
{
	public static void main(String[] args)
	{
		    try  
		    { 
		    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		       // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } 
		    catch (UnsupportedLookAndFeelException e) 
		    {
		       e.printStackTrace();
		    }
		    catch (ClassNotFoundException e) 
		    {
		    	e.printStackTrace();
		    }
		    catch (InstantiationException e) 
		    {
		    	e.printStackTrace();
		    }
		    catch (IllegalAccessException e)  
		    {
		    	 e.printStackTrace();
		    }
		    Mediator.getInstance().handle(Go.LOGIN);
		}
		//Because it's static it can be run before the object is created
		//instantiates the Mediator class in which the first GUI is instantiated
	}

