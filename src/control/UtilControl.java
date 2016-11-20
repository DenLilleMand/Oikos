package control;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tables.State;
import entity.AttachedFile;
import entity.Customer;
import entity.Entity;
import entity.LogEntry;

/**
 * UtilControl.java In accordance with Singleton Pattern Class for controlling
 * the applications utilities.
 * 
 * @author Jon, Matti, Peter, Mads
 */

public final class UtilControl implements IUtilControl {
	/** Instance of DBControl to allow for easy access */

	private DBControl dbControl = DBControl.getInstance();
	/** Instance of State to allow for easy access */
	private State state = State.getInstance();
	/** Instance of FileControl to allow for easy access */
	private final FileControl fileControl = FileControl.getInstance();
	private final PdfControl pdfControl = PdfControl.getInstance();

	/** empty final string to use in excelfiles */
	private static final String ZERO = "";
	/** a space character stored as a char */
	private static final char SPACE = ' ';
	/** The only instance of UtilControl we allow to exist */
	private static UtilControl instance;

	/** Dateformatter to use for formatting dates */
	private DateFormat dateFormatter = new SimpleDateFormat(
			"dd/MM/yyyy - HH:mm");

	/**
	 * Private constructor - only to be accessed from within this class
	 */
	private UtilControl() {
		if (instance != null) {
			throw new IllegalStateException(
					"Cannot make new instance of singleton object");
		}
	}

	/**
	 * Returns the instance of UtilControl and creates it if not instantiated
	 * 
	 * @return UtilControl
	 */
	public static synchronized UtilControl getInstance() {
		if (instance == null) {
			instance = new UtilControl();
		}
		return instance;
	}

	/*
	 * Passes on the call further for logging in to the system
	 */
	public boolean attemptLogin(final String attemptedUsername,
			final String attemptedPasword) {
		return dbControl.attemptLogin(attemptedUsername, attemptedPasword);
	}

	/**
	 * Returns an entity with a given id
	 * 
	 * @param inputList
	 *            An arraylist of objects of type Entity
	 * @param search
	 *            The search parameters
	 * @return either Customer or LogEntry
	 */
	public <T extends Entity> T getSpecificElement(ArrayList<T> inputList,
			final int search) {

		for (T t : inputList) {
			if (t.getId() == search) {
				return t;
			}
		}
		return null;
	}

	public void setLogentryCheck(final ArrayList<LogEntry> inputList,
			final int search) throws Exception {
		for (LogEntry logEntry : inputList) {
			if (logEntry.getId() == search) {
				if (logEntry.getChecked().equalsIgnoreCase("unchecked")) {
					logEntry.setChecked("checked");
				} else {
					logEntry.setChecked("unchecked");
				}
			}
		}
	}

	/**
	 * Removes the customer from a list
	 */
	public void removeCustomerFromList(ArrayList<Customer> customerList,
			Customer customer) {
		for (Customer c : customerList) {
			if (c.getId() == customer.getId()) {
				customerList.remove(c);
				break;
			}
		}
	}

	/**
	 * Method for sorting out already added customers for the
	 * addCustomersToexcel functionality Does this by a nested for-loop checking
	 * against cpr numbers of already added customers for a given list
	 */
	public ArrayList<Customer> sortListForExcel(ArrayList<Customer> inputList) {
		ArrayList<Customer> outputList = new ArrayList<Customer>(inputList);
		for (Customer c : inputList) {
			for (Customer cc : state.getSelectedCustomersForExcel()) {
				if ((c.getId() == cc.getId())) {
					outputList.remove(c);
				}
			}
		}
		return outputList;
	}

	/**
	 * Some comments here...
	 * 
	 * @see control.IUtilControl#searchCustomers(java.lang.String,
	 *      java.lang.String)
	 */

	public ArrayList<Customer> searchCustomers(String search,
			String positionString) {

		if (positionString.equals("main")) {
			state.setCustomersInCustomerTable(dbControl.searchCustomer(search));
			return state.getCustomersInCustomerTable();
		}
		if (positionString.equals("excel")) {
			state.setCustomersForExcel(dbControl.searchCustomer(search));
			return state.getCustomersForExcel();

		}
		return null; // Handled upstream
	}

	/**
	 * runs through 3 of a customers attributes and checks whether a particular
	 * input from user is contained therein called within other search methods
	 * of utilControl.
	 * 
	 * @param customer
	 *            The customer to be searched through
	 * @param search
	 *            The parameter to search for
	 * @return an ArrayList of Customers
	 */

	public ArrayList<Customer> trimCustomerList(ArrayList<Customer> inputList,
			String search) {
		ArrayList<Customer> outputList = new ArrayList<Customer>();

		for (Customer c : inputList) {
			String wholeName = c.getFirstName() + SPACE + c.getLastName();
			if (wholeName.toLowerCase().startsWith(search.toLowerCase())
					|| // or
					wholeName.toLowerCase().contentEquals(search.toLowerCase())
					|| // or
					c.getCpr().toLowerCase().startsWith(search.toLowerCase()) || // or
					c.getCpr().toLowerCase()
							.contentEquals(search.toLowerCase()) || // or
					Integer.toString(c.getId()).toLowerCase()
							.startsWith(search.toLowerCase()) || // or
					Integer.toString(c.getId()).toLowerCase()
							.contentEquals(search.toLowerCase())) {
				outputList.add(c);
			}
		}
		return outputList;
	}

	/**
	 * Method for creating and saving a new excel file, filled out with a given
	 * customer's information as well as info from other added customers
	 */
	private File makeExcelSheet(final LogEntry inputLogEntry, ArrayList<Customer> inputList) throws InterruptedException,Exception 
	{
		for (Customer c : inputList) 
		{
			// adds information to the Customers in the inputList
			dbControl.additionalCustomerInfo(c);
		}
		// Next a Excel file with the customers attributes as input is created
		// and saved to disk
		File newExcelFile = fileControl.createExcel(inputLogEntry, inputList);
		return newExcelFile;
	}

	/**
	 * Method for obtaining the entire logEntryList for a given customer.
	 */
	public ArrayList<LogEntry> getLogEntryList(int selectedCustomerId) 
	{
		return dbControl.getLogEntryList(selectedCustomerId);
	}

	/**
	 * Method for deleting a logEntry, from both the local list of the
	 * selectedCustomer and from the database itself.
	 */
	public void deleteLogEntry(final LogEntry inputLog) 
	{
		// deletes the log entry from the Customers list of logentries
		// sends the delete call further to db
		dbControl.deleteLogEntry(inputLog);
	}

	/**
	 * call to fileControl to open a given file in it's native applikation This
	 * works across most platforms, is currently tested on Windows 7, 8, OSX and
	 * Ubuntu 13.10
	 */
	public void openFileInNativeApp(File inputFile) throws Exception 
	{
		fileControl.openFileInNativeApp(inputFile);
	}

	/**
	 * Method for saving a file, by saving the file to a specific place on the
	 * disk, aswell as saving the new filereference
	 */
	public void saveFile(final File fileToSave, Customer inputCustomer,
			LogEntry logEntry) throws InterruptedException {
		fileControl.checkForCustomerDir(inputCustomer);
		// only do it if the file actually exists
		if (fileToSave.exists()) {
			File file = fileControl.saveAttachedFile(fileToSave, inputCustomer,
					logEntry);
			dbControl.saveFileToDB(file.toString(), logEntry.getId());
		}
	}

	/*
	 * sleep method used to give the progressBar some time to update during
	 * upload of file.
	 */
	public void sleep() throws InterruptedException {
		Thread.sleep(10);
	}

	// /*
	// * Method for saving a different file to a already stored logEntry
	// */
	// public void editSavedFile(File fileToSave, Customer inputCustomer,
	// LogEntry inputLog) throws InterruptedException{
	// //deletes the original file associated with this logEntry
	// //Matti: these checks aren¨t valid - due to the fact that when we upload
	// a file into the program,
	// //that might be a .xlsx extension, and then we dont call deleteFile /
	// savedAttachedFile on it.
	//
	// // if(!inputLog.getFileExtension().equals("xlsx") ||
	// !inputLog.getFileExtension().equals("pdf"))
	// // {
	// sleep();
	// fileControl.deleteFile(inputLog.getFile());
	// //saves the new file through fileControl
	// sleep();
	// fileControl.saveAttachedFile(fileToSave, inputCustomer,inputLog.getId());
	// // }
	// //saves the new reference through DBControl using editFileReference
	// stored procedure
	// sleep();
	// dbControl.editFileReference(fileToSave.toString(), inputLog.getId());
	// }

	private void editSavedFileWithUploadedFile(File fileToSave, Customer inputCustomer, LogEntry inputLog) throws InterruptedException 
	{
		fileControl.deleteFile(inputLog.getAttachedFile());
		File outputFile = fileControl.saveAttachedFile(fileToSave,inputCustomer, inputLog);
		dbControl.editFileReference(outputFile.toString(), inputLog.getId());
	}

	private void editSavedFileWithPdfOrExcel(File fileToSave, Customer inputCustomer, LogEntry inputLog) throws InterruptedException 
	{
		fileControl.deleteFile(inputLog.getAttachedFile());
		dbControl.editFileReference(fileToSave.toString(), inputLog.getId());
	}

	/*
	 * Method for saving a different file to a already stored logEntry
	 */
	public void editSavedFile(File fileToSave, Customer inputCustomer,
			LogEntry inputLog) throws InterruptedException {
		// deletes the original file associated with this logEntry
		if (!inputLog.getAttachedFile().getFileExtension().equals("xlsx")) {
			sleep();
			fileControl.deleteFile(inputLog.getAttachedFile());
			// saves the new file through fileControl
			sleep();
			if (fileToSave.exists()) {
				fileControl.saveAttachedFile(fileToSave, inputCustomer,
						inputLog);
			}
		}
		// saves the new reference through DBControl using editFileReference
		// stored procedure
		sleep();
		dbControl.editFileReference(fileToSave.toString(), inputLog.getId());
	}

	/*
	 * Method for deleting a saved File, removes it from the disc, and the DB
	 */
	public void deleteSavedFile(final LogEntry inputLog) {
		try {
			// Deletes the file from the customer folder
			fileControl.deleteFile(inputLog.getAttachedFile());
			// Deletes the reference and row in DB;
			dbControl.deleteFileReference(inputLog.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method for creating a new Logentry First sets all attributes based on
	 * current time, loggged in user and given parameters, then procedes to
	 * handle the given fileCase, before saving the logEntry and perhaps
	 * filereference to the DB
	 */
	public int newLogEntry(String description, String comment, final File file,
			ArrayList<Customer> inputList) throws InterruptedException,
			Exception {

		Customer customer = state.getSelectedCustomer();
		LogEntry newLogEntry = new LogEntry();

		// createdBy / lastEdited:
		newLogEntry.setCreateDate(getCurrentUtilDate());
		newLogEntry.setLastEditDate(getCurrentUtilDate());

		// Current User
		newLogEntry.setCreatedBy(state.getCurrentUser().getName());
		newLogEntry.setLastEditBy(state.getCurrentUser().getName());

		// Comment
		newLogEntry.setComment(comment);

		// Description
		newLogEntry.setDescription(description);

		// customerId:
		newLogEntry.setCustomerId(customer.getId());

		// we save the logEntry itself here, as we need the LogEntryId for
		// saving a file
		// as LogEntryId is a FK in the fileTable.
		int logEntryId = dbControl.newLogEntry(newLogEntry);
		newLogEntry.setId(logEntryId);
		customer.addLogEntryToList(newLogEntry);

		switch (State.fileCase) {
		case FILE:
			break;

		case NEWEXCEL:
			// Lastly, the newly created ExcelFile is saved to the database
			dbControl.saveFileToDB(makeExcelSheet(newLogEntry, inputList)
					.toString(), logEntryId);
			break;
		case NONE:
			break;
		case EDITEXCEL:
			break;
		case EDITFILE:
			break;
		case NEWFILE:
			customer.setDankort(ZERO);
			if (file != null) {
				System.out.println("This is the new log id:"
						+ newLogEntry.getId());
				saveFile(file, customer, newLogEntry);

			}
			break;
		case NEWPDF:
			dbControl.additionalCustomerInfo(customer);
			dbControl.saveFileToDB(pdfControl.createPdf(customer, newLogEntry)
					.toString(), logEntryId);
			break;
		default:
			break;
		}
		return logEntryId;
	}

	/*
	 * Edits the info in a given logEntry to the new parametres. Can also handle
	 * change in the attached file
	 */

	public void editExistingLogEntry(LogEntry inputLog, final File inputFile,
			String comment, final String description,
			ArrayList<Customer> inputList) throws InterruptedException,
			Exception {
		try {
			Customer tempCustomer = state.getSelectedCustomer();
			inputLog.setLastEditBy(state.getCurrentUser().getName());
			inputLog.setLastEditDate(getCurrentUtilDate());
			inputLog.setComment(comment);
			inputLog.setDescription(description);

			dbControl.editLogEntry(inputLog);
			switch (State.fileCase) {
			case NEWFILE:
				if (inputFile != null) {
					saveFile(inputFile, tempCustomer, inputLog);
				}
				break;

			case EDITFILE:
				if (inputFile != null) 
				{
					editSavedFileWithUploadedFile(inputFile, tempCustomer,inputLog);
				}
				break;

			case EDITEXCEL:
				editSavedFileWithPdfOrExcel(makeExcelSheet(inputLog, inputList), tempCustomer,inputLog);
				break;

			case NONE:
				break;
			case FILE:
				break;
			case NEWEXCEL:
				break;
			case EDITNEWEXCEL:
				dbControl.saveFileToDB(makeExcelSheet(inputLog, inputList)
						.toString(), inputLog.getId());
				break;

			case EDITNEWPDF:
				dbControl.additionalCustomerInfo(tempCustomer);
				AttachedFile file = new AttachedFile(pdfControl.createPdf(
						tempCustomer, inputLog));
				dbControl.saveFileToDB(file.toString(), inputLog.getId());

				break;
			case DELETEFILE:
				deleteSavedFile(inputLog);
				inputLog.setAttachedFile(new File(ZERO));
				break;
			case NEWPDF:
				break;
			case EDITPDF:
				dbControl.additionalCustomerInfo(tempCustomer);
				editSavedFileWithPdfOrExcel(
						pdfControl.createPdf(tempCustomer, inputLog),
						tempCustomer, inputLog);
				// maybe make the selected customer null after such an
				// operation, because eventually we'll end up with
				// otherwise we can just rely on the CB.

				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Methods for converting dates to String and back
	 */
	public String dateToString(Date date) {
		return dateFormatter.format(date);
	}

	/*
	 * Returns a java.sql.Date for usage when saving date into the DB
	 */
	public Date getCurrentUtilDate() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		return cal.getTime();
	}

	public void openGuide() throws Exception {
		fileControl.openGuide();
	}

}
