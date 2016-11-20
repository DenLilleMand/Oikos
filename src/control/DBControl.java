package control;

import static constants.StringConstant.*;
import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import entity.Customer;
import entity.LogEntry;
import tables.State;
import wrapper.DBWrapper;

/**
 * DBControl.java. 
 * In accordance with Singleton Pattern. The only class that connects with DBWrapper
 * 
 * In it's current form, DBControl is merely passing on methodcalls
 * from UtilControl to the DBWrapper. It is kept in place, as we might wan't to move
 * functionality from utilControl to DBControl in the future.
 * 
 * Public final because of no inheritance
 * @author Jon, Mads, Matti, Peter
 */
public final class DBControl implements IDBControl{
	/** The only instance of DBControl we allow to exist */
	private static DBControl instance;
	/** Local reference to dbWrapper for easy access */
	private final DBWrapper dbWrapper = DBWrapper.getInstance();
	
	/**
	 * Private constructor - only to be accessed from within this class
	 * if condition is to protect against reflection
	 */
	private DBControl(){
		if (instance!=null){
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}
	
	/**
	 * Checks if instance of db control exists
	 * @comment Synchronized to ease further development
	 * @return Returns the instance of DBControl - creates it if it doesn't exist
	 */
	public static synchronized DBControl getInstance(){
		if(instance==null){
			instance = new DBControl();
		}
		return instance;		
	}
	
	/*
	 * passes on the call from Utilcontrol to DBWrapper
	 */
	public boolean attemptLogin(final String attemptedUsername, final String attemptedPasword){
		return dbWrapper.attemptLogin(attemptedUsername, attemptedPasword);
	}
	
	/*
	 * Method to retrieve rest of a Customer's attributes from db. Takes selectedCustomer as parameter, since we don't want connection to utilControl
	 * Passes on the call to DBWrapper 
	 */
	public void additionalCustomerInfo(final Customer selectedCustomer){
		dbWrapper.additionalCustomerInfo(selectedCustomer);
	}
	
	/*
	 * Passes on the call to DBWrapper
	 */
	public ArrayList<Customer> searchCustomer(final String search){
		return dbWrapper.searchCustomer(search);
	}

	/*
	 * Passes on the call to DBWrapper
	 * Returns the LogEntries with a given selectedCustomerId as FK as called from dbWrapper in ArrayList
	 */
	public ArrayList<LogEntry> getLogEntryList(int selectedCustomerId){
		ArrayList<LogEntry> outputList = dbWrapper.getLogEntryList(selectedCustomerId);

		//The reason for this for loop is to be able to show which file type is attached in the LogEntryTable in MainGUI
		for(LogEntry log : outputList){
			if(!(log.getAttachedFile().toString().equals(ZERO)))
			{
				String extension = FilenameUtils.getExtension(log.getAttachedFile().toString()); //getting file extension to show in JTable
				System.out.println(extension);
				String fileName = FilenameUtils.getBaseName(log.getAttachedFile().toString());
				log.setAttachedFile(new File(State.getInstance().getFilePath()+ File.separator + selectedCustomerId + File.separator + fileName + "." + extension));
				log.getAttachedFile().setFileExtension(extension.toLowerCase());		
			}
				log.setChecked("unchecked");//lav til enum lol - og optimer den her kode,
				//ingen grund til at have nogle String objekter skabt n�r vi kan ligge dem direkte ind i den her variable.
		}
		return outputList;
	}
	
	/*
	 * Passes on the call to DBWrapper
	 */
	public int newLogEntry(final LogEntry inputLog){
		return dbWrapper.newLogEntry(inputLog);	
	}
	
	/*
	 * Passes on the call to DBWrapper
	 */
	public void editLogEntry(final LogEntry inputLog){
		dbWrapper.editLogEntry(inputLog);
	}
	
	/*
	 * Passes on the call to DBWrapper
	 */
	public void deleteLogEntry(final LogEntry inputLog){
		dbWrapper.deleteLogEntry(inputLog);
	}
	
	/*
	 * Passes on the call to DBWrapper
	 */
	public void saveFileToDB(final String fileReference,final int logEntryId){
		dbWrapper.saveFileToDB(fileReference, logEntryId);
	}
	/*
	 * Passes on the call to DBWrapper
	 */
	public void editFileReference(final String fileToSave, final int logEntryId){
		dbWrapper.editFileReference(fileToSave, logEntryId);
	}
	
	/*
	 * Passes on the call to DBWrapper
	 */
	public void deleteFileReference(final int logEntryId){
		dbWrapper.deleteFileReference(logEntryId);
	}

}
