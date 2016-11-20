package control;

import java.io.File;
import java.util.ArrayList;
import entity.Customer;
import entity.Entity;
import entity.LogEntry;
/**
 * Interface for a utility-control class
 * @author Jon
 */
public interface IUtilControl {
	
	//Customer methods
	/**
	 * Searches customers and returns them as an arrayList
	 * @param search The parameter to search for
	 * @param positionString 
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> searchCustomers(String search, String positionString);
	/**
	 * Returns a Customer
	 * @param customerList The ArrayList of customers to search through
	 * @param search The parameter to search for
	 * @return Customer
	 */
	public <T extends Entity> T getSpecificElement(ArrayList<T>  inputList, int search);
	
	//Excel methods
	
	/**
	 * Method for sorting out already added customers for the addCustomersToexcel functionality 
	 * @param selectedList
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> sortListForExcel(ArrayList<Customer> selectedList);
	
	//LogEntry methods	
	/**
	 * Method for obtaining the entire logEntryList for a given customer.
	 * @param selectedCustomerId Id of the selected customer
	 * @return ArrayList<LogEntry>
	 */
	public ArrayList<LogEntry> getLogEntryList(int selectedCustomerId);

	
	/**
	 * Method for deleting a logEntry, from both the local list of the selectedCustomer
	 * and from the database itself.
	 * @param inputLog The LogEntry to delete
	 */
	public void deleteLogEntry(LogEntry inputLog);

	/**
	 *  Method for creating a new Logentry
	 * First sets all attributes based on current time, loggged in user and given parameters,
	 * then procedes to handle the given fileCase, before saving the logEntry and perhaps filereference to the DB
	 * @param description A short description
	 * @param comment A short comment
	 * @param file File related to Logentry
	 * @param inputList List of customers to include in entry
	 * @return int Id of newly created logentry
	 * @throws InterruptedException
	 * @throws Exception
	 */
//	public int newLogEntry(String description, String comment, final File file, ArrayList<Customer> inputList)
//			throws InterruptedException, Exception;
	/**
	 * Edits the info in a given logEntry to the new parametres. Can also handle change in the attached file
	 * @param inputLog
	 * @param inputFile
	 * @param comment
	 * @param description
	 * @param fileCase
	 * @param inputList
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void editExistingLogEntry(LogEntry inputLog, File inputFile, String comment, 
			String description, ArrayList<Customer> inputList)  throws InterruptedException, Exception;

	//Log in methods	
	/**
	 * Passes on the call further for logging in to the system
	 * @param attemptedUsername The entered username
	 * @param attemptedPassword The entered password
	 * @return boolean
	 */
	public boolean attemptLogin(String attemptedUsername, String attemptedPassword);
	
	//File methods
	/**
	 * Call to fileControl to open a given file in it's native application
	 * @param inputFile File to open in native app
	 */
	public void openFileInNativeApp(File inputFile) throws Exception;
	/**
	 * Method for saving a file, by saving the file to a specific place on the disk, as well as saving the 
	 * new filereference
	 * @param fileToSave
	 * @param inputCustomer
	 * @param logEntryId
	 * @throws InterruptedException
	 */
	public void saveFile(File fileToSave, Customer inputCustomer, LogEntry logEntryId) throws InterruptedException;
	
	/**
	 * Method for saving a different file to a already stored logEntry
	 * @param fileToSave
	 * @param inputCustomer
	 * @param inputLog
	 * @throws InterruptedException
	 */
//	public void editSavedFile(File fileToSave, Customer inputCustomer, LogEntry inputLog) throws InterruptedException;
//	
	/**
	 * Method for deleting a saved File, removes it from the disc, and the DB
	 * @param inputCustomer
	 * @param inputLog
	 */
	public void deleteSavedFile(LogEntry inputLog);

}
