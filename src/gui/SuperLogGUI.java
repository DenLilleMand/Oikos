
package gui;
import static constants.StringConstant.*;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.Button;
import components.CustomTitledBorder;
import components.Label;
import components.TextArea;
import components.TextField;

import java.io.File;
import java.util.ArrayList;

import tables.State;
import mediator.Mediator;
import control.UtilControl;
import entity.Customer;

/**
 * SuperLogGUI. 
 * Takes care of layout and default ActionListener for log-related GUIs
 * 
 * @author Matti, Peter, Jon
 */
public abstract class SuperLogGUI extends JDialog implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 3886583535937809795L;

	protected GridBagConstraints c;
	
	//MultipleCustomersForExcelDialog
	protected MultipleCustomersForExcelDialog customersForExcelDialog;

	// JPanels -
	protected JPanel editablePanel, uneditablePanel , backgroundPanel, customerPanel, overWriteFilePanel, newExcelPanel,
	moreCustomersOverWriteCardPanel,descriptionPanel,masterCardPanel, excelPanel, filePanel, emptyPanel,editPdfPanel, newPdfPanel,
	superPdfPanel, buttonPanel;
	
	protected ActionListener multipleCustomersForExcelListener;
	
	/** variables with complimentary labels/Fields (even though we're not able to
	 * edit these values, the values that varies
	 * from customer to customer still needs to be JTextFields instead of
	 * JLabels due to the fact that they will resize the entire parent component if some1 has a retardedly long name
	 */

	protected Label attachFilePath , customerIdLabel, customerNameLabel, addedToExcelLabel, editedByLabel, createdByLabel,
	createdDateLabel, lastEditDateLabel, bogusLabel, overwriteFileLabel, fileLabel,excelLabel,
	optionsLabel, customersForExcelLabel,excelCheckLabel,fileCheckLabel,pdfCheckLabel,commentAreaLabel,
	descriptionLabel,attachFileLabel, overwritePdfLabel, deletePdfLabel, pdfLabel, attachedPdfLabel, cprLabel;
	
	protected TextField customerNameField, customerIdField, editedByField, createdByField, createdDateField, lastEditDateField,
	complimentaryDescriptionField, attachedFileField, pdfField, cprField;
	
	protected JCheckBox newExcelFileBox,excelCheckBox,pdfCheckBox, fileCheckBox,overwritePdfCheckBox;
	
	protected Button confirmBTN , cancelBTN , attachFileBTN, addMoreToExcelButton, addMoreToExcel1Button, deleteFileButton,
	deletePdfButton;
	
	protected CardLayout cl, cl1, clDescription;
	//for description cardlayout
	
	//Jon: following documentation, boolean values are false by default. Therefore inializing a false boolean value is redundant.
	protected boolean newExcelFile;
	
	protected JFileChooser fileChooser;
	
	protected TextArea commentArea;
	
	protected final static String EMPTY = "empty";
	protected JComboBox<String> descriptionBox, customersForExcelBox;
	
	protected File attachedFile;
	
	protected JScrollPane scrollPane;

	// borders
	protected CustomTitledBorder uneditablePanelBorder, editablePanelBorder, customerPanelBorder, tempBorder,buttonBorder;
	
	

	protected String description,fileCase;

	protected static final String[] DESCRIPTION = {"Engagement afsendt", "Engagement modtaget", "Dankort afsendt", 
			"arbejdsgiverkonto afsendt", "arbejdsgiverkonto modtaget","Etablering af konto","Andet"};
	
	protected UtilControl utilControl;
	protected Mediator mediator;
	protected State state;
	
	protected ArrayList<Button> buttonList = new ArrayList<Button>();
	protected ArrayList<TextField> fieldList = new ArrayList<TextField>();
	protected ArrayList<Customer> listForComboBox = new ArrayList<Customer>(); //array list for comboBox
	protected ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();


	public SuperLogGUI(final String logType)
	{	
		/**The String logType is for setting name in the JDialog
		 * mainGui is the parentFrame of the super class JDialog
		*/
		super(Mediator.getInstance().getMainGui(), logType, true); //The dialog is created
		mediator = Mediator.getInstance();
		utilControl = UtilControl.getInstance();
		state = State.getInstance();
		state.setFileCase(null);
		
		mediator.setSuperLogGui(this);
		// modal precautions
				setModal(true);
			    addWindowFocusListener(new WindowFocusListener() 
			    {
			        @Override
			        public void windowGainedFocus(final WindowEvent event) 
			        {
			           toFront();        
			        }
					@Override
					public void windowLostFocus(WindowEvent event) {}
			    });
		

	   	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	   	this.setSize(500,750);
	   	this.setLocationRelativeTo(mediator.getMainGui());     
	   	this.setResizable(false);
	}
	
	protected void setComponent(Insets insets, Container parent, Component component, int xSize, int ySize)
	{
		c.insets = insets;
		c.ipady = ySize;
		c.ipadx = xSize;
		parent.add(component,c);
	}
	

	protected void doThreadSafeWork(final int caseNumber) 
	{
		if (SwingUtilities.isEventDispatchThread()) 
		{
			switch (caseNumber) 
			
			{
			case 1:
				if(customersForExcelBox.getItemCount() > 0)
				{
					customersForExcelBox.setSelectedIndex(0);
				}
				break;
			case 2:
				dispose();
				break;
				
			case 3:
				attachFileBTN.setEnabled(false);
				break;
				
			case 4:
				attachFileBTN.setEnabled(true);
				break;
				
			case 5:
				excelCheckBox.setSelected(true);
				fileCheckBox.setSelected(false);
				pdfCheckBox.setSelected(false);
				cl = (CardLayout)(masterCardPanel.getLayout());
				cl.show(masterCardPanel, "excel");
				break;
				
			case 6:
				excelCheckBox.setSelected(false);
				fileCheckBox.setSelected(true);
				pdfCheckBox.setSelected(false);
				cl = (CardLayout)(masterCardPanel.getLayout());
				cl.show(masterCardPanel, "file");
				break;
				
			case 7:
				excelCheckBox.setSelected(false);
				fileCheckBox.setSelected(false);
				pdfCheckBox.setSelected(true);
				cl = (CardLayout)(masterCardPanel.getLayout());
				cl.show(masterCardPanel, "pdf");
				break;         
	         case 10:  
	            clDescription = (CardLayout)(descriptionPanel.getLayout());
				clDescription.show(descriptionPanel, EMPTY);
		        break;
		            
	         case 11:  
	            clDescription = (CardLayout)(descriptionPanel.getLayout());
				clDescription.show(descriptionPanel, "field");
		        break;
	         case 12:
	        	 	excelCheckBox.setSelected(false);
					fileCheckBox.setSelected(false);
					pdfCheckBox.setSelected(false);
					cl = (CardLayout)(masterCardPanel.getLayout());
					cl.show(masterCardPanel, EMPTY);
					break;					
	         case 13:
	        	 pdfField.setText(ZERO);
	        	 break;
				
			}
		}
		else 
		{
			final Runnable callDoThreadSafeWork = new Runnable() 
			{
				public void run()
				{
					doThreadSafeWork(caseNumber);
				}
			};
			SwingUtilities.invokeLater(callDoThreadSafeWork);
		}
	}

	// Set and Get methods for relevant objects
	public void setDateField(final String inputString) {
		this.createdDateField.setText(inputString);
	}

	// Method for "closing the dialog"
	public void closeDialog() 
	{
		doThreadSafeWork(2);
	}
	
	public void descriptionHandling()
	{
		//description event handling
		if(!(((String) descriptionBox.getSelectedItem()).equalsIgnoreCase("Andet"))){ 
			//need to cast, since objects in JComboBox are generic
			description = (String) descriptionBox.getSelectedItem();
		}
		else if(((String) descriptionBox.getSelectedItem()).equalsIgnoreCase("Andet")){
			description = complimentaryDescriptionField.getText();
		}
		else
		{
			description = "";
		}
	}

	public void openFileChooser() 
	{
		try 
		{
			//makes an object
			fileChooser = new JFileChooser();
			
			//de filer man bliver vist når man åbner. Filtrerer underlige filer fra
			final FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF og Doc filer", "pdf", "doc", "docx");
			fileChooser.setFileFilter(filter);
			
			//null er parent frame
			final int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				attachedFile = fileChooser.getSelectedFile();
				attachedFileField.setText(attachedFile.toString());
				attachedFileField.setCaretPosition(1);
			}
		} 
		catch (Exception i) 
		{
			System.out.println("Something went wrong in SuperLogGUI.attachFileBTN filechooser");
			i.printStackTrace();
		}
	}
	
	// Item listener for the newExcelFileBox checkbox
	public void itemStateChanged(final ItemEvent e) 
	{
		if (e.getSource() == newExcelFileBox) 
		{

			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				System.out.println("newExcelFile = true!");
				newExcelFile = true;
			}
			if (e.getStateChange() == ItemEvent.DESELECTED) 
			{
				System.out.println("newExcelFile = false!");
				newExcelFile = false;
			}
		}
	}
	

	/**
	 * updates the customersForExcelBox. Starts by removing all in case the user performs cancel and redo the
	 * adding of customers
	 * @param customerList
	 */
	public void setListForComboBoxAndUpdate(final ArrayList<Customer> customerList)
	{
		customersForExcelBox.removeAllItems();
		for(final Customer c : customerList)
		{
			customersForExcelBox.addItem(c.getFirstName() + " " + c.getLastName());
		}	
	}

	public void actionPerformed(final ActionEvent event)
	{		
		 if(event.getSource() == descriptionBox)
		 {
			if(descriptionBox.getSelectedItem().toString().equalsIgnoreCase("andet"))
			{
				doThreadSafeWork(11);
			}
			else
			{
				doThreadSafeWork(10);
			}
		}
		
		else if(event.getSource() == deleteFileButton){
			attachedFileField.setText(ZERO);
			attachedFile = null;
		}
		else if(event.getSource() == pdfCheckBox)
		{
			if(pdfCheckBox.isSelected())
			{
				doThreadSafeWork(7);
			}
			else 
				doThreadSafeWork(12);
		}
		else if(event.getSource() == excelCheckBox)
		{
			if(excelCheckBox.isSelected())
			{
				doThreadSafeWork(5);
			}
			else 
				doThreadSafeWork(12);
			
		}

		else if(event.getSource() == fileCheckBox)
		{
			if(fileCheckBox.isSelected())
			{
				doThreadSafeWork(6);
			}
			else 
				doThreadSafeWork(12);
		}
		else if(event.getSource() == newExcelFileBox)
		{
			if(newExcelFileBox.isSelected())
			{

				doThreadSafeWork(3);
			}
			else 
				doThreadSafeWork(4);
			
		}
		else if(event.getSource() == attachFileBTN){
			openFileChooser();
		}
		else if(event.getSource() == cancelBTN)
		{
			cancel();
		}
	}
	
	protected void cancel()
	{
		state.getSelectedCustomersForExcel().clear();
		try
		{
			closeDialog();
		}
		catch(Exception i){
			i.printStackTrace();
			System.out.println("Something unexpected happened in SuperLogGUI.closeDialog");
		}
	}

	//for generating JOptionPanes
	protected void makeJOptionPane(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
}
