package wrapper;

import java.io.File;

import entity.Customer;
import entity.LogEntry;
/**
 * Interface for FileWrapper
 * @author Jon
 *
 */
public interface IFileWrapper 
{
	/**
	 * Saves a given file to the customers folder by copying it over.
	 * 
	 * @param inputFile 
	 * @param inputCustomer
	 * @param logEntryId	
	 * @return File
	 */
	public File saveFileToCustomerFolder(File inputFile, Customer inputCustomer, LogEntry logEntryId);
	
	/**
	 * Opens a given file in it's native applications as specified in the Operating system.
	 * @param inputFilePath Path to the file to open
	 * @return boolean
	 */
	public void openFileInNativeApp(File inputFile) throws Exception;
	
	/**
	 * Method for deleting a given file from the disk
	 * @param file Path to the file to open
	 */
	public void deleteFile(File file);

	
}
