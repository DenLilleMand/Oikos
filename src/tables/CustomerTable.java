


package tables;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import components.GlobalCellRenderer;

import entity.Customer;
/**
 * CustomerTable.java
 * The standard main gui customer table
 * @author Jon, Matti
 */
public class CustomerTable extends SuperTable
{
	private static final long serialVersionUID = 8742250652168565679L;

	private DefaultTableModel customerModel;
	
	
	/**
	 * Constructor for CustomerTable.
	 */
	public CustomerTable()
	{
		
		mediator.setCustomerTable(this);
		
		//The model init
		customerModel = new DefaultTableModel();
		customerModel.setColumnIdentifiers(new String[]{"Navn", "Medlemsnummer","Cpr Nummer"});
		
		setModel(customerModel);
		
		getColumnModel().getColumn(0).setPreferredWidth(190);
		getColumnModel().getColumn(1).setPreferredWidth(120);
		getColumnModel().getColumn(2).setPreferredWidth(95);
		
		GlobalCellRenderer GCR = new GlobalCellRenderer();
		getColumnModel().getColumn(0).setCellRenderer(GCR);
		getColumnModel().getColumn(1).setCellRenderer(GCR);
		getColumnModel().getColumn(2).setCellRenderer(GCR);
		
		ListSelectionModel listSelectionModel = getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
	    	public void valueChanged(ListSelectionEvent e){
	    		if (e.getValueIsAdjusting())//checks whether an event is part of a chain and returns false only if its the final event in a chain
	    			return;//returns from method if getValueIsAdjusting() returns true.
	    		
	            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	            if (lsm.isSelectionEmpty()) {
	            	resetTableModel();
	            }
	            else{
	            	int selectedRow = lsm.getMinSelectionIndex();
	            	
	            	//pulls out the id from the table
	            	int customerId = (int)getValueAt(selectedRow, 1);
	            	
	            	//retrieves the selectedCustomer object by the id, sets it in State class
	            	state.selectedCustomer = utilControl.getSpecificElement(state.getCustomersInCustomerTable(), customerId);
	            	//sets the logEntryList of the selected customer by customer id
	            	state.selectedCustomer.setLogEntryList(utilControl.getLogEntryList(customerId));
	            	
	            	//updates the logEntryTable
	                mediator.getLogEntryTable().updateLogTable(state.selectedCustomer.getLogEntryList());
	                
	                //deselects the selectedLogEntry
	                state.selectedLogEntry = null;
	            }
	        }
	    });
	}
	

	/**
	 * Method used to updateCustomerTable
	 * takes a arraylist as parameter to update it's own global variable
	 */
	public void updateCustomerTable(ArrayList <Customer> inputList){	
		if(inputList!=null){
			customerModel.setRowCount(inputList.size());
			for(int i = 0; i < customerModel.getRowCount(); i++){
				setRowHeight(i, 30);
			}
			int row = 0;
			for (Customer c: inputList){
				customerModel.setValueAt(c.getFirstName() +" "+ c.getLastName(), row, 0); 
				customerModel.setValueAt(c.getId(), row, 1);
				customerModel.setValueAt(c.getCpr(), row, 2);
				row++;
			}
		}
	}
}
