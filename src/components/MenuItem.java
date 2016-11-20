package components;

import gui.Ressources;

import javax.swing.JMenuItem;

public class MenuItem extends JMenuItem
{
	private static final long serialVersionUID = -8852403488018517825L;

	
	
	public MenuItem(String text)
	{
		setText(text);
		setFont(Ressources.getFont());
	}
	
	
	
}
