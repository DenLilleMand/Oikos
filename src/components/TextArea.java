package components;

import gui.Ressources;

import javax.swing.JTextArea;

public class TextArea extends JTextArea
{
	private static final long serialVersionUID = 1L;

	public TextArea(String text)
	{
		setLineWrap(true);
    	setWrapStyleWord(true);
		setText(text);
		setFont(Ressources.getFont());
	}
	
}
