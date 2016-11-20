package gui;

//import entity.User;
import static constants.StringConstant.*;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.Button;
import components.CustomTitledBorder;
import components.Label;
import components.TextArea;
import components.TextField;
import entity.Customer;
import mediator.FileCase;
import mediator.Go;
import mediator.UpdateLogTable;

/**
 * NewLogGUI.java responsible for making new logs
 * 
 * @author Matti, Peter
 */

public final class NewLogGUI extends SuperLogGUI {

	private static final long serialVersionUID = 5477169956799000692L;

	public NewLogGUI() 
	{
		// title passed to super
		super("Opret ny log");
		/**local variable since we don't want variables from the State class as global variables,
		 * since the only place that they should be changed is in the State class.
		 */
		Customer selectedCustomer = state.getSelectedCustomer();
		
	    backgroundPanel = new JPanel(new GridBagLayout());
		
		
		
		 //the buttons used throughout.
	    confirmBTN = new Button("Gem");
    	cancelBTN = new Button("Annuller");
    	attachFileBTN = new Button("Vedhæft");
   		attachFileBTN.setPreferredSize(new Dimension(40,25));
   		
   		deleteFileButton = new Button("Slet");
    	deleteFileButton.setPreferredSize(new Dimension(40,25));

		//customerPanel
	    customerPanel = new JPanel(new GridBagLayout());
	    customerPanelBorder = new CustomTitledBorder("Kunde");
	    customerPanel.setBorder(customerPanelBorder);
	    
	    //Customer Name
	    customerNameLabel = new Label("Navn:");
	    customerNameField = new TextField(selectedCustomer.getFirstName() +SPACE+ selectedCustomer.getLastName(), false);

	    //Customer Number
	    customerIdLabel = new Label("Kunde nr:");
	    customerIdField = new TextField(ZERO+selectedCustomer.getId(), false);
	    buttonPanel = new JPanel(new GridBagLayout());
	    buttonBorder = new CustomTitledBorder(ZERO);
	    buttonPanel.setBorder(buttonBorder);
	    
	    
	    cprLabel = new Label("Cpr:");
	    cprField = new TextField(ZERO+selectedCustomer.getCpr(),false);
	   

	    //setting the components
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weighty = 1;
		c.weightx = 1;
	    
		// int top, int left, int bottom, int right)
	    
		c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(30,-80,-35,60);
	    customerPanel.add(cprLabel, c);
	 
	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(0,-90,0,80);
	    customerPanel.add(customerIdLabel, c);
	    
	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(-30,-80,35,60);
	    customerPanel.add(customerNameLabel, c);
	  
	    c.ipadx = 140;
	    c.ipady = 5;
	    c.insets = new Insets(-30,120,35,-90);
	    customerPanel.add(customerNameField, c);

	    c.insets = new Insets(0,120,0,-90);
	    customerPanel.add(customerIdField, c);
	    
	    c.insets = new Insets(30,120,-35,-90);
	    customerPanel.add(cprField, c);
	    
	    //uneditablePanel	    
	    uneditablePanel = new JPanel(new GridBagLayout());
	    uneditablePanelBorder = new CustomTitledBorder("Information");
	    uneditablePanel.setBorder(uneditablePanelBorder);

	    createdDateLabel = new Label("Dato oprettet:");
	    createdByLabel = new Label("Oprettet af:");
	    editedByLabel = new Label("Redigeret af:");
	    lastEditDateLabel = new Label("Sidst redigeret:");

	    createdDateField = new TextField(ZERO,false);
	    createdByField = new TextField(ZERO,false);
	    editedByField = new TextField(ZERO,false);
	    lastEditDateField = new TextField(ZERO, false);
	    complimentaryDescriptionField = new TextField(ZERO,true); 
	    
	    fieldList.add(createdDateField);
	    fieldList.add(createdByField);
	    fieldList.add(editedByField);
	    fieldList.add(lastEditDateField);
	
	    for(final TextField field : fieldList){
	    	field.setPreferredSize(new Dimension(130,20));
	    }
	    
	    fieldList.add(complimentaryDescriptionField);
	    fieldList.add(customerNameField);
	    fieldList.add(customerIdField);
	    
	    complimentaryDescriptionField.setPreferredSize(new Dimension(65,20));
	    customerNameField.setPreferredSize(new Dimension(55,20));
	    customerIdField.setPreferredSize(new Dimension(55,20));
	    cprField.setPreferredSize(new Dimension(55,20));
	    
	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(-50,-80,50,120);
	    uneditablePanel.add(createdDateLabel, c);
	    
	    c.ipady = 8;
	    c.insets = new Insets(-50,110,50,-100);
	    uneditablePanel.add(createdDateField, c);
	     
	    c.ipady = 15;
	    c.insets = new Insets(-20,-80,20,102);
	    uneditablePanel.add(createdByLabel, c);
	
	    c.ipady = 8;
	    c.insets = new Insets(-20,110,20,-100);
	    uneditablePanel.add(createdByField, c);
	    
	    // int top, int left, int bottom, int right)
	    c.ipady = 15;

	    c.insets = new Insets(40,-80,-40,125);
	    uneditablePanel.add( lastEditDateLabel, c);
	    
	    c.ipady = 8;
	    c.insets = new Insets(40,110,-40,-100);
	    uneditablePanel.add( lastEditDateField, c);
	    
	    c.ipady = 15;
	    c.insets = new Insets(10,-80,-10,105);
	    uneditablePanel.add(editedByLabel, c);
	    
	    c.ipady = 8;
	    c.insets = new Insets(10,110,-10,-100);
	    uneditablePanel.add(editedByField, c);
	    
	    //editablePanel
	    editablePanel = new JPanel(new GridBagLayout());
	    editablePanelBorder = new CustomTitledBorder("Valgmuligheder");
		editablePanel.setBorder(editablePanelBorder);
		
		optionsLabel = new Label("Fil Muligheder:");
		excelCheckLabel = new Label("Excel:");
		fileCheckLabel = new Label("Fil:");
		pdfCheckLabel = new Label("Netbank:");
		
		pdfCheckBox = new JCheckBox(); //is being put automatically to selected later.
		excelCheckBox = new JCheckBox();
		fileCheckBox = new JCheckBox();
		
		//description CardLayout stuff
		descriptionPanel = new JPanel(new CardLayout());
		bogusLabel = new Label("");
		descriptionPanel.add(bogusLabel, EMPTY);
		descriptionPanel.add(complimentaryDescriptionField, "field");
		
		//file related CardLayouts:
		masterCardPanel = new JPanel(new CardLayout());
		superPdfPanel = new JPanel(new CardLayout());
		
		
		
		excelPanel = new JPanel(new GridBagLayout());
		filePanel = new JPanel(new GridBagLayout());
		editPdfPanel = new JPanel(new GridBagLayout());
		newPdfPanel = new JPanel();
		emptyPanel = new JPanel();
		
		excelPanel.setPreferredSize(new Dimension(220,25));
		filePanel.setPreferredSize(new Dimension(220,25));
		emptyPanel.setPreferredSize(new Dimension(220,25));
		superPdfPanel.setPreferredSize(new Dimension(220,25));
		
		superPdfPanel.add(newPdfPanel, "new");
		superPdfPanel.add(editPdfPanel, "edit");
		
		masterCardPanel.add("excel", excelPanel);
		masterCardPanel.add("file", filePanel);
		masterCardPanel.add(EMPTY, emptyPanel);
		masterCardPanel.add("pdf", superPdfPanel);
		
		doThreadSafeWork(12);
	
		excelLabel = new Label("Tilføj kunder til Excel:");
		fileLabel = new Label("Fil:");
		descriptionLabel = new Label("Beskrivelse:");
		
		//pdfpanelcomponents
		overwritePdfCheckBox = new JCheckBox();
		overwritePdfLabel = new Label("Overskriv:");
		pdfLabel = new Label("Pdf:");
		attachedPdfLabel = new Label("Vedhæftet pdf:");
		
		deletePdfButton = new Button("Slet");
		pdfField = new TextField(ZERO, false);
		pdfField.setPreferredSize(new Dimension(155,20));
		

 	    c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(-20,-100,5,60);
	    editPdfPanel.add(pdfLabel,c);
	    
	    c.ipady = 1;
 	    c.ipadx = 20;
 	    c.insets = new Insets(20,-130,-20,105);
	    editPdfPanel.add(attachedPdfLabel,c);
	    
		c.ipadx = 65;
		c.ipady = 15;
	    c.insets = new Insets(-10,150,15,-150);
	    editPdfPanel.add(overwritePdfLabel,c);
		
		c.ipadx = 65;
		c.ipady = 15;
	    c.insets = new Insets(-10,180,15,-180);
	    editPdfPanel.add(overwritePdfLabel,c);
	    
	    c.ipady = 1;
 	    c.ipadx = 70;
 	    c.insets = new Insets(-20,10,0,-70);
	    editPdfPanel.add(deletePdfButton,c);
	    
	    
	    c.ipady = 1;
	    c.ipadx = 60;
	    c.insets = new Insets(10,100,-30,-90);
	    editPdfPanel.add(pdfField,c);

	    c.ipadx = 35;
	    c.ipady = 25;
	    c.insets = new Insets(-10,210,20,-210);
	    editPdfPanel.add(overwritePdfCheckBox,c);
		
		//adding pre-defined descriptions - or making your own choosing "Andet"
		descriptionBox = new JComboBox<String>(DESCRIPTION);
		descriptionBox.addActionListener(this);
		descriptionBox.setFont(Ressources.font);
		
		//combo box to show which Customers have been added for the excel sheet
		customersForExcelBox = new JComboBox<String>();
		customersForExcelBox.setEditable(false);
		customersForExcelBox.setEnabled(true);
		customersForExcelBox.setFont(Ressources.font);
		customersForExcelBox.setPreferredSize(new Dimension(200,20));
		
		final ActionListener comboBoxListener = new ActionListener()
		{
			public void actionPerformed(final ActionEvent event) 
			{
					doThreadSafeWork(1);
			}
		 };
		 
		customersForExcelBox.addActionListener(comboBoxListener);
		
		addMoreToExcelButton = new Button("Tilføj kunder");
		addMoreToExcel1Button = new Button("Tilføj kunder");

		overwriteFileLabel = new Label("Overskriv");		
		customersForExcelLabel = new Label("Kunder tilføjet excel:");
		
		//Excel file CardLayout
		moreCustomersOverWriteCardPanel = new JPanel(new CardLayout());
		newExcelPanel = new JPanel(new GridBagLayout());
		overWriteFilePanel = new JPanel(new GridBagLayout());
				
		moreCustomersOverWriteCardPanel.setPreferredSize(new Dimension(100,30));
		
		moreCustomersOverWriteCardPanel.add("overwrite", overWriteFilePanel);				
		moreCustomersOverWriteCardPanel.add("newExcel", newExcelPanel);

		commentAreaLabel = new Label("Indtast Kommentar:");
    	commentArea = new TextArea(ZERO);
    	
    	scrollPane = new JScrollPane(commentArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane.setPreferredSize(new Dimension(150,25));
	    newExcelFileBox = new JCheckBox();
	    
	    boxList.add(newExcelFileBox);
	    boxList.add(pdfCheckBox);
	    boxList.add(excelCheckBox);
	    boxList.add(fileCheckBox);
	        
	    for(final JCheckBox box : boxList)
	    {
	    	box.addActionListener(this);
	    }
    	newExcelFileBox.addItemListener(this);

 
    	attachFileLabel = new Label("Tilføjet fil:");
    	attachedFileField = new TextField(ZERO,false);
    	attachedFileField.setPreferredSize(new Dimension(155,20));

    	
    	 // int top, int left, int bottom, int right)
    	c.ipady = 15;
  	    c.ipadx = 65;
  	    c.insets = new Insets(-90,-70,90,100);
  	    editablePanel.add(descriptionLabel, c);
  	    
  	    c.ipady = 2;
	    c.ipadx = 1;
	    c.insets = new Insets(-90,100,90,-100);
	    editablePanel.add(descriptionBox, c);
	    
	    c.ipady = 8;
	    c.ipadx = 150;
	    c.insets = new Insets(-60,100,60,-100);
	    editablePanel.add(descriptionPanel, c);
    	
    	c.ipady = 15;
  	    c.ipadx = 65;
  	    c.insets = new Insets(-30,-110,40,110);
  	    editablePanel.add(commentAreaLabel, c);
  	    
  	    c.ipady = 30;
  	    c.ipadx = 90;
  	    c.insets = new Insets(-20,110,10,-100);
  	    editablePanel.add(scrollPane, c);
  	    
  	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(30,-95,-30,100);
	    editablePanel.add(optionsLabel, c);
	    
	    c.ipady = 5;
	    c.ipadx = 5;
	    c.insets = new Insets(30,50,-30,-42);
	    editablePanel.add(pdfCheckBox, c);
	    
	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(30,38,-30,-38);
	    editablePanel.add(pdfCheckLabel, c);
  	    
  	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(30,135,-30,-90);
	    editablePanel.add(excelCheckLabel, c);
	    
	    c.ipady = 5;
  	    c.ipadx = 5;
  	    c.insets = new Insets(30,145,-30,-90);
  	    editablePanel.add(excelCheckBox, c);
  	    
  	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(30,190,-30,-160);
	    editablePanel.add(fileCheckLabel, c);
	    
	    c.ipady = 5;
  	    c.ipadx = 5;
  	    c.insets = new Insets(30,180,-30,-180);
  	    editablePanel.add(fileCheckBox, c);
  	    
  	    //CardLayouts - masters
  	    c.ipady = 40;
	    c.ipadx = 230;
	    c.insets = new Insets(60,0,-100,0);
	    editablePanel.add(masterCardPanel, c);
	    
	    
 	   //CardLayouts - the 2 beneath that 
 	    //JPanel to handle excel 

	    c.ipady = 3;
        c.ipadx = 90;
        c.insets = new Insets(0,-30,0,-40);
        newExcelPanel.add(addMoreToExcelButton, c);

        c.ipady = 1;
        c.ipadx = 20;
        c.insets = new Insets(0,-50,0,40);
        overWriteFilePanel.add(addMoreToExcel1Button, c);
       

	    c.ipady = 5;
	    c.ipadx = 1;
	    c.insets = new Insets(0,85,0,-120);
	    overWriteFilePanel.add(newExcelFileBox, c);
	    
	    c.ipady = 5;
	    c.ipadx = 10;
	    c.insets = new Insets(0,40,0,-70);
	    overWriteFilePanel.add(overwriteFileLabel, c);
	    
	    //overWriteCardPanel
	    c.ipady = 3;
	    c.ipadx = 120;
	    c.insets = new Insets(-10,100,10,-95);
	    excelPanel.add(moreCustomersOverWriteCardPanel, c);
	   
		c.ipady = 2;
	    c.ipadx = 20;
	    c.insets = new Insets(20,110,-20,-90);
	    excelPanel.add(customersForExcelBox, c);
	    
	    c.ipady = 1;
	    c.ipadx = 3;
	    c.insets = new Insets(-20,-150,5,145);
	    excelPanel.add(excelLabel, c);
	    
	    c.ipady = 1;
 	    c.ipadx = 20;
 	    c.insets = new Insets(20,-130,-20,140);
 	    excelPanel.add(customersForExcelLabel,c);
 	    
 	    
	    
 	    c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(-20,-100,5,60);
 	    filePanel.add(fileLabel,c);
	    
	    c.ipady = 1;
 	    c.ipadx = 70;
 	    c.insets = new Insets(-20,10,0,-70);
 	    filePanel.add(attachFileBTN,c);
 	    
 	    c.ipady = 1;
	    c.ipadx = 60;
	    c.insets = new Insets(10,100,-30,-90);
	    filePanel.add(attachedFileField,c);
 	    
 	    c.ipady = 1;
	    c.ipadx = 70;
	    c.insets = new Insets(-20,160,0,-150);
	    filePanel.add(deleteFileButton,c);
	    
	    c.ipady = 1;
 	    c.ipadx = 20;
 	    c.insets = new Insets(20,-100,-20,100);
 	    filePanel.add(attachFileLabel,c);
  	    
   		c.ipady = 55;
   		c.ipadx = 300;
   		c.insets = new Insets(-15,0,460,0);
   		backgroundPanel.add(customerPanel, c);
	
   		c.ipady = 100;
   		c.ipadx = 255;
   		c.insets = new Insets(-10,0,190,0);
   		backgroundPanel.add(uneditablePanel, c);
   		
   		c.ipady = 190;
		c.ipadx = 1;
		c.insets = new Insets(225,0,-5,0);
		backgroundPanel.add(editablePanel, c);
   		
		
		setComponent(new Insets(270,0,-265,0),backgroundPanel,buttonPanel,110,2);
		setComponent(new Insets(0,100,0,-50),buttonPanel,cancelBTN,40,8);
		setComponent(new Insets(0,-100,0,50),buttonPanel,confirmBTN,40,8);
	
   		buttonList.add(confirmBTN);
   		buttonList.add(cancelBTN);
   		buttonList.add(addMoreToExcelButton);
   		buttonList.add(deleteFileButton);
   		buttonList.add(attachFileBTN);
   		buttonList.add(deletePdfButton);
   		
   		for(final Button b : buttonList)
   		{
   			b.addActionListener(this);
   		}
   		
   		
   		//setting configs.
	   	this.getContentPane().add(backgroundPanel);
		// for mediator reference
		mediator.setNewLogGui(this);
		state.getSelectedCustomersForExcel().clear();
		state.getSelectedCustomersForExcel().add(state.getSelectedCustomer());
		setListForComboBoxAndUpdate(state.getSelectedCustomersForExcel());

		// fields
		editedByField.setText(state.getCurrentUser().getName());
		createdByField.setText(state.getCurrentUser().getName());
		createdDateField.setText(utilControl.dateToString(new Date()));
		lastEditDateField.setText(utilControl.dateToString(new Date()));

		/**
		 * if the field goes over its bounds, setCaretPosition(1) puts the view
		 * back to character 1.
		 */
		// for(JTextField field : fieldList)
		// {
		// field.setCaretPosition(1);
		// }

		addMoreToExcelButton.addActionListener(this);
		
		
		ActionListener newLogGUIListener = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if (event.getSource() == confirmBTN) 
				{
					if(commentArea.getText().length() > 125)
					{
						JOptionPane.showMessageDialog(null, "Comment feltet er begrænset til"
								+ " 125 tegn. ");
						return;
					}
					if (excelCheckBox.isSelected()) 
					{
						state.setFileCase(FileCase.NEWEXCEL);
						mediator.handle(Go.NEWLOGGUI_PROGRESSBARDIALOG);
					}
					else if (fileCheckBox.isSelected()) 
					{
						if (attachedFile != null) 
						{
							state.setFileCase(FileCase.NEWFILE);
							callNewLogEntry();
							dispose();
						}
						else
						{
							state.setFileCase(FileCase.NONE);
							callNewLogEntry();
							dispose();
						}
					} 
					else if(pdfCheckBox.isSelected())
					{
						state.setFileCase(FileCase.NEWPDF);
						mediator.handle(Go.NEWLOGGUI_PROGRESSBARDIALOG);
					}
					else 
					{
						state.setFileCase(FileCase.NONE);
						callNewLogEntry();
						dispose();
					}
				}
			}
		};
		multipleCustomersForExcelListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
					mediator.handle(Go.NEWLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG);
			}
		};
		
		confirmBTN.addActionListener(newLogGUIListener);
		addMoreToExcelButton.addActionListener(multipleCustomersForExcelListener);
		
		
		cl1 = (CardLayout) (moreCustomersOverWriteCardPanel.getLayout());
		cl1.show(moreCustomersOverWriteCardPanel, "newExcel");
		cl1 = (CardLayout) (superPdfPanel.getLayout());
		cl1.show(superPdfPanel, "new");

		setVisible(true);
	}
	
	private void callNewLogEntry()
	{
		try
		{
			newLogEntry();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * makes a new LogEntry and updates the logModel
	 * 
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void newLogEntry() throws InterruptedException, Exception 
	{
		descriptionHandling();
		int logEntryId = utilControl.newLogEntry(description,commentArea.getText(), attachedFile,state.getSelectedCustomersForExcel());
		//if logEntryId is 0, then it has not been generated by the db.
		if (logEntryId == 0)
		{
			makeJOptionPane("Der er ikke oprettet en ny logentry");
		}
		state.setSelectedLogEntry(utilControl.getSpecificElement(state.getSelectedCustomer().getLogEntryList(), logEntryId));
		mediator.getLogEntryTable().dynamicallyUpdateLogModel(UpdateLogTable.NEW);
	}
}
