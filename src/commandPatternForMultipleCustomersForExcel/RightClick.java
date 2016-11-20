package commandPatternForMultipleCustomersForExcel;


import java.util.ArrayList;

import control.UtilControl;
import entity.Customer;

public class RightClick implements Command
{
	private final Customer customer;
	private final UtilControl utilControl = UtilControl.getInstance();
	
	public RightClick(Customer customer)
	{
		this.customer = Customer.makeClone(customer);
	}
	
	@Override
	public void undo(ArrayList<Customer> selectedList)
	{
		selectedList.remove(utilControl.getSpecificElement(selectedList, customer.getId()));
	}

	@Override
	public void execute(ArrayList<Customer> customerList, ArrayList<Customer> selectedList) 
	{
		selectedList.add(utilControl.getSpecificElement(customerList, customer.getId()));
	}
}
