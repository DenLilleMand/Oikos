package components;

import gui.Ressources;

import javax.swing.JLabel;

public class Label extends JLabel
{
	private static final long serialVersionUID = -2802395924185309804L;

	public Label(final String text)
	{
		setText(text);
		setFont(Ressources.getFont());
	}
}
