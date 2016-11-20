package components;

import gui.Ressources;

import javax.swing.JTextField;

public class TextField extends JTextField
{
	private static final long serialVersionUID = 1L;
	
	public TextField(String text, Boolean editable)
	{
		setEditable(editable);
		setFont(Ressources.getFont());
		setText(text);
	}
	
	
}
