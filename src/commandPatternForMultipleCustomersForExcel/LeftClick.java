package commandPatternForMultipleCustomersForExcel;

import java.util.ArrayList;

import control.UtilControl;
import static constants.StringConstant.ZERO;


import entity.Customer;

public class LeftClick implements Command
{
	private final Customer customer;
	private final UtilControl utilControl = UtilControl.getInstance();
	
	public LeftClick(Customer customer)
	{
		this.customer = Customer.makeClone(customer);
	}
	
	@Override
	public void undo(ArrayList<Customer> selectedList) 
	{
		selectedList.add(customer);
	}

	@Override
	public void execute(ArrayList<Customer> selectedList, ArrayList<Customer> nullList) 
	{
		utilControl.getSpecificElement(selectedList, customer.getId()).setDankort(ZERO);
		selectedList.remove(utilControl.getSpecificElement(selectedList, customer.getId()));
	}

}
