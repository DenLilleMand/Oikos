package components;

import gui.Ressources;

import javax.swing.border.TitledBorder;

public class CustomTitledBorder extends TitledBorder
{

	private static final long serialVersionUID = -2517237771202235148L;

	public CustomTitledBorder(final String title) 
	{
		super(title);
		setTitleFont(Ressources.getFont());
		
	}
	
}
