package control;

import java.io.File;
import java.util.ArrayList;

import entity.Customer;
import entity.LogEntry;

/**
 * Interface for FileControl classes
 * @author Jon
 */
public interface IFileControl {
	/**
	 *  Forwards the request to save a file to the fileWrapper class and returns the file given from 
	 * filewrapper.
	 * 
	 * @param inputFile
	 * @param inputCustomer
	 * @return LogEntryId 
	 */
	public File saveAttachedFile(File inputFile, Customer inputCustomer,LogEntry logEntryId);

	/**
	 * Opens a file in a suitable application
	 * @param inputFile File to open
	 * @return boolean
	 */
	public void openFileInNativeApp(File inputFile) throws Exception;
	
	/**
	 * Checks if the directory for a customer exists
	 * @param customer The customer object that needs a directory
	 * @return boolean
	 */
	public boolean checkForCustomerDir(Customer customer);
	/**
	 * Creates an excel file based on the master excel sheet and fills out the customers information
	 * @param inputCustomer The customer object to use when creating the excel-file
	 * @param logId The id of the logentry the excel-file will be attached to
	 * @param inputList A list of additional customer-objects to add to the excel-file 
	 * @return File 
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public File createExcel(LogEntry logId,ArrayList<Customer> inputList) throws InterruptedException, Exception;
}
