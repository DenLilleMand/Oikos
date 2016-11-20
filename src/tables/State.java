
package tables;

import java.util.ArrayList;

import mediator.FileCase;
import entity.Customer;
import entity.LogEntry;
import entity.User;

/**
 * State.java
 * A class made to keep track of all our different variables.
 * To make sure that the variables we use are updated.
 * 
 * (Matti) - we should truly ask Christian, to bring us 
 * up to speed on what a State class is allowed to do, and what it's not,
 * because obviously it's a good thing that we've moved things that change
 * often out of their respective classes,but in this version of a State class, we have
 * truly opened up for all our fields bleeding out into the Client code and therefore creating
 * unnessecery dependencies, the selectedLogRow, i think this is an obvious mistake,
 * i have a feeling that this could be done alot smarter, it's rather weird 
 * performance wise that the tables has to go out of their own domain to get an int
 * like this-and it's rather unclear which methods would even be interesed in this int,
 * maybe 100 classes is using it - and maybe no1 is.
 *  
 * getters/setters has to go 100%, i think looking into the MVC pattern 
 * combined with the observer pattern is something along the correct path.
 * 
 * @author Jon
 */
public final class State
{
	LogEntry selectedLogEntry;
	Customer selectedCustomer;
	int selectedLogRow;
	
	//general filePath through the system
	private String filePath;
	

	//used in Excel-tables
	int selectedCustomerRow;
	int selectedRowForExcel;
	
	
	//Variables only used in UtilClass
	private ArrayList<Customer> customersInCustomerTable = new ArrayList<Customer>();

	Customer customerForExcel;
	Customer selectedCustomerForExcel;
	
	//ArrayLists are initialized, to prevent null-pointers when updating tables
	ArrayList<Customer> selectedCustomersForExcel = new ArrayList<Customer>();
	ArrayList<Customer> customersForExcel = new ArrayList<Customer>();
	
	User currentUser;
	
	public static FileCase fileCase;
	
	private static State instance;

	private State(){
		if(instance != null){
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}
	
	public static synchronized State getInstance(){
		if (instance == null){
			instance = new State();
		}
		return instance;
	}
	
	//set methods
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setSelectedLogEntry(LogEntry selectedLogEntry)
	{
		this.selectedLogEntry = selectedLogEntry;
	}
	
	public void setCustomerForExcel(Customer customerForExcel)
	{
		this.customerForExcel = customerForExcel;
	}
	
	public void setCustomersForExcel(ArrayList<Customer> customersForExcel)
	{
		this.customersForExcel = customersForExcel;
	}
	
	public void setSelectedCustomerForExcel(Customer selectedCustomerForExcel){
		this.selectedCustomerForExcel = selectedCustomerForExcel;
	}
	
	public void setSelectedCustomersForExcel(ArrayList<Customer> selectedCustomersForExcel){
		this.selectedCustomersForExcel = selectedCustomersForExcel;
	}
	
	public void setCurrentUser(User currentUser){
		this.currentUser = currentUser;
	}
	
	public void setFileCase(FileCase inputFileCase){
		fileCase = inputFileCase;
	}
	
	public void setCustomersInCustomerTable(ArrayList<Customer> customersInCustomerTable){
		this.customersInCustomerTable = customersInCustomerTable;
	}
	
	
	//get methods
	public String getFilePath() {
		return filePath;
	}
	
	public Customer getSelectedCustomer(){
		return selectedCustomer;
	}
	
	public LogEntry getSelectedLogEntry(){
		return selectedLogEntry;
	}
	
	public Customer getCustomerForExcel(){
		return customerForExcel;
	}
	
	public Customer getSelectedCustomerForExcel(){
		return selectedCustomerForExcel;
	}
	
	public ArrayList<Customer> getSelectedCustomersForExcel(){
		return selectedCustomersForExcel;
	}
	
	public int getSelectedCustomerRow(){
		return selectedCustomerRow;
	}
	
	public int getSelectedRowForExcel(){
		return selectedRowForExcel;
	}
	
	public ArrayList<Customer> getCustomersForExcel(){
		return customersForExcel;
	}
	
	public User getCurrentUser(){
		return currentUser;
	}
	

	public Enum<FileCase> getFileCase(){
		return fileCase;
	}
	
	public ArrayList<Customer> getCustomersInCustomerTable(){
		return customersInCustomerTable;
	}
	
	//tjek om metoden er nødvendig
	//bruges når man trykker annuller i multiCustomersForExcelDialog
	public void resetSelectedCustomersForExcelList()
	{
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		setSelectedCustomersForExcel(customerList);
	}
	

}
