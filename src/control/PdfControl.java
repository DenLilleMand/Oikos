package control;

import java.io.File;

import static constants.StringConstant.*;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import tables.State;
import entity.Customer;
import entity.LogEntry;

public final class PdfControl
{
	static final Logger logger = Logger.getLogger(PdfControl.class);
	private static PdfControl instance;
	private FileControl fileControl = FileControl.getInstance();
	private DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	/** private constructor - only to be accessed from within this class */
	private PdfControl()
	{
		if(instance!=null)
		{
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}

	/** 
	 * Returns the only instance of PdfControl we allow to exist - creates it if it doesn't exist
	 * @return PdfControl
	 */
	public static synchronized PdfControl getInstance(){
		if(instance==null)
		{
			instance = new PdfControl();
		}
		return instance;
	}

	/**
	 * This method has a problem because it has a relation to the State class,
	 * which isn't allowed at all, should get all the information from parameters. 
	 * not quite sure why we're not just checking on the input customer, 
	 * since we have it as a parameter.
	 * 
	 * 
	 * @param inputCustomer
	 * @param logId
	 * @return
	 * @throws Exception
	 */
	public File createPdf(Customer inputCustomer, LogEntry logId) throws Exception
	{
		if (fileControl.checkForCustomerDir(State.getInstance().getSelectedCustomer()) == false)
		{
			return null;
		}
		BasicConfigurator.configure();
		sleep();
		File masterFile = getRessource("NetbankenMedForms.pdf");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss") ;
		File customerFile = getFile(ZERO + inputCustomer.getId(), "netbank" + logId.getId()+ dateFormat.format(new Date())+".pdf");
		System.out.println(customerFile.toString());
		customerFile.createNewFile();
		FileUtils.copyFile(masterFile, customerFile);
		sleep();
		
		FileInputStream input = new FileInputStream(customerFile);
		PDDocument doc = (PDDocument) PDDocument.load(input);
		PDDocumentCatalog docCatalog = doc.getDocumentCatalog();

		PDAcroForm acroForm = docCatalog.getAcroForm(); 
		sleep();
		
		PDField field = acroForm.getField("navn"); 
		field.setValue(inputCustomer.getFirstName() + SPACE + inputCustomer.getLastName());
		
		field = acroForm.getField("postnummerogby"); 
		field.setValue(inputCustomer.getPostalArea() + SPACE + inputCustomer.getCity());
		
		field = acroForm.getField("vejnavnognummer");
		field.setValue(inputCustomer.getStreetName() + SPACE + inputCustomer.getStreetNumber() + inputCustomer.getApartmentNumberOrSide());
		sleep();
		
		field = acroForm.getField("cprcvr"); 
		if(!inputCustomer.getCvr().equals(ZERO))
		{
			field.setValue(inputCustomer.getCvr());
		}
		else 
		{
			field.setValue(inputCustomer.getCpr());
		}
		sleep();
		
		
		field = acroForm.getField("kundenummer"); 
		field.setValue(ZERO + inputCustomer.getId());
		
		field = acroForm.getField("kontonummerrow1"); 
		field.setValue(inputCustomer.getAccountNumber());
		sleep();
		field = acroForm.getField("kontonummer"); 
		field.setValue(inputCustomer.getAccountNumber());
		
		field = acroForm.getField("brugernavn"); 
		field.setValue(State.getInstance().getCurrentUser().getName());
		sleep();
		field = acroForm.getField("dato"); 
		field.setValue(ZERO + dateFormatter.format(new Date()));

	    doc.save(customerFile);
	    doc.close();
		return customerFile;
	}
	
	private void sleep() throws Exception
	{
		Thread.sleep(10);
	}
	
	private File getRessource(String... paths) 
	{
		String path = FileUtils.getUserDirectory() + File.separator + APP_NAME;
		StringBuilder sb = new StringBuilder(path);
		for (String s : paths) 
		{
			sb.append(File.separator).append(s);
		}
		return new File(sb.toString());
	}	
	private File getFile(String... paths) 
	{
		final StringBuilder sb = new StringBuilder(State.getInstance().getFilePath());
		for (String s : paths) 
		{
			sb.append(File.separator).append(s);
		}
		return new File(sb.toString());
	}	
}
