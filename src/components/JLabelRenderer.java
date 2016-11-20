
package components;
import gui.Ressources;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/*
 * JLabelRenderer.java
 * A custom implementation of DefaultTableCellRenderer - used to draw fileExtensions and their matching pictures
 * @Author Matti
 */
public class JLabelRenderer extends DefaultTableCellRenderer 
{

	private static final long serialVersionUID = -166379583761969293L;
	
	//Takes in the parameter value, which is the object that specifies what icon should render the cell.
	//value is set by logModel.setValueAt(log.getFileExtension(), row, 5) in LogEntryTable
	//Returns the default table cell renderer. Creates the component by call to superclass.
	@Override
	final public Component getTableCellRendererComponent(final JTable table, final Object value,
			final boolean isSelected, final boolean hasFocus, final int row, final int column) { 
    	Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
    	
        if("doc".equals(value) || "docx".equals(value)) {
        	((JLabel) tableCellRendererComponent).setIcon(Ressources.getDoc());
        	((JLabel) tableCellRendererComponent).setFont(Ressources.getFontForTables());
        } 
        else  if("xlsx".equals(value)){
        	((JLabel) tableCellRendererComponent).setIcon(Ressources.getXlsx());
        	((JLabel) tableCellRendererComponent).setFont(Ressources.getFontForTables());
            
        } 
        else if("pdf".equals(value)){
        	((JLabel) tableCellRendererComponent).setIcon(Ressources.getPdf());
        	((JLabel) tableCellRendererComponent).setFont(Ressources.getFontForTables());
        }
        else {
        	((JLabel) tableCellRendererComponent).setIcon(null);
        }
        
        //returns the rendered component
        return tableCellRendererComponent;
    }
};

