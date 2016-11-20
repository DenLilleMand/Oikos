package tables;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import components.CardRenderer;
import components.GlobalCellRenderer;
import entity.Customer;


/**
 * CustomerTable.java
 * The standard main gui customer table
 * @author Matti, Jon
 */
public class SelectedCustomersForExcelTable extends SuperTable {
	private static final long serialVersionUID = -2951766959546527747L;

	private static final String ZERO = "";
	
	private DefaultTableModel selectedCustomersForExcelModel;
	

	/**
	 * Constructor for SelectedCustomersForExcelTable - table used to add people to excel
	 * 
	 */
	public SelectedCustomersForExcelTable()
	{

		mediator.setSelectedCustomersForExcelTable(this);
	
		selectedCustomersForExcelModel = new DefaultTableModel();

		selectedCustomersForExcelModel.setColumnIdentifiers(new String[] {"Navn", "Medlemsnummer", "Cpr Nummer", "Dankort" }); // maybe we want to
		setModel(selectedCustomersForExcelModel);
		
		getColumnModel().getColumn(0).setPreferredWidth(194);
		getColumnModel().getColumn(1).setPreferredWidth(120);
		getColumnModel().getColumn(2).setPreferredWidth(95);
		getColumnModel().getColumn(3).setPreferredWidth(60);
		getColumnModel().getColumn(3).setCellRenderer(new CardRenderer());
		GlobalCellRenderer GCR = new GlobalCellRenderer();
		getColumnModel().getColumn(0).setCellRenderer(GCR);
		getColumnModel().getColumn(1).setCellRenderer(GCR);
		getColumnModel().getColumn(2).setCellRenderer(GCR);
		

		ListSelectionModel listSelectionModel = getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting())
							return;

						ListSelectionModel lsm = (ListSelectionModel) e.getSource();
						if (lsm.isSelectionEmpty()) {
							//mediator.getSelectedCustomersForExcelTable().resetTableModel();
						} 
						else{
							state.selectedRowForExcel = lsm.getMinSelectionIndex();
							int selectedCustomerForExcelId = (int) getValueAt(state.selectedRowForExcel, 1);
							state.selectedCustomerForExcel = utilControl.getSpecificElement(state.selectedCustomersForExcel,selectedCustomerForExcelId);
						}
					}
				});

	}
	
	
	/**
	 * 
	 */
	public void updateSelectedCustomersForExcelTable()
	{	
			selectedCustomersForExcelModel.setRowCount(state.selectedCustomersForExcel.size());
			for(int i = 0; i < selectedCustomersForExcelModel.getRowCount(); i++){
				setRowHeight(i, 30);
			}
			int row = 0;
			for (Customer c :state.selectedCustomersForExcel){
				selectedCustomersForExcelModel.setValueAt(c.getFirstName() +" "+ c.getLastName(), row, 0); 
				selectedCustomersForExcelModel.setValueAt(c.getId(), row, 1);
				selectedCustomersForExcelModel.setValueAt(c.getCpr(), row, 2);
				selectedCustomersForExcelModel.setValueAt(c.getDankort(),row,3);
				row++;
			}
	}
	
	/**
	 * 
	 */
	public void cancelMultipleCustomersForExcel()
	{
		for(Customer c : state.selectedCustomersForExcel)
		{
			c.setDankort(ZERO);
		}
		//removes all elements in the list
		state.selectedCustomersForExcel.clear();
		//Then re-adds the selected customer, as we still need him to be on the list, should the user proceed
		//with creating the excel, just for the main customer.
		state.selectedCustomersForExcel.add(state.selectedCustomer);
	}	
}
