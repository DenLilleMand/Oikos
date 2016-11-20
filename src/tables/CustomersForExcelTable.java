
package tables;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import components.GlobalCellRenderer;

import entity.Customer;


/**
 * CustomersForExcelTable.java
 * the multipleCustomersForExcel table 
 * @author Matti, Jon
 */

public class CustomersForExcelTable extends SuperTable {

	private static final long serialVersionUID = -9117192532252921798L;
	/**
	 * This defaultTable model was a static final, which gave us a pretty hilarious
	 * bug where multipleCustomersForExcel didn't delete it's model for every time,
	 * it had to update it's jtables, because any constructor being runned once again, didn't
	 * change the model at all
	 */
	private DefaultTableModel CUSTOMERSFOREXCELMODEL = new DefaultTableModel();
	
	/**
	 * Constructor for CustomersForExcelTable class
	 * this table is used in MultipleCustomersForExcel
	 */
	public CustomersForExcelTable(){
		mediator.setCustomersForExcelTable(this);
		
		
		CUSTOMERSFOREXCELMODEL.setColumnIdentifiers(new String[]{"Navn", "Medlemsnummer","Cpr Nummer"});	
		setModel(CUSTOMERSFOREXCELMODEL);
		
		getColumnModel().getColumn(0).setPreferredWidth(214);
		getColumnModel().getColumn(1).setPreferredWidth(120);
		getColumnModel().getColumn(2).setPreferredWidth(95);
		
		GlobalCellRenderer GCR = new GlobalCellRenderer();
		getColumnModel().getColumn(0).setCellRenderer(GCR);
		getColumnModel().getColumn(1).setCellRenderer(GCR);
		getColumnModel().getColumn(2).setCellRenderer(GCR);

		final ListSelectionModel listSelectionModel = getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new ListSelectionListener()
	    {
	    	public void valueChanged(final ListSelectionEvent e){
	    		//checks whether an event is part of a chain and returns false only if its the final event in a chain
	    		if (e.getValueIsAdjusting())
	    		{
	    			return;//returns from method if getValueIsAdjusting() returns true.
	    		}
	    		
	            final ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	            if (lsm.isSelectionEmpty())
	            {
	            	//mediator.getCustomersForExcelTable().resetTableModel();
	            }
	            else
	            {
	            	state.selectedCustomerRow = lsm.getMinSelectionIndex();
	            	final int customerForExcelId = (int)getValueAt(state.selectedCustomerRow, 1);
	            	state.customerForExcel = utilControl.getSpecificElement(state.customersForExcel, customerForExcelId);           	
	            }
	        }
	    });
     	
		
	}

	/**
	 * Responsible for updating the customersForExcelTable
	 * over in MultipleCustomersForExcel.
	 * we use the state variable customersForExcel for this.
	 */
		public void updateCustomerForExcelTable(ArrayList<Customer> inputList)
		{	
				CUSTOMERSFOREXCELMODEL.setRowCount(inputList.size());
				for(int i = 0; i < CUSTOMERSFOREXCELMODEL.getRowCount(); i++)
				{
					setRowHeight(i, 30);
				}
				int row = 0;

				for (Customer c: inputList)
				{
					CUSTOMERSFOREXCELMODEL.setValueAt(c.getFirstName() +" "+ c.getLastName(), row, 0); 
					CUSTOMERSFOREXCELMODEL.setValueAt(c.getId(), row, 1);
					CUSTOMERSFOREXCELMODEL.setValueAt(c.getCpr(), row, 2);
					row++;
				}
		}	
}
