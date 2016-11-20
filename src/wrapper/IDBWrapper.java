package wrapper;

import java.util.ArrayList;

import entity.Customer;
import entity.LogEntry;

/**
 * Interface for DBWrapper
 * @author Jon
 *
 */
public interface IDBWrapper {
	// Customer methods
	/**
	 * Takes in a search string, and using the StoredProcedure finds all customers with Name, customerId or Cpr
	 * That match or partially match this search string. Is only called open initial search, the rest of the sorting
	 * is handled in utilControl.search()
	 * @param search String containing search parameters
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> searchCustomer(String search);
	/**
	 * Search method for Customer objects. 
	 * Calls a Stored Procedure and puts result in an ArrayList of Customer objects to be returned
	 * @param customer Takes in Customer and not customerId to underline what kind of info the method is working with
	 * @return Customer
	 */

	public void additionalCustomerInfo(Customer customer);
	
	//LogEntry methods
	/**
	 * Method for saving a given logEntry to the DB using storedprocedure "newLogEntry"
	 * which makes a INSERT with the given variables as parameters for the new row
	 * returns the logEntryId for the new log if successful - 0 if not 
	 * @param inputLog The logentry to save in database
	 * @return int 
	 */
	public int newLogEntry(LogEntry inputLog);
	
	/**
	 * Method to get LogEntries for a selected Customer
	 * @param customerId Id for the customer who has the logentries
	 * @return ArrayList<LogEntry> 
	 */
	public ArrayList<LogEntry> getLogEntryList(int customerId);
	/**
	 * Method for changing the contents of a given logEntry in the Database using the storedprocedure
	 * "editLogEntry" which calls a UPDATE on the rows where he logId is the same.
	 * @param inputLog The edited logentry to be saved in the database
	 */
	public void editLogEntry(LogEntry inputLog);
	/**
	 * Method for deleting a logEntry from the DB using the storedprocedure "delteLogEntry"
	 * which deletes the LogEntry (Should probably also delete all rows affiliated with this logEntry)
	 * @param inputLog The logentry to be deleted
	 */
	public void deleteLogEntry(LogEntry inputLog);
	
	//File reference methods
	/**
	 * Method for saving a given fileReference to the Database with a logEntryId as ForeignKey
	 * @param fileReference The full path to the file
	 * @param logEntryId The id of the logentry
	 */
	public void saveFileToDB(String fileReference, int logEntryId);
	/**
	 * Method for deleting a Filereference in the DB. Does not interfere with the logEntry itself.
	 * @param logEntryId Id of the logentry to delete filereference for
	 */
	public void deleteFileReference(int logEntryId);
	
	//User methods
	/**
	 * Calls stored procedure createUser
	 * @param username The wanted username
	 * @param password The wanted password
	 * @param salt The salt obtained from UserControl - 24 bits
	 * @param realName The real name of the user
	 * @param userType The usertype of the user
	 */
	public void callCreateUser(String username, String password, String salt, String realName, int userType);
	
	// Connection methods
	/** 
	 * Takes input from LoginGui and tries to login as 'loginuser' in database by calling the other attemptLogin method
	 * loginuser only has SELECT on User, Insert on login_attempt, successful_login and execute privileges for the login-stored procedure
	 * Creates a user object, set as currentUser in State 
	 *  
	 * A boolean value is returned
	 * @param username the username entered
	 * @param password the password entered
	 * @return boolean did the user log in?
	 */
	public boolean attemptLogin(String username, String password);
	
		
}
