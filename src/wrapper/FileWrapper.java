package wrapper;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import tables.State;
import entity.Customer;
import entity.LogEntry;

/**
 * Singleton pattern
 * @author Peter, Matti
 *
 */
public final class FileWrapper implements IFileWrapper 
{
	
	private State state = State.getInstance();
	private final String ZERO = "";
	
	/** The only instance of FileWrapper we allow to exist */
	private static FileWrapper instance;
	
	/**
	 * private constructor - only to be accessed from within this class
	 */
	private FileWrapper()
	{
		if (instance!=null)
		{
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}
	
	/**
	 * Returns the instance of FileWrapper
	 * @return FileWrapper
	 */
	public static synchronized FileWrapper getInstance(){
		if (instance == null){
			instance = new FileWrapper();
		}
		return instance;
	}
	
	/**
	 * Method for saving a given input file to the customers filefolder.
	 * We don't delete the original file, nor move it. It is copied so
	 * the original file stays intact.
	 * It then returns the new file for later us if needed.
	 * @param inputFilePath 
	 * @param inputCustomer
	 * @param logEntryId	
	 * @return File
	 */
	public File saveFileToCustomerFolder(File inputFile, Customer inputCustomer, LogEntry logEntry)
	{
		//filePath refers to the path the user has provided
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss") ;
		File outputFile = new File(State.getInstance().getFilePath()
				+ File.separator + inputCustomer.getId() + File.separator + dateFormat.format(new Date()) + inputFile.getName());
				

		try
		{
			// Creates the outputFile
			outputFile.createNewFile();
			//Then copies it
			FileUtils.copyFile(inputFile, outputFile);
		}	
		catch (IOException i)
		{
		   i.printStackTrace();
		   return null;
		}
		return outputFile;
	}

	/**
	 * Opens a given file in it's native applications as specified in the Operating system.
	 * @param inputFilePath
	 * @return boolean
	 * @TODO should receive a file, not create one. a File has a path
	 */
	public void openFileInNativeApp(File inputFile) throws Exception 
	{
			Desktop.getDesktop().open(inputFile);
	}
	
	/**
	 * Method for deleting a given file from the disk
	 * @param inputFile File to delete
	 */
	public void deleteFile(File inputFile)
	{
		try
		{
			FileUtils.moveFileToDirectory(inputFile,getFile(ZERO + state.getSelectedCustomer().getId(), "skraldespand"), false);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private File getFile(String... paths) 
	{
		final StringBuilder sb = new StringBuilder(state.getFilePath());
		for (final String s : paths) {
			sb.append(File.separator).append(s);
		}
		return new File(sb.toString());
	}
}
