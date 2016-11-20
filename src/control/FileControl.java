package control;

import static constants.StringConstant.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import entity.Customer;
import entity.LogEntry;
import wrapper.FileWrapper;
import tables.State;


/**
 * FileControl.java
 * In accordance with Singleton Pattern.
 * The only class that connects with FileWrapper. 
 * 
 * (matti)-When we're going to rework this class, which we will have to, 
 * eventually, we will have to consider making it a immutable utility
 * class without any fields that can violete it's internal representation,
 * and on top of that, we would allso prepare ourselfes for harder times when 
 * we have to take a interface with the given excel co-ordinates,
 * so that we can support several different excel sheets by just changeing a input
 * object.   To pick even further on this class, it breaks the 1-responsibility
 * rule several times, this class responsibility is both standard utility wise,
 * and responsible for auto-generateing excel sheets, we have to move utility into
 * an immutable static utility class, and we have to seperate the excel creation into a
 * class file of it's own to lessen the responsibility of this class,
 * we allso have to remove dependencies of this class,
 * it SIMPLY CANNOT(!!) know a  tables.state and a entity.Customer
 * class, it's just not healthy dependecies and it makes it impossible
 * for us to track method calls and to check where some state field was
 * edited from if everybody has access.i think we should very much consider
 * making the state class a bit more encapsulated, so that only specific classes
 * can change it's internal representation, but keep it so that many classes
 * can get a copy of this representation, in order to do some computation.
 * 
 * 
 * 
 * @author Matti, Peter
 */
public final class FileControl implements IFileControl{
	/** The only instance of FileControl we allow to exist */
	private static FileControl instance;
	/** local reference to filewrapper for easy access */
	private final FileWrapper fileWrap = FileWrapper.getInstance();
	/** Local reference to the state*/
	private final State state = State.getInstance();
	/** char representation of a space character */

	private Customer mainCustomer;
	private String mainName;	
	private String mainAddress;
	private String mainAccountNumber;
	private String mainCpr;
	
	private CellStyle style;
	private String userRealname;
	
	private ArrayList<Customer> customerList;
	
	/**
	 * Private constructor - only to be accessed from within this class
	 */
	private FileControl(){
		if (instance != null){
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}

	/**
	 * Returns the only instance of FileControl we allow to exist
	 * @comment synchronized to ease further development 
	 * @return Filecontrol instance
	 */
	public static synchronized FileControl getInstance() {
		if (instance == null){
			instance = new FileControl();
		}
		return instance;
	}
	/**
	 * Method for saving a given file to a customers file folder.
	 * Sends the call to the filewrapper and returns the new filepath as a file.
	 * @param inputFile The file to be saved
	 * @param inputCustomer Customer object
	 * @return File 
	 */
	public File saveAttachedFile(final File inputFile, final Customer inputCustomer, final LogEntry logEntryId) {
		return fileWrap.saveFileToCustomerFolder(inputFile, inputCustomer, logEntryId);
	}

	/**
	 * Forwarding request to filewrapper to open file in OS native application
	 * @param inputFile File to be opened in it's native app
	 * @return boolean wether the open was succesfull or not.
	 */
	public void openFileInNativeApp(final File inputFile) throws Exception{
		fileWrap.openFileInNativeApp(inputFile);
	}

	/**
	 * Forwarding request to filewrapper to delete a given file
	 * @param inputFile File object representing file to be deleted
	 */
	public void deleteFile(final File inputFile){
		if(!inputFile.exists())
		{
			return;
		}
		fileWrap.deleteFile(inputFile);
	}

	/**
	 * Method is used for checking if a customer directory exists, and if it doesn't, tries to create one for the 
	 * given customer.
	 * @param customer is used to to identify the correct folder
	 * @return boolean return whether a new folder was created or not
	 * 
	 */
	public boolean checkForCustomerDir(final Customer customer){
		File f = getFile(ZERO + customer.getId());
		System.out.println("check for customer dir: "  +f.toString());
		boolean bool = true;
		if (!f.exists()){
			try
			{
				File finalFile = getFile(ZERO + customer.getId(),"skraldespand");
				FileUtils.forceMkdir(finalFile);
			} 
			catch (Exception e) {
				bool = false;
			}
		}
		return bool;
	}
	
	/**
	 * createExcel - the main method in this class, it's the one that creates the customerFile, copies the 
	 * masterExcel sheet into the customerFile(that is actually a functionality that should've been split up into another method
	 * and this should've just taken in a file as parameter),  we create all of the variables used for all of the excel methods
	 * in this class in this method, we set the font, the style, address, name etc.
	 * it's also this class where we make the XSSF-WorkBook, and the sheet variable given as parameter to all of the methods.
	 * we also have to call sleep a couple of times because this method is so dependant on the progressBar being updated
	 * during and after. we return a file in order to save the newly made excelfile. 
	 * @param inputCustomer Customer object to base excel file on
	 * @param logId Id of the log
	 * @param inputList Arraylist<Customer> to include in file
	 * @return file - newly created Excelfile
	 */

	public File createExcel(final LogEntry logEntry, final ArrayList<Customer> inputList) throws InterruptedException, Exception 
	{
		if (checkForCustomerDir(state.getSelectedCustomer()) == false)
		{
			return null; // handle this null value in the "calling" class.
		}
		setCustomerInfo(inputList);

		userRealname = state.getCurrentUser().getName();
		sleep();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss") ;
		final File inputCustomerFile = getFile(ZERO + mainCustomer.getId(), "excel" + dateFormat.format(new Date()) + ".xlsx");
		
		
		mainName = mainCustomer.getFirstName() + SPACE + mainCustomer.getLastName();
		mainAddress = makeAddress(mainCustomer);
		sleep();

		final File masterFile = getFileForRessource("excelark.xlsx");
		
		inputCustomerFile.createNewFile();
		FileUtils.copyFile(masterFile, inputCustomerFile);
		InputStream input = new FileInputStream(inputCustomerFile);
		Workbook inputCustomerWorkbook = WorkbookFactory.create(input);

		sleep();

		style = inputCustomerWorkbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		Font font = inputCustomerWorkbook.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 9); // 9 is font size
		style.setFont(font);
		sleep();

		Sheet sheet = inputCustomerWorkbook.getSheet("Underskrift");
		createUnderskriftSheet(sheet);
		sleep();

		sheet = inputCustomerWorkbook.getSheet("Ovf. engagement 1");
		createOvfSheet(sheet);

		sheet = inputCustomerWorkbook.getSheet("Ovf. engagement 2");
		createOvfSheet(sheet);
		sleep();

		sheet = inputCustomerWorkbook.getSheet("Dankort 1");
		createDankort(sheet, mainCustomer);

		sleep();
		
		//if no dankort2, then no reason to create sheet, therefore we check it before calling the create method.
		for (final Customer c : customerList) {
			if (c.getDankort().equalsIgnoreCase("card")){
				sheet = inputCustomerWorkbook.getSheet("Dankort 2");
				createDankort(sheet, c);
			}
		}
		sleep();

		sheet = inputCustomerWorkbook.getSheet("Arbejdsgiver 1");
		createArbejdsgiver(sheet);
		sleep();

		sheet = inputCustomerWorkbook.getSheet("Arbejdsgiver 2");
		createArbejdsgiver(sheet);
		sleep();

		sheet = inputCustomerWorkbook.getSheet("Flytning PBS");
		createFlytningPBS(sheet);
		sleep();

		sheet = inputCustomerWorkbook.getSheet("Åbning af konti i Fksp.");
		createAabningAfKontiIFksp(sheet);
		sleep();

		FileOutputStream output = new FileOutputStream(inputCustomerFile);
		inputCustomerWorkbook.write(output);
		output.close();
		input.close();
		mainCustomer.setDankort(ZERO);
		return inputCustomerFile;
		}
	
	
	//sets inputCustomer as the instance variable refering to the selected customer
	//furthermore sets various widely used properties for easy access
	//and mainCustomer as the first element of inputList, finally sets the sorted
	//inputList as the instance variable customerList for easy access
	private void setCustomerInfo(ArrayList<Customer> inputList){
		for(final Customer c : inputList)
		{
			if(c.getCpr().equalsIgnoreCase(state.getSelectedCustomer().getCpr()))
			{
				System.out.println("this bracket of code is being run");
				mainCustomer = c;
				mainAccountNumber = c.getAccountNumber();
				mainCpr = c.getCpr();
	
				inputList.set(0, c);
				break;
			}
		}
		customerList = inputList;
	}
	
	/**
	 * Takes a sheet in as parameter, and writes customer info into hardcoded cells/columns,
	 * so it does depend on the correct "sheet".  specifically for the underskrift sheet.
	 * @param sheet Sheet to work on
	 * @throws Exception
	 */
	
	private void createUnderskriftSheet(final Sheet sheet){
		
		Row row = sheet.getRow(7);
		row.createCell(33).setCellValue(mainCustomer.getId());
		row.getCell(33).setCellStyle(style);
		
		row = sheet.getRow(31);
		row.createCell(37).setCellValue(userRealname);
		row.getCell(31).setCellStyle(style);

		row = sheet.getRow(9);
		row.createCell(0).setCellValue(mainAccountNumber);
		row.getCell(0).setCellStyle(style);

		row = sheet.getRow(18);
		row.createCell(39).setCellValue(mainAccountNumber);
		row.getCell(39).setCellStyle(style);

		row = sheet.getRow(21);
		row.createCell(39).setCellValue(mainAccountNumber);
		row.getCell(39).setCellStyle(style);

		row = sheet.getRow(23);
		row.createCell(39).setCellValue(mainAccountNumber);
		row.getCell(39).setCellStyle(style);

		row = sheet.getRow(9);
		row.createCell(5).setCellValue(mainCustomer.getAccountType());
		row.getCell(5).setCellStyle(style);

		row = sheet.getRow(22);
		row.createCell(3).setCellValue(mainCustomer.getPhone());
		row.getCell(3).setCellStyle(style);

		row = sheet.getRow(22);
		row.createCell(11).setCellValue(mainCustomer.getMobilePhone());
		row.getCell(11).setCellStyle(style);

		row = sheet.getRow(22);
		row.createCell(26).setCellValue(mainCustomer.getEmail());
		row.getCell(26).setCellStyle(style);
		
		//for the part of the sheet where more customers can be added
		int rowNumber = 19;
		for (Customer c: customerList){	
			row = sheet.getRow(rowNumber);
			row.createCell(0).setCellValue(c.getFirstName() + SPACE + c.getLastName());
			row.getCell(0).setCellStyle(style);
	
			row = sheet.getRow(rowNumber);
			row.createCell(14).setCellValue(c.getCpr());
			row.getCell(14).setCellStyle(style);
	
			row = sheet.getRow(rowNumber);
			row.createCell(19).setCellValue(makeAddress(c));
			row.getCell(19).setCellStyle(style);
			
			rowNumber++;	
		}
	}

	/**
	 * Takes a sheet in as parameter, and writes customer info into hardcoded cells/columns,
	 * so it does depend on the correct "sheet". specifically for the ovf 1 sheet.
	 * @param sheet Sheet to work on
	 * @throws Exception
	 */
	private void createOvfSheet(final Sheet sheet){
		

		Row row = sheet.getRow(2);
		row.createCell(26).setCellValue(userRealname);
		row.getCell(26).setCellStyle(style);
		
		final int cprCell = 14;
		final int nameCell = 1;
		final int addressCell = 21;
		int rowNumber = 12;
			
		for (final Customer c : customerList){
			row = sheet.getRow(rowNumber);
			row.createCell(nameCell).setCellValue(c.getFirstName() + SPACE + c.getLastName());
			row.getCell(nameCell).setCellStyle(style);
	
			row = sheet.getRow(rowNumber);
			row.createCell(cprCell).setCellValue(c.getCpr());
			row.getCell(cprCell).setCellStyle(style);
	
			row = sheet.getRow(rowNumber);
			row.createCell(addressCell).setCellValue(makeAddress(c));
			row.getCell(addressCell).setCellStyle(style);
			rowNumber++;

		}
	}

	/**
	 * 
	 * Takes a sheet in as parameter, and writes customer info into hardcoded cells/columns.
	 * With respect to Dankort 2 it's not the mainCustomer, therefore its take in any customer as parameter
	 * @param sheet Sheet to work on
	 * @throws Exception
	 */
	private void createDankort(final Sheet sheet, final Customer c){
		
		Row row = sheet.getRow(2);
		row.createCell(0).setCellValue(c.getFirstName() + SPACE + c.getLastName());
		row.getCell(0).setCellStyle(style);

		row = sheet.getRow(2);
		row.createCell(11).setCellValue(mainAddress);
		row.getCell(11).setCellStyle(style);

		row = sheet.getRow(3);
		row.createCell(3).setCellValue(c.getRegistrationNumber());
		row.getCell(3).setCellStyle(style);

		row = sheet.getRow(3);
		row.createCell(10).setCellValue(c.getAccountNumber());
		row.getCell(10).setCellStyle(style);

		row = sheet.getRow(3);
		row.createCell(20).setCellValue(c.getCpr());
		row.getCell(20).setCellStyle(style);
	}



	/**
	 * Takes a sheet in as parameter, and writes customer info into hardcoded cells/columns,
	 * so it does depend on the correct "sheet".specifically for the arbejdsGiver1 sheet.
	 * @param sheet
	 * @throws Exception
	 */
	private void createArbejdsgiver(final Sheet sheet){
		
		final int cellValue = 3;
		Row row = sheet.getRow(10);
		
		row.createCell(cellValue).setCellValue(mainCpr);
		row.getCell(cellValue).setCellStyle(style);

		row = sheet.getRow(12);
		row.createCell(cellValue).setCellValue(mainName);
		row.getCell(cellValue).setCellStyle(style);

		row = sheet.getRow(13);
		row.createCell(cellValue).setCellValue(mainAddress);
		row.getCell(cellValue).setCellStyle(style);
	}


	/**
	 * Takes a sheet in as parameter, and writes customer info into hardcoded cells/columns,
	 * so it does depend on the correct "sheet". specifically for the ÅbningAfKontiIFksp sheet.
	 * @param sheet
	 * @throws Exception
	 */
	private void createAabningAfKontiIFksp(final Sheet sheet){
		
		final int nameCell = 0;
		final int cprCell = 14;
		final int addressCell = 19;
		
		Row row = sheet.getRow(5);
		row.createCell(17).setCellValue(mainCustomer.getAccountNumber());
		row.getCell(17).setCellStyle(style);

		int rowNumber = 24;
		
		for (final Customer c : customerList){
			row = sheet.getRow(rowNumber);
			row.createCell(nameCell).setCellValue(c.getFirstName() + SPACE + c.getLastName());
			row.getCell(nameCell).setCellStyle(style);
	
			row = sheet.getRow(rowNumber);
			row.createCell(cprCell).setCellValue(c.getCpr());
			row.getCell(cprCell).setCellStyle(style);
	
			row = sheet.getRow(rowNumber);
			row.createCell(addressCell).setCellValue(makeAddress(c));
			row.getCell(addressCell).setCellStyle(style);
			rowNumber++;

		}
	}

	/**
	 * Takes a sheet in as parameter, and writes customer info into hardcoded cells/columns,
	 * so it does depend on the correct "sheet". specifically for the FlytningPBS sheet.
	 * @param sheet
	 * @throws Exception
	 */
	
	private void createFlytningPBS(final Sheet sheet){
		Row row = sheet.getRow(19);
		row.createCell(7).setCellValue(mainCpr);
		row.getCell(7).setCellStyle(style);

		row = sheet.getRow(26);
		row.createCell(7).setCellValue(mainCpr);
		row.getCell(7).setCellStyle(style);

		row = sheet.getRow(19);
		row.createCell(2).setCellValue(mainAccountNumber);
		row.getCell(2).setCellStyle(style);

		row = sheet.getRow(26);
		row.createCell(2).setCellValue(mainAccountNumber);
		row.getCell(2).setCellStyle(style);

		row = sheet.getRow(19);
		row.createCell(18).setCellValue(mainName);
		row.getCell(18).setCellStyle(style);

		row = sheet.getRow(26);
		row.createCell(18).setCellValue(mainName);
		row.getCell(18).setCellStyle(style);
	}


	/**
	 * getFile method.  takes in a various amounts of Strings and makes a String[] - 
	 * creates a new StringBuilder object of which it appends word for word and puts a fileseperator inbetween.
	 * and in the end - returns  a file.
	 * @param paths
	 * @return File
	 */
	private File getFile(String... paths) 
	{
		final StringBuilder sb = new StringBuilder(State.getInstance().getFilePath());
		for (final String s : paths) 
		{
			sb.append(File.separator).append(s);
		}
		return new File(sb.toString());
	}
	
	private File getFileForRessource(String... paths) 
	{
		String path = FileUtils.getUserDirectory() + File.separator + APP_NAME;
		final StringBuilder sb = new StringBuilder(path);
		for (final String s : paths) {
			sb.append(File.separator).append(s);
		}
		return new File(sb.toString());
	}

	/**
	 * Used in order to keep the code as brief as possible, and then we only have to change the sleep timer one place.
	 * @throws InterruptedException 
	 */
	
	
	private void sleep() throws InterruptedException{
		Thread.sleep(20);
	}

	/**
	 * Making the address using a final String space in order to limit CPU time, 
	 * and we also just create one object, the String builder instead of making 9 different String objects.
	 * @param inputCustomer The customer to create an address string for
	 * @return String the combined address from a given inputcustomer.
	 */
	private String makeAddress(final Customer inputCustomer) 
	{
		StringBuilder sb = new StringBuilder();

		sb.append(inputCustomer.getCo()).append(SPACE).
		append(inputCustomer.getStreetName()).append(SPACE).
		append(inputCustomer.getStreetNumber()).append(SPACE).
		append(inputCustomer.getFloor()).append(SPACE).
		append(inputCustomer.getApartmentNumberOrSide()).append(SPACE).
		append(inputCustomer.getPostalArea()).append(SPACE).
		append(inputCustomer.getZipcode()).append(SPACE).
		append(inputCustomer.getCity()).append(SPACE).
		append(inputCustomer.getCountry());
		return sb.toString();
	}

	public void openGuide() throws Exception
	{
		Desktop.getDesktop().open(getFileForRessource("OlympusBrugervejledning.pdf"));
	}


}
