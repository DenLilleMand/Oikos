
package control;

import java.util.ArrayList;

import entity.Customer;
import entity.LogEntry;

/**
 * All methods in DBControl are public, so all methods are declared here
 * IDBControl.java
 * @author Jon
 */
public interface IDBControl {
	/**
	 * passes on the call from Utilcontrol to DBWrapper
	 * @param attemptedUsername the entered username
	 * @param attemptedPassword The entered password
	 * @return boolean
	 */
	public boolean attemptLogin(String attemptedUsername, String attemptedPassword);
	/**
	 * * Method to retrieve rest of selectedCustomers attributes from db. Takes selectedCustomer as parameter, since we don't want connection to utilControl
	 * Passes on the call to DBWrapper 
	 * @param selectedCustomer
	 * @return Customer
	 */
	public void additionalCustomerInfo(Customer c);
	/**
	 * Passes on the call to DBWrapper
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> searchCustomer(String search);
	/**
	 * Passes on the call to DBWrapper
	 * Returns the LogEntries with a given selectedCustomerId as FK as called from dbWrapper in ArrayList
	 * @return ArrayList<LogEntry>
	 */
	public ArrayList<LogEntry> getLogEntryList(int selectedCustomerId);
	/**
	 * Passes on the call to DBWrapper
	 * @return int
	 */
	public int newLogEntry(LogEntry inputLog);
	/**
	 * Passes on the call to DBWrapper
	 * @param inputLog
	 */
	public void editLogEntry(LogEntry inputLog);
	/**
	 * Passes on the call to DBWrapper
	 * @param inputLog
	 */
	public void deleteLogEntry(LogEntry inputLog);
	/**
	 * Passes on the call to DBWrapper
	 * @param fileReference
	 * @param logEntryId
	 */
	public void saveFileToDB(String fileReference, int logEntryId);
	/**
	 * Passes on the call to DBWrapper
	 * @param logEntryId
	 */
	public void deleteFileReference(int logEntryId);
}
