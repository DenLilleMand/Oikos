/*
 * EditLogGUI.java
 * EditLogGUI - extends SuperLogGUI and is responsible for the editing of logs
 * @Author Peter & Matti
 */
package gui;
import static constants.StringConstant.*;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import mediator.FileCase;
import mediator.Go;
import mediator.UpdateLogTable;
import entity.Customer;
//import java.io.File;
import entity.LogEntry;


public final class EditLogGUI extends SuperLogGUI
{
	private static final long serialVersionUID = -8538924447112826260L;
	/** */
	private LogEntry logToEdit;
	/** Label describing attachedFileField */
	private Label attachedFileLabel;
	/** a Field containing whatever file is attached to the logEntry, 
	 * it will only show either the name of a uploaded file, or the synonyms 
	 * 'netbank' or 'Master excel ark', and if no file is attached it will show nothing.*/
	private TextField attachedGeneralFileField;
	/**Button to delete a given attached file */
	private Button deleteButton;
	
	/** Button to open any attached file*/
	private Button openFileButton;
	
	/** Label for the master excel sheet (overwrite) */
	private Label overwriteExcelLabel;
	/** field for the master excel sheet (overwrite)*/
	private TextField overwriteExcelField;
	
	private JPanel attachedFilePanel;
	
	private ActionListener deleteListener;
	private ActionListener openFileListener;
	private ActionListener editLogGUIListener;
	
	private Dimension buttonDimension = new Dimension(45,25);
	
	
	

	public EditLogGUI()
	{	
		//sends the title in superLogGUI 
		super("Rediger log");
		
		mediator.setEditLogGui(this);
		
		/**local variable since we don't want variables from the State class as global variables,
		 * since the only place that they should be changed is in the State class.
		 */
		Customer selectedCustomer = state.getSelectedCustomer();
		//the log to be edited is the selected log as set in state class
		logToEdit = state.getSelectedLogEntry();
		if (logToEdit == null)   throw new NullPointerException("Somehow logentry was null");
		
	    backgroundPanel = new JPanel(new GridBagLayout());
	    attachedFilePanel = new JPanel(new GridBagLayout());
		
		
		/**
		 * Consider if adding the button dimensions in a loop would be better(minor tweak rly).
		 */
		 //the buttons used throughout.
	    confirmBTN = new Button("Gem");
	    cancelBTN = new Button("Annuller");
	    attachFileBTN = new Button("Vedhæft fil");
  		attachFileBTN.setPreferredSize(buttonDimension);
  	
  		openFileButton = new Button("Åben fil");
  		openFileButton.setPreferredSize(buttonDimension);
  		
  		deleteButton = new Button("Slet fil");
  		deleteButton.setPreferredSize(buttonDimension);
  		
  		
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

	    /** The field that contains the main file attached to this logentry */
	    attachedGeneralFileField = new TextField(ZERO,false);
	    buttonPanel = new JPanel(new GridBagLayout());
	    buttonBorder = new CustomTitledBorder(ZERO);
	    buttonPanel.setBorder(buttonBorder);
	    
	    /** Label  */
	    attachedFileLabel = new Label("Fil vedhæftet:");
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
	    fieldList.add(attachedGeneralFileField);
	
	    for(final TextField field : fieldList)
	    {
	    	field.setPreferredSize(new Dimension(130,20));
	    }
	    
	    fieldList.add(complimentaryDescriptionField);
	    fieldList.add(customerNameField);
	    fieldList.add(customerIdField);
	    
	    complimentaryDescriptionField.setPreferredSize(new Dimension(130,20));
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
		bogusLabel = new Label(ZERO);
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
		overwriteExcelLabel = new Label("Ny fil vedhæftet:");
		overwriteExcelField = new TextField(MASTEREXCEL,false);
		fileLabel = new Label("Fil:");
		descriptionLabel = new Label("Beskrivelse:");
		
		//pdfpanelcomponents
		attachedPdfLabel = new Label("Ny fil vedhæftet:");
		pdfField = new TextField(NETBANK, false);
		pdfField.setPreferredSize(new Dimension(155,20));
		
	    c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(-20,-130,10,125);
	    editPdfPanel.add(attachedPdfLabel,c);
	    
	    c.ipady = 5;
	    c.ipadx = 60;
	    c.insets = new Insets(-20,100,10,-90);
	    editPdfPanel.add(pdfField,c);
		
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
	    
	    boxList.add(pdfCheckBox);
	    boxList.add(excelCheckBox);
	    boxList.add(fileCheckBox);
	        
	    for(final JCheckBox box : boxList)
	    {
	    	box.addActionListener(this);
	    }


	    attachFileLabel = new Label("Ny fil vedhæftet:");
	    attachedFileField = new TextField(ZERO,false);
	    attachedFileField.setPreferredSize(new Dimension(155,20));

   	
   	 // int top, int left, int bottom, int right)
	    c.ipady = 15;
 	    c.ipadx = 65;
 	    c.insets = new Insets(-130,-70,130,100);
 	    editablePanel.add(descriptionLabel, c);
 	    
 	    c.ipady = 2;
	    c.ipadx = 1;
	    c.insets = new Insets(-130,95,130,-100);
	    editablePanel.add(descriptionBox, c);
	    
	    c.ipady = 8;
	    c.ipadx = 205;
	    c.insets = new Insets(-105,80,95,-100);
	    editablePanel.add(descriptionPanel, c);
   	
	    c.ipady = 15;
 	    c.ipadx = 65;
 	    c.insets = new Insets(-70,-110,70,110);
 	    editablePanel.add(commentAreaLabel, c);
 	    
 	    c.ipady = 50;
 	    c.ipadx = 220;
 	    c.insets = new Insets(-50,110,50,-100);
 	    editablePanel.add(scrollPane, c);
 	    
 	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(60,-95,-75,100);
	    editablePanel.add(optionsLabel, c);
	    
	    c.ipady = 5;
	    c.ipadx = 5;
	    c.insets = new Insets(60,50,-75,-42);
	    editablePanel.add(pdfCheckBox, c);
	    
	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(60,38,-75,-38);
	    editablePanel.add(pdfCheckLabel, c);
 	    
 	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(60,135,-75,-90);
	    editablePanel.add(excelCheckLabel, c);
	    
	    c.ipady = 5;
 	    c.ipadx = 5;
 	    c.insets = new Insets(60,145,-75,-90);
 	    editablePanel.add(excelCheckBox, c);
 	    
 	    c.ipady = 15;
	    c.ipadx = 65;
	    c.insets = new Insets(60,190,-75,-160);
	    editablePanel.add(fileCheckLabel, c);
	    
	    c.ipady = 5;
 	    c.ipadx = 5;
 	    c.insets = new Insets(60,180,-75,-180);
 	    editablePanel.add(fileCheckBox, c);
 	    
 	    //CardLayouts - masters
 	    c.ipady = 75;
	    c.ipadx = 230;
	    c.insets = new Insets(130,0,-120,0);
	    editablePanel.add(masterCardPanel, c);
	    
	    
	   //CardLayouts - the 2 beneath that 
	    //JPanel to handle excel 
	   c.ipady = 3;
       c.ipadx = 90;
       c.insets = new Insets(0,-30,0,-40);
       newExcelPanel.add(addMoreToExcelButton, c);

       c.ipady = 3;
       c.ipadx = 300;
       c.insets = new Insets(0,3,0,0);
       overWriteFilePanel.add(addMoreToExcel1Button, c);

	    //overWriteCardPanel
	    c.ipady = 1;
	    c.ipadx = 120;
	    c.insets = new Insets(15,100,-15,-95);
	    excelPanel.add(moreCustomersOverWriteCardPanel, c);
	   
		c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(40,110,-40,-90);
	    excelPanel.add(customersForExcelBox, c);
	    
	    c.ipady = 1;
	    c.ipadx = 3;
	    c.insets = new Insets(15,-150,-15,160);
	    excelPanel.add(excelLabel, c);
	    
	    c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(40,-130,-40,155);
	    excelPanel.add(customersForExcelLabel,c);
	    
	    setComponent(new Insets(-20,-130,10,105), excelPanel, overwriteExcelLabel, 40, 5);
	    setComponent(new Insets(-20, 100,10,-90), excelPanel, overwriteExcelField,60,5);
	  
	    c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(20,-85,-20,80);
	    filePanel.add(fileLabel,c);
	    
	    c.ipady = 1;
	    c.ipadx = 150;
	    c.insets = new Insets(10,100,-30,-90);
	    filePanel.add(attachFileBTN,c);
	    
	    c.ipady = 5;
	    c.ipadx = 60;
	    c.insets = new Insets(-20,90,10,-90);
	    filePanel.add(attachedFileField,c);
	    
	    
	    
	    c.ipady = 1;
	    c.ipadx = 20;
	    c.insets = new Insets(-20,-130,10,120);
	    filePanel.add(attachFileLabel,c);
 	    
  		c.ipady = 55;
  		c.ipadx = 300;
  		c.insets = new Insets(-75,0,525,0);
  		backgroundPanel.add(customerPanel, c);
	
  		c.ipady = 100;
  		c.ipadx = 255;
  		c.insets = new Insets(-130,0,190,0);
  		backgroundPanel.add(uneditablePanel, c);
  		
  		c.ipady = 260;
		c.ipadx = -100;
		c.insets = new Insets(110,0,-120,0);
		backgroundPanel.add(editablePanel, c);
  		
		
		setComponent(new Insets(350,0,-325,0),backgroundPanel,buttonPanel,110,2);
		setComponent(new Insets(0,100,0,-50),buttonPanel,cancelBTN,40,8);
		setComponent(new Insets(0,-100,0,50),buttonPanel,confirmBTN,40,8);
		
		/** Attached File Panel   */
		setComponent(new Insets(20,0,-25,0),editablePanel,attachedFilePanel,330,30);
		
		setComponent(new Insets(-10,-110,15,130),attachedFilePanel,attachedFileLabel,3,1);
		setComponent(new Insets(-10,100,15,-110),attachedFilePanel,attachedGeneralFileField,110,3);
		
		setComponent(new Insets(20,165,-10,-170),attachedFilePanel,deleteButton,70,1);
		setComponent(new Insets(20,33,-10,-50),attachedFilePanel,openFileButton,70,1);
		
		/** ----------------   */
  		buttonList.add(confirmBTN);
  		buttonList.add(cancelBTN);
  		buttonList.add(addMoreToExcelButton);
  		buttonList.add(attachFileBTN);
  		
  		for(final Button b : buttonList)
  		{
  			b.addActionListener(this);
  		}
  		
  		
  		//setting configs.
	   	this.getContentPane().add(backgroundPanel);
	  // 	this.pack();
		
		//reference for the mediator
		state.getSelectedCustomersForExcel().clear();
		state.getSelectedCustomersForExcel().add(state.getSelectedCustomer());
		setListForComboBoxAndUpdate(state.getSelectedCustomersForExcel());
		
		
		
		//edited and created by
		editedByField.setText(logToEdit.getLastEditBy());
		createdByField.setText(logToEdit.getCreatedBy());
		
	
		//Dates
		createdDateField.setText(utilControl.dateToString(logToEdit.getCreateDate()));
		lastEditDateField.setText(utilControl.dateToString(logToEdit.getLastEditDate()));
		
	
		//Description box and complementaryDescriptionField
		for(int i = 0 ; i < DESCRIPTION.length ; i++)
		{
			if(logToEdit.getDescription().equals(DESCRIPTION[i]))
			{
				descriptionBox.setSelectedItem(DESCRIPTION[i]);
				doThreadSafeWork(10);
				break;
			}
			else
			{
				descriptionBox.setSelectedItem("Andet");
				doThreadSafeWork(11);
				complimentaryDescriptionField.setText(logToEdit.getDescription());
			}
		}
			//Comment
			commentArea.setText(logToEdit.getComment());
			//File
			attachedGeneralFileField.setText(logToEdit.getAttachedFile().getName());
			/** tilføj kode her der kun tager navnet af file. */
			doThreadSafeWork(12);

//		ActionListener editLogGUIListener = new ActionListener()
//		{
//			public void actionPerformed(ActionEvent event)
//			{
//		//File
//		if(logToEdit.getAttachedFile().toString().equalsIgnoreCase(ZERO)){
//			doThreadSafeWork(12);
//		}
//		else if(logToEdit.getAttachedFile().toString().contains("excel")){
//			doThreadSafeWork(5);
//		}
//		else if(logToEdit.getAttachedFile().toString().contains("netbank"))
//		{
//			doThreadSafeWork(7);
//			pdfField.setText(logToEdit.getAttachedFile().toString());
//		}
//		else
//		{
//			doThreadSafeWork(6);
//			attachedFileField.setText(logToEdit.getAttachedFile().toString());
//		}
//	}
//		};
		
		editLogGUIListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(event.getSource() == confirmBTN) 
				{
					if(commentArea.getText().length() > 125 || complimentaryDescriptionField.getText().length() > 125)
					{
						JOptionPane.showMessageDialog(null, "Comment & Beskrivelses felterne er begrænset til 125 tegn.");
						return;
					}
					//File Event Handling:
					//EXCEL:
					if(excelCheckBox.isSelected())
					{
						if(logToEdit.getAttachedFile().toString().equalsIgnoreCase(ZERO))
						{
							state.setFileCase(FileCase.EDITNEWEXCEL);
							mediator.handle(Go.EDITLOGGUI_PROGRESSBARDIALOG);
									
						}	
						else
						{
							state.setFileCase(FileCase.EDITEXCEL);
							//If excelfile is added, the save logEntry happens in the ProgressBarDialog
								mediator.handle(Go.EDITLOGGUI_PROGRESSBARDIALOG);
							//Sends the call for editing the logEntry and creating the ExcelFile
						}
					 }
					//FILE:
					else if(fileCheckBox.isSelected())
					{
						if(attachedFile == null)
						{
							noFileCase();
							/**-kaster dispose */
						}
						else if(!logToEdit.getAttachedFile().toString().equalsIgnoreCase(ZERO))
						{
								state.setFileCase(FileCase.EDITFILE); 
								callEditLogEntry();
								dispose();
						}
						else
						{
							state.setFileCase(FileCase.NEWFILE);
							callEditLogEntry();
							dispose();
						}
					}
					//PDF:
					else if(pdfCheckBox.isSelected())
					{
						if(logToEdit.getAttachedFile().toString().equals(ZERO))
						{
							state.setFileCase(FileCase.EDITNEWPDF);
							mediator.handle(Go.EDITLOGGUI_PROGRESSBARDIALOG);
						}
						else
						{
							state.setFileCase(FileCase.EDITPDF);
							mediator.handle(Go.EDITLOGGUI_PROGRESSBARDIALOG);	
						}
					}
					else if(!logToEdit.getAttachedFile().toString().equals(ZERO) && attachedGeneralFileField.getText().equals(ZERO))
					{
						/**Med det her har vi evt. ikke brug for den FileCase der hedder DELETEPDF */
						state.setFileCase(FileCase.DELETEFILE);
						callEditLogEntry();
						dispose();
					}
					else
					{
						noFileCase();
					}
				}
			}
		};
		
		deleteListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if(event.getSource() == deleteButton)
				{
					attachedGeneralFileField.setText(ZERO);
				}
			}
		};
		deleteButton.addActionListener(deleteListener);
		
		openFileListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if(event.getSource() == openFileButton)
				{
					if(attachedGeneralFileField.getText().equals(ZERO))
					{
						return;
					}
					try 
					{
						
						utilControl.openFileInNativeApp(logToEdit.getAttachedFile());
					} catch (Exception e) 
					{
						e.printStackTrace();
						JOptionPane.showMessageDialog(EditLogGUI.this, "Der skete en fejl da vi prøvede at åbne din fil");
					}
				}
			}
		};
		openFileButton.addActionListener(openFileListener);
		
		
		
		multipleCustomersForExcelListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
					mediator.handle(Go.EDITLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG);
			}
		};
		
		cl1 = (CardLayout) (superPdfPanel.getLayout());
		cl1.show(superPdfPanel, "edit");
		//actionListeners added
		confirmBTN.addActionListener(editLogGUIListener);
		addMoreToExcelButton.addActionListener(multipleCustomersForExcelListener);
		addMoreToExcel1Button.addActionListener(multipleCustomersForExcelListener);
		
		cl1 = (CardLayout)(moreCustomersOverWriteCardPanel.getLayout());
		cl1.show(moreCustomersOverWriteCardPanel, "overwrite");
		
		setVisible(true);
		commentArea.requestFocusInWindow();
	}
	
	private void noFileCase()
	{
			state.setFileCase(FileCase.NONE);
			//if no file attached we create the LogEntry without a progress bar
			callEditLogEntry();
			dispose();
	}
		
	private void callEditLogEntry()
	{
		try
		{
			editLogEntry();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Noget gik galt da vi skulle redigere dit log entry");
			e.printStackTrace();
		}
	}
	
	public void editLogEntry() throws InterruptedException, Exception 
	{
		descriptionHandling();
		utilControl.editExistingLogEntry(logToEdit, attachedFile, commentArea.getText(), description, state.getSelectedCustomersForExcel());
		mediator.getLogEntryTable().dynamicallyUpdateLogModel(UpdateLogTable.EDIT);
		state.setSelectedLogEntry(utilControl.getSpecificElement(state.getSelectedCustomer().getLogEntryList(), logToEdit.getId()));
		
	}
	
}