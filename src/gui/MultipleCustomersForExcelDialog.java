
package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import commandPatternForMultipleCustomersForExcel.Command;
import commandPatternForMultipleCustomersForExcel.LeftClick;
import commandPatternForMultipleCustomersForExcel.RightClick;
import components.Button;
import components.CustomArrowButton;
import components.CustomTitledBorder;
import components.Label;
import tables.CustomersForExcelTable;
import tables.SelectedCustomersForExcelTable;
import tables.State;
import mediator.Mediator;
import control.UtilControl;
import entity.Customer;

/**
 * MultipleCustomersForExcel.java
 *	A custom implementation of JDialog. Its responsible
 *	for showing 2 customer tables, one to search in, and another for the "selected customers" 
 * 	whom will be added to the excel sheet, allso allows us to add a surden Customer to the dankort 2 
 * 	sheet in excel. allso has has focus on not being able to do something unrationel such as
 * removing our main customer, or being able to add the same customer twice.  In addition it has
 * a memory of customers, so if you close it down and open it again, as long as you didn¨t press Annuler or godkend
 * in newLogGUI it will remember the state.
 * @author Matti
 */
public final class MultipleCustomersForExcelDialog extends JDialog implements DocumentListener, ActionListener
{

	private static final long serialVersionUID = -4160967943503103033L;

	private UtilControl utilControl;
	
	//JPanels
	private JPanel backgroundPanel, customerPanel,selectedPanel, contentPanel;
	
	//borders
	private CustomTitledBorder customerPanelBorder,selectedPanelBorder, headerPanelBorder;
	
	//search field
	private JTextField searchField;
	private Label searchLabel;
	
	//tables
	private CustomersForExcelTable customerTable;
	private SelectedCustomersForExcelTable selectedTable;
	
	// JScrollPane
	private JScrollPane selectedPane, customerPane;
	
	//Buttons
	private CustomArrowButton rightArrowButton, leftArrowButton;
	private Button confirmButton, cancelButton, addDankort2Button, removeDankort2Button;

	private ArrayList<Button> buttonList = new ArrayList<Button>();
	private ArrayList<CustomArrowButton> arrowButtonList = new ArrayList<CustomArrowButton>();
	

	private GridBagConstraints c;
	
	/**
	 * constants are static final since there is no reason to
	 * have one copy per instance if they don't change
	 */
	private static final String ZERO = "";
	private static final String CARD = "card";
	private static final char SPACE = ' '; 
	
	/**Liste brugt til at implementere et commandpattern, sådan at vi kan lave
	 * scrollbacks på evt. ændringer efter annuller er blevet trykket på,
	 * den bliver initialiseret i konstrukturen alene fordi vi vil undgå
	 * at listen ikke bliver fornyet hvergang constructoren kører - og så
	 * vil vi også minimere vores scope på variabler mest muligt.  */
	private ArrayList<Command> commandList;

	
	//singletons
	private Mediator mediator;
	private State state;

	public MultipleCustomersForExcelDialog(){
		utilControl = UtilControl.getInstance();
		mediator = Mediator.getInstance();
		mediator.setMultipleCustomersForExcelDialog(this);
		state = State.getInstance();
		
		//modal precautions
		setModal(true);
	    addWindowFocusListener(new WindowFocusListener(){

	        @Override
	        public void windowLostFocus(WindowEvent event) {}

	        @Override
	        public void windowGainedFocus(WindowEvent event) {
	           toFront();        
	        }
	    });
	    
	    /**init af listen der står for CommandPattern */
	    commandList = new ArrayList<Command>();
		
		//Tables
		customerTable = new CustomersForExcelTable();
		selectedTable = new SelectedCustomersForExcelTable();
		selectedTable.updateSelectedCustomersForExcelTable();
		
		//ScrollPanes
		selectedPane = new JScrollPane(selectedTable);
		customerPane = new JScrollPane(customerTable);
		
		// border
		customerPanelBorder = new CustomTitledBorder("Kunder");
		selectedPanelBorder = new CustomTitledBorder("Tilføjet");
		headerPanelBorder = new CustomTitledBorder("Tilføj yderligere kunder til " + State.getInstance().getSelectedCustomer().getFirstName() +
				SPACE + State.getInstance().getSelectedCustomer().getLastName() + " excel ark");
		
		
		customerPanelBorder.setTitleJustification(TitledBorder.CENTER);
		selectedPanelBorder.setTitleJustification(TitledBorder.CENTER);
		headerPanelBorder.setTitleJustification(TitledBorder.CENTER);
		
		//init for panels
		customerPanel = new JPanel(new GridBagLayout());
		selectedPanel = new JPanel(new GridBagLayout());
		contentPanel = new JPanel(new GridBagLayout());
		
		//adding borders
		customerPanel.setBorder(customerPanelBorder);
		selectedPanel.setBorder(selectedPanelBorder);
		contentPanel.setBorder(headerPanelBorder);
		

		searchField = new JTextField();
		Document searchFieldDoc = searchField.getDocument(); // makes a document
		searchFieldDoc.putProperty("name", "searchField");
		searchFieldDoc.addDocumentListener(this);
		searchLabel = new Label("Søg:");

		
		//Button init
		rightArrowButton = new CustomArrowButton("right");
		leftArrowButton = new CustomArrowButton("left");
		confirmButton = new Button("Godkend");
		cancelButton = new Button("Annuller");
		addDankort2Button = new Button("Tilføj til dankort 2"); //add this in right click aswell
		removeDankort2Button = new Button("Fjern fra dankort 2");

		
		arrowButtonList.add(rightArrowButton);
		arrowButtonList.add(leftArrowButton);
		for(CustomArrowButton b : arrowButtonList){
			b.addActionListener(this);
		}
		
		buttonList.add(confirmButton);
		buttonList.add(cancelButton);
		buttonList.add(addDankort2Button);
		buttonList.add(removeDankort2Button);
		
		for(Button b : buttonList){
			b.addActionListener(this);
		}
		
		//the rug that ties the room together
		backgroundPanel = new JPanel(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		
		//customerPanel - setting components
		c.ipady = 13;
		c.ipadx = 90;
		c.insets = new Insets(-200,-125,200,125);
		customerPanel.add(searchField,c);
		
		c.ipady = 13;
		c.ipadx = 30;
		c.insets = new Insets(-200,-175,200,175);
		customerPanel.add(searchLabel,c);
		
		c.ipady = 375;
		c.ipadx = 410;
		c.insets = new Insets(25,0,-25,0);
		customerPanel.add(customerPane,c);
		
		//selectedPanel - setting components
		c.ipady = 375;
		c.ipadx = 450;
		c.insets = new Insets(25,0,-25,0);
		selectedPanel.add(selectedPane,c);
		
		c.ipady = 8;
		c.ipadx = 1;
		c.insets = new Insets(-200,-180,200,140);
		
		selectedPanel.add(addDankort2Button,c);
		c.ipady = 8;
		c.ipadx = 1;
		c.insets = new Insets(-200,0,200,20);
		selectedPanel.add(removeDankort2Button,c);
		
		//background Panel - setting components
		c.ipady = 50;
		c.ipadx = 1;
		c.insets = new Insets(-50,-250,0,270);
		contentPanel.add(customerPanel,c);
		
		c.ipady = 50;
		c.ipadx = 1;
		c.insets = new Insets(-50,330,0,-157);
		contentPanel.add(selectedPanel,c);
		
		c.ipady = 25;
		c.ipadx = 30;
		c.insets = new Insets(-60,-20,80,18);
		contentPanel.add(rightArrowButton,c);
		
		c.ipady = 25;
		c.ipadx = 30;
		c.insets = new Insets(-10,-20,40,18);
		contentPanel.add(leftArrowButton,c);
		
		c.ipady = 15;
		c.ipadx = 65;
		c.insets = new Insets(245,-50,-235,100);
		contentPanel.add(confirmButton,c);

		c.ipady = 15;
		c.ipadx = 65;
		c.insets = new Insets(245,50,-235,-100);
		contentPanel.add(cancelButton,c);

		setSize(1000,600); 
		
		c.ipady = 110;
		c.ipadx = 310;
		c.insets = new Insets(0,0,0,0);
		backgroundPanel.add(contentPanel,c);
		
		add(backgroundPanel);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

	/**
	 * Called whenever the gui is updated
	 */
	private void doThreadSafeWork(final int caseNumber)
	{
		//Returns true if the current thread is an AWT event dispatching thread.
		if (SwingUtilities.isEventDispatchThread()) 
		{
			switch (caseNumber) 
			{
			case 1:
				updateSearch();
				break;
			case 2:
				updateRightArrow();
				break;
			case 3:
				updateLeftArrow();
				break;
			case 4:
				updateCustomerTable(new ArrayList<Customer>());
				break;
			
			case 6:
				selectedTable.updateSelectedCustomersForExcelTable();
	            break;
			}
		} 
		else {
			Runnable callDoThreadSafeWork = new Runnable() 
			{
				public void run() 
				{
					doThreadSafeWork(caseNumber);
				}
			};
			//the component can be updated whenever the EDT has the time
			SwingUtilities.invokeLater(callDoThreadSafeWork);
		}
	}
	

	/**
	 * updates the list of customers for Excel in the State class by sorting the list 
	 */
	private void updateSearch()
	{
		String searchString = searchField.getText();
		/**Åbenbart et check som CommandPattern krævede for ikke at smide
		 * en exception */
		if(searchString == ZERO)
		{
			return;
		}
		try 
		{
			if(searchField.getText(searchString.length()-1, 1).equals(" "))
			{
				return;
			}
		} catch (BadLocationException ble) 
		{
			ble.printStackTrace();
			return;
		}
		
		if(searchString.length() == 1)
		{
			updateCustomerTable(utilControl.searchCustomers(searchString, "excel"));
		}
		else if(searchString.length() > 1)
		{
			updateCustomerTable(utilControl.trimCustomerList(state.getCustomersForExcel(), searchString));
		}
		customerTable.clearSelection();
	}

	private void updateCustomerTable(ArrayList<Customer> inputList)
	{
		customerTable.updateCustomerForExcelTable(utilControl.sortListForExcel(inputList));
	}

	private void updateRightArrow()
	{
		if(state.getCustomerForExcel() == null)
		{
			return;
		}
		else if(state.getCustomerForExcel() == state.getSelectedCustomer())
		{
			return;
		}
		else if(state.getSelectedCustomersForExcel().size() >= 3){
			makeJOptionPane("Du kan højst tilføje 3 kunder til et Excelark");
			return;
		}
		else
		{
			RightClick rightClick = new RightClick(state.getCustomerForExcel());
			rightClick.execute(state.getCustomersForExcel(),state.getSelectedCustomersForExcel());
			state.setCustomerForExcel(null);
			updateSearch();
			selectedTable.updateSelectedCustomersForExcelTable();
		}
	}
	

	private void updateLeftArrow()
	{
		//Checks for the nullPointerexception waiting to happen, and if there is, we just simply avoid it by
		//returning, it also instantly declines leftButton click if nothing is selected.
		if(state.getSelectedCustomerForExcel() == null)
		{
			return;
		}
		if(state.getSelectedCustomerForExcel().getId() == state.getSelectedCustomer().getId())
		{
			makeJOptionPane("Du kan ikke fjerne din primære kunde fra Excelarket");
			return;
		}
		LeftClick leftClick = new LeftClick(state.getSelectedCustomerForExcel());
		commandList.add(leftClick);
		leftClick.execute(state.getSelectedCustomersForExcel(),null);
		updateSearch();
		selectedTable.updateSelectedCustomersForExcelTable();
		
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) 
	{
		Document doc = e.getDocument();
		if (doc.getProperty("name").equals("searchField")) 
		{
			doThreadSafeWork(1);
		}
	}
	
	@Override
	public void removeUpdate(DocumentEvent e)
	{
		Document doc = e.getDocument();
		
		if(searchField.getText().equals(ZERO))
		{
			doThreadSafeWork(4);
		}
		else if (doc.getProperty("name").equals("searchField")){
			doThreadSafeWork(1);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		doThreadSafeWork(1);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == rightArrowButton)
		{
			doThreadSafeWork(2);
		}
		else if(event.getSource() == leftArrowButton)
		{
			doThreadSafeWork(3);
		}
		else if(event.getSource() == confirmButton)
		{
			state.setSelectedCustomersForExcel(state.getSelectedCustomersForExcel());	
			mediator.getSuperLogGui().setListForComboBoxAndUpdate(state.getSelectedCustomersForExcel());
			mediator.handle(mediator.getGuiSequence());
		}
		else if(event.getSource() == cancelButton)
		{
			/**Should consider making some of these calls less extensive */
			//ArrayList<Customer> tempCustomerList = state.getSelectedCustomersForExcel();
			for(Command c : commandList)
			{
				c.undo(state.getSelectedCustomersForExcel());
			}
			//mediator.getMultipleCustomersForExcelDialog().selectedTable.cancelMultipleCustomersForExcel();
			mediator.handle(mediator.getGuiSequence());
		
		}
		else if(event.getSource() == addDankort2Button){
			if(state.getSelectedCustomerForExcel() != null){
			for(Customer c : state.getSelectedCustomersForExcel()){
				c.setDankort(ZERO);
			}
			state.getSelectedCustomerForExcel().setDankort(CARD);
			doThreadSafeWork(6);
			}
		}
		else if(event.getSource() == removeDankort2Button){
			if(state.getSelectedCustomerForExcel() != null){
			state.getSelectedCustomerForExcel().setDankort(ZERO);
			doThreadSafeWork(6);
			}
		}
	}
	
	public void makeJOptionPane(String message){
		JOptionPane.showMessageDialog(null, message);
	}
}
