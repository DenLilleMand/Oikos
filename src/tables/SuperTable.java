

package tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import mediator.Mediator;
import control.UtilControl;
/**
 * SuperTable.java
 * a table that all 4 tables extend.
 * 
 * I feel like changing to groovy is a step in the right direction,
 * but i would just like to mention in relation to these JTables,
 * the Observer pattern is a no-brainer, it's probably the best 
 * pattern ever to update something like JTables.
 * 
 * 
 * @author Jon
 */
public abstract class SuperTable extends JTable{

	private static final long serialVersionUID = -1367127954693885895L;
	
	protected UtilControl utilControl = UtilControl.getInstance();
	protected Mediator mediator = Mediator.getInstance();
	protected State state = State.getInstance();
	
	
	public SuperTable()
	{
		setAutoCreateRowSorter(true);//allows to sort through the information.
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTableHeader().setReorderingAllowed(false);
	}
	
	//overrides the inherited method in children
	@Override
	public boolean isCellEditable(int row, int column) 
	{
       return false;
	}
	
	//Gets the TableModel of some JTable and cast it to DefaultTableModel to set rowCount to 0
	public void resetTableModel(){
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.setRowCount(0);
	}
	
	//Gets the TableModel of some JTable and cast it to DefaultTableModel to remove designated row
	public void removeRow(int rowToRemove)
	{
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.removeRow(rowToRemove);
	}
}
