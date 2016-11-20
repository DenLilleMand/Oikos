package components;

import gui.Ressources;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckRenderer extends DefaultTableCellRenderer 
{

	private static final long serialVersionUID = 7930583307973616914L;

	@Override
	final public Component getTableCellRendererComponent(final JTable table, final Object value,
			final boolean isSelected, final boolean hasFocus, final int row, final int column) 
	{ 
    	Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
    	
        if("checked".equals(value))
        {
        	((JLabel) tableCellRendererComponent).setIcon(Ressources.getCheckedIcon());
        	//((JLabel) tableCellRendererComponent).setText("");
        }
        else 
        {
        	((JLabel) tableCellRendererComponent).setIcon(Ressources.getUncheckedIcon());
        	//((JLabel) tableCellRendererComponent).setText("");
        }
        
        //returns the rendered component
        return tableCellRendererComponent;
    }
};

