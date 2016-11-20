package components;

import gui.Ressources;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * Button.java
 * Custom implementation of the class JButton to suit our needs
 * @author Matti,Jon
 */
public class Button extends JButton implements KeyListener{

	private static final long serialVersionUID = -449787755867114265L;
	private JPanel backgroundPanel;
	private JLabel textLabel;
	private Color color;
	private int caseNumber;
	private int caseNumberKeyListener;
	
	//Constructor for the Button class
	/**
	 * Fix this class, if the idea about javaFX gets declined,
	 * we need to take a look at a button class useing a gridbaglayout,
	 * and a bunch of components on top of it - we should be able to somehow make it 
	 * look like we want and behave like we want w/o all of this code, some of the calls
	 * doesn't even make sense because it's the default Values anyway,. - Matti
	 * @param text
	 */
	public Button(final String text)
	{
		//the panel in the background of the button
		backgroundPanel = new JPanel(new GridBagLayout());
		
		//mouselistener to change color 
		final MouseListener mouseListener = new MouseListener(){
			public void mouseClicked(final MouseEvent event) {}
			public void mouseEntered(final MouseEvent event) {
				caseNumber = 1;
				doThreadSafeWorkMouse();
			}
			public void mouseExited(final MouseEvent event) {
				caseNumber = 2;
				doThreadSafeWorkMouse();
			}
			public void mousePressed(final MouseEvent event) {}
			public void mouseReleased(final MouseEvent event) {}
		 };
		 
		 addMouseListener(mouseListener);
		 
		 final GridBagConstraints c = new GridBagConstraints();
		 /**
		  * gridx Specifies the cell containing the leading edge of the component's display area,
		  * where the first cell in a row has gridx=0
		  */
		 c.gridx = 1;
		 c.gridy = 1;

		 textLabel = new JLabel(text);//text for the button, sat in guis
		 textLabel.setFont(Ressources.getFont());
		 backgroundPanel.add(textLabel,c);
		 setMargin(new Insets(0,0,0,0));
		 setOpaque(true);//If true the component paints every pixel within its bounds
		 setFocusPainted(true);//Sets the paintFocus property, which must be true for the focus state to be painted
		 setContentAreaFilled(true);//Sets the contentAreaFilled property. If true the button will paint the content area
		 add(backgroundPanel);
	}
	
	 private void doThreadSafeWorkMouse() 
	 {
			if(SwingUtilities.isEventDispatchThread())
			{
				if(caseNumber == 1)
				{
			       color = textLabel.getForeground();
			       
			       		if( textLabel.getText().equalsIgnoreCase("annuller")
			    		|| textLabel.getText().equalsIgnoreCase("slet fil") || textLabel.getText().equalsIgnoreCase("slet")
			    		|| textLabel.getText().equalsIgnoreCase("fjern fra dankort 2")){
			            	textLabel.setForeground(new Color(255, 0,0 ));   
			            }
			     		else
			     		{
			     			textLabel.setForeground(new Color(0, 210,27 ));  
			     		}
				}
				else if(caseNumber == 2)
				{
					textLabel.setForeground(color);
				}
			}
			else
			{
					final Runnable callDoThreadSafeWork = new Runnable(){
					public void run()
					{
						doThreadSafeWorkMouse();
					}
				};
				//the component can be updated whenever the EDT has the time
				SwingUtilities.invokeLater(callDoThreadSafeWork);
			}
		}
	 
	 private void doThreadSafeWorkKeylistener() 
	 {
			if(SwingUtilities.isEventDispatchThread())
			{
				if(caseNumberKeyListener == 1)
				{  
			       		
				}
				else if(caseNumberKeyListener == 2)
				{
					
				}
			}
			else
			{
					final Runnable callDoThreadSafeWork = new Runnable(){
					public void run()
					{
						doThreadSafeWorkKeylistener();
					}
				};
				//the component can be updated whenever the EDT has the time
				SwingUtilities.invokeLater(callDoThreadSafeWork);
			}
		}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if(event.getKeyChar() == KeyEvent.VK_ENTER)
		{
			doThreadSafeWorkKeylistener();
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent event) {}

	@Override
	public void keyTyped(KeyEvent event) {}	
}
