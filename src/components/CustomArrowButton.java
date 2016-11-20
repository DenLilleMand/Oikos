
package components;

import gui.Ressources;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * CustomArrowButton.java
 * Custom implementation of the class JButton 
 * used to make MultipleCustomersForExcel more intriguing with arrows
 * @author Matti
 */
public class CustomArrowButton extends JButton {
	private static final long serialVersionUID = -2985307705532149962L;
	
	//only called from within the gui package, therefore protected
	public CustomArrowButton(final String direction) 
	{
		JPanel backgroundPanel = new JPanel(new GridBagLayout())
		{
			private static final long serialVersionUID = -2340349332345437132L;
			
			@Override
			protected void paintComponent(final Graphics g)
			{

				if(direction.equals("right")){
					Ressources.getRightarrow().paintIcon(this, g,0,0);
				}
				else if(direction.equals("left")){
					Ressources.getLeftarrow().paintIcon(this, g,0,0);
				}	
			}
		};

		setMargin(new Insets(0,0,0,0));
		setOpaque(false);//If true the component paints every pixel within its bounds
		setFocusPainted(true);
		setContentAreaFilled(true);
		add(backgroundPanel);
	}
}
