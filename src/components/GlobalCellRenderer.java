package components;

import gui.Ressources;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class GlobalCellRenderer extends DefaultTableCellRenderer
{

	private static final long serialVersionUID = 4145985511780751760L;

		//Takes in the parameter value, which is the object that specifies what icon should render the cell.
		//value is set by logModel.setValueAt(log.getFileExtension(), row, 5) in LogEntryTable
		//Returns the default table cell renderer. Creates the component by call to superclass.
		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value,
				final boolean isSelected, final boolean hasFocus, final int row, final int column) { 
	    	final Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
	        ((JLabel) tableCellRendererComponent).setFont(Ressources.getFontForTables());
	        
	        //returns the rendered component
	        return this;
	    }
	};
