
package components;

import gui.Ressources;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * CardRenderer.java
 * Custom implementation of the class DefaultTableCellRenderer
 * used to draw the "dankort" icon in multipleCustomersForExcel gui.
 * @author Matti 
 */
public class CardRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = -166379583761969293L;
	private static final String ZERO = "";

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	//Returns the default table cell renderer.
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		//A component is an object having a graphical representation
		//that can be displayed on the screen and that can interact with the user

    	final Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
    	if("card".equals(value)) 
    	{
        	((JLabel) tableCellRendererComponent).setAlignmentX(CENTER_ALIGNMENT);
        	((JLabel) tableCellRendererComponent).setIcon(Ressources.getDankort());
        	((JLabel) tableCellRendererComponent).setText(ZERO);
        } 
        else {
        	((JLabel) tableCellRendererComponent).setIcon(null);
        }
        	
		return tableCellRendererComponent;
    }
};
