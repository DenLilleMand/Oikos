
package components;

import gui.Ressources;
import javax.swing.JProgressBar;


/**
 * ProgressBar.java
 * A custom implementation of JProgressBar
 * makes sure that the % is painted on top of it, and that the min is 0 and 100 is max.
 * @author Matti
 */

public class ProgressBar extends JProgressBar 
{
	private static final long serialVersionUID = -2951067465602982189L;
	
	//Variables - ProgressBar related:
	final private boolean stringPainted = true; //the String painted inside the progressbar


	public ProgressBar()
	{
		setStringPainted(stringPainted);
		setFont(Ressources.getFont());
	}	
}