
package gui;

//import java.awt.CardLayout;
import java.awt.Color;
//import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import components.Button;
import components.CustomTitledBorder;
import components.Label;
import components.TextField;
import mediator.Go;
import tables.CustomerTable;
import tables.LogEntryTable;
import tables.State;

/*
 * MainGUI.java
 *	extends SuperGUI,  is responsible for showing the main customer table and LogTable, 
 * @Author Matti, Jon, Peter
 */
public final class MainGUI extends SuperGUI implements ActionListener, DocumentListener
{
	private static final long serialVersionUID = 9096891996253634514L;
	//tables
	private CustomerTable customerTable;
	private LogEntryTable logTable;
	// JScrollPane
	private JScrollPane logPane, customerPane;
	// JLabels
	private Label searchLabel, logoLabel;
	// søgeFunktion
	private TextField searchField;
	
	private final String ZERO = "";

	// JButtons
	private Button newEntryButton, viewEntryButton, deleteEntryButton, openFileButton;
	
	private JComboBox<String[]> comboBox;

	private int mainCaseNumber;

	private JPanel customerPanel, logPanel;
	
	private CustomTitledBorder customerTitle, logTitle;

	// ArrayLists
	ArrayList<JTextField> fieldList = new ArrayList<JTextField>();
	ArrayList<JLabel> labelList = new ArrayList<JLabel>();


	public MainGUI()
	{
		mediator.setMainGui(this);//sets this mainGui in the mediator class for reference
		//modify the picture variable from SuperGui
		picture = "maingui";

		setTitle("Olympus - Main Menu");
		setFont(Ressources.font);
		//create tables
		customerTable = new CustomerTable();
		logTable = new LogEntryTable();

		// JScrollPane
		customerPane = new JScrollPane(customerTable);
		logPane = new JScrollPane(logTable);

		//comboBox
		//accepts only String arrays
		comboBox = new JComboBox<String[]>();
		comboBox.addActionListener(this);
		
		// border
		customerTitle = new CustomTitledBorder("Kunder");
		logTitle = new CustomTitledBorder("Log");

		// Buttons
		newEntryButton = new Button("Ny log entry");
		viewEntryButton = new Button("Se log entry");
		deleteEntryButton = new Button("Slet");
		openFileButton = new Button("Se fil");

		buttonList.add(newEntryButton);
		buttonList.add(viewEntryButton);
		buttonList.add(deleteEntryButton);
		buttonList.add(openFileButton);

		for (Button button : buttonList) 
		{
			button.addActionListener(this);
			button.setForeground(Color.GREEN);
		}
		
		// Labels
		searchLabel = new Label("Søg:");
		logoLabel = new Label(ZERO);
		logoLabel.setOpaque(false);


		// headerPanels
		customerPanel = new JPanel(new GridBagLayout());
		logPanel = new JPanel(new GridBagLayout());

		customerPanel.setBorder(customerTitle);
		logPanel.setBorder(logTitle);

		//search field
		searchField = new TextField(ZERO,true);
		
		// makes a document to register changes
		Document searchFieldDoc = searchField.getDocument();
		
		searchFieldDoc.addDocumentListener(this);

		//putProperty to retrieve changes in field in Update(DocumentEvent e)-methods
		searchFieldDoc.putProperty("name", "searchField");
		
		// - adding components to customerPanel
		c.ipady = 525;
		c.ipadx = 400;
		c.insets = new Insets(-10, 0, -60, 0);
		customerPanel.add(customerPane, c);

		c.ipady = 13;
		c.ipadx = 30;
		c.insets = new Insets(-70, -175, 480, 180);
		customerPanel.add(searchLabel, c);

		c.ipady = 13;
		c.ipadx = 90;
		c.insets = new Insets(0, -120, 550, 120);
		customerPanel.add(searchField, c);

		// - adding components to logPanel
		c.ipady = 600;
		c.ipadx = 800;
		c.insets = new Insets(50, 0, 0, 0);
		logPanel.add(logPane, c);

		c.ipady = 10;
		c.ipadx = 11;
		c.insets = new Insets(0, -70, 550, 580);
		logPanel.add(newEntryButton, c);

		c.ipady = 10;
		c.ipadx = 16;
		c.insets = new Insets(0, -100, 550, 330);
		logPanel.add(viewEntryButton, c);

		c.ipady = 10;
		c.ipadx = 9;
		c.insets = new Insets(0, -150, 550, 103);
		logPanel.add(openFileButton, c);
		
		 c.ipady = 10;
		 c.ipadx = 9;
		 c.insets = new Insets(0,-250,550,-120);
		 logPanel.add(deleteEntryButton,c);
		 
		 
		c.ipady = 300;
		c.ipadx = 1;
		c.insets = new Insets(25, -400, 100, 370);
		backgroundPanel.add(customerPanel, c);

		c.ipady = 600;
		c.ipadx = 500;
		c.insets = new Insets(25, 500, 100, 10);
		backgroundPanel.add(logPanel, c);

		setVisible(true);
	}
	
	//called whenever the gui needs to be updated
	private void doThreadSafeWork() {
		if (SwingUtilities.isEventDispatchThread()) {
			if(mainCaseNumber==6) {
				searchCustomer();
			}
		} 
		else {
			Runnable callDoThreadSafeWork = new Runnable() {
				public void run() {
					doThreadSafeWork();
				}
			};
			SwingUtilities.invokeLater(callDoThreadSafeWork);
		}
	}
	

	/** The following three methods are in the DocumentListener interface
	The methods takes a DocumentEvent as input and then it checks that if the
	Document in which the event was registered is the same object as the one associated with searchField,
	then do what is in the if-statement.
	 */

	@Override
	public void insertUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		if (doc.getProperty("name").equals("searchField")) {
			//checks whether last input from user was a space
			try {
				if(searchField.getText(searchField.getText().length()-1, 1).equals(" ")){
					return;
				}
			} catch (BadLocationException ble) 
			{
				ble.printStackTrace();
			}
				
			mainCaseNumber = 6;
			doThreadSafeWork();
		}
	}
	@Override
	public void removeUpdate(DocumentEvent e){
		//needs to return if searchField is empty to avoid a BadLocationException being thrown
		if(searchField.getText().equals("")) {
			customerTable.resetTableModel();
			logTable.resetTableModel();
			return;
		}
		
		//checks whether last input from user was a space
		Document doc = e.getDocument();
		if (doc.getProperty("name").equals("searchField")) {
			try {
				if(searchField.getText(searchField.getText().length()-1, 1).equals(" ")){
					return;
				}
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
		
		else if (doc.getProperty("name").equals("searchField")){
			mainCaseNumber = 6;
			doThreadSafeWork();
		}
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {}
	
	/**
	 * actionPerformed method for the buttons in the class
	 */
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() == newEntryButton) 
		{
	
			if(state.getSelectedCustomer() == null)
			{
				JOptionPane.showMessageDialog(null, "Vælg venligst en kunde, som du ønsker at oprette en log entry for");
				return;
			}
			State.getInstance().resetSelectedCustomersForExcelList();
			mediator.handle(Go.MAINGUI_NEWLOGGUI);
		}
		
		else if (event.getSource() == viewEntryButton) 
		{
			if(state.getSelectedLogEntry()!=null)
			{
				State.getInstance().resetSelectedCustomersForExcelList();
				mediator.handle(Go.MAINGUI_EDITLOGGUI);
				
			}
			else
				makeJOptionPane("Vælg venligst en log entry, som du ønsker at åbne");
		}
		else if(event.getSource() == openFileButton)
		{	
			if(state.getSelectedLogEntry().getAttachedFile() != null){
				logTable.openFileInNativeApp();
			}
			else if(state.getSelectedLogEntry()!=null){
				makeJOptionPane("Der er ingen fil tilknyttet den valgte log entry");
			}
			else{
				makeJOptionPane("Vælg venligst en log entry hvis tilknyttede fil du ønsker at åbne");
			}
		}
		else if(event.getSource() == deleteEntryButton)
		{
				logTable.deleteLogEntry();
		}
	}
	
	public void searchCustomer()
	{
		String searchString = searchField.getText();
		if(searchString.length() == 1){
			customerTable.updateCustomerTable(utilControl.searchCustomers(searchString, "main"));
		}
		else if(searchString.length() > 1){
			customerTable.updateCustomerTable(utilControl.trimCustomerList(state.getCustomersInCustomerTable(), searchString));
		}
	}

}
