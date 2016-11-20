package components;

import gui.Ressources;

import javax.swing.JMenu;

public class Menu extends JMenu
{
	private static final long serialVersionUID = 8806239592408194758L;

	public Menu(String text)
	{
		setText(text);
		setFont(Ressources.getFont());
	}	
}
