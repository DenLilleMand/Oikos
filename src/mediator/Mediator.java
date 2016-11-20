
package mediator;

import javax.swing.JOptionPane;

import tables.CustomerTable;
import tables.CustomersForExcelTable;
import tables.LogEntryTable;
import tables.SelectedCustomersForExcelTable;
import gui.EditLogGUI;
import gui.LoginGUI;
import gui.MainGUI;
import gui.MultipleCustomersForExcelDialog;
import gui.NewLogGUI;
import gui.OptionsDialog;
import gui.ProgressBarDialog;
import gui.SuperLogGUI;
/**
 * (Matti)Tanker om denne klasse, her 01-08-2014 hvor jeg sidder og kigger
 * koden igennem. Alle getters/setters skal v�k fra denne klasse,
 * det er uansvarligt af os at lade alle vores felter bleede ud i vores
 * API p� denne m�de, vi kan desv�rre ikke lave felter final s� vidt jeg kan
 * se, s� ville vi skulle instansiere dem med det samme, hvilket vi ikke gider.
 * (Kunne dog overveje for login/maingui, der ville vi kunne bytte
 * det at instansiere dem med det samme, for at kunne lave dem final)
 * 
 * Hvad vigtigere er at getters og setters ikke burde v�re n�dvendige.
 * getters() burde altid returnere en kopi af vores objekt, hvilket 
 * ingen mening giver n�r det er et gui objekt. Og setters er n�rmest
 * det v�rste, vi har i det mindste lavet vores mainGui og loginGui
 * final, hvilket ikke n�dvendigvis er for de rigtige �rsager, 
 * men hvis de ikke havde v�ret det ville man kigge p� et k�mpe sikkerheds hul.
 * anyway - vi bruger setters i konstrukt�ren af de objekter
 * vi laver her, s� vi s�tter et objekt til en reference,
 * som vi allerede har. - s� vi �bner op for vores 
 * interne implementation og fremst�r som idioter samtidigt.
 */
/**
 * Mediator.java
 * The class provides functionality for the Mediator design pattern.
 * It is the class from where the Guis and complex gui elements are instantiated
 * and through which they communicate with each other
 * @author Jon
 */
public final class Mediator implements IMediator 
{
	private LoginGUI loginGui;
	private MainGUI mainGui;
	private NewLogGUI newLogGui;
	private EditLogGUI editLogGui;
	private SuperLogGUI superLogGui;//Abstract class
	private MultipleCustomersForExcelDialog multipleCustomersForExcelDialog;
	private ProgressBarDialog progressBarDialog;
	private LogEntryTable logEntryTable;
	private CustomerTable customerTable;
	private SelectedCustomersForExcelTable selectedCustomersForExcelTable;
	private CustomersForExcelTable customersForExcelTable;
	private OptionsDialog optionsDialog;
	private Go guiSequence;
	private static Mediator instance;
	
	//private constructor - only to be accessed from within this class
	private Mediator()
	{
		if(instance != null)
		{
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}
	
	public static synchronized Mediator getInstance()
	{
		if (instance == null)
		{
			instance = new Mediator();
		}
		return instance;
	}
	
	//Set metods, so the mediator is working with the correct GUI objects
	
	public void setGuiSequence(Go guiSequence){
		this.guiSequence = guiSequence;
	}
	
	public void setLogEntryTable(LogEntryTable logEntryTable){
		this.logEntryTable = logEntryTable;
	}
	
	public void setCustomerTable(CustomerTable customerTable){
		this.customerTable = customerTable;
	}
	
	public void setMainGui(MainGUI mainGui){
		this.mainGui = mainGui;
	}
	
	public void setLoginGui(LoginGUI loginGui){
		this.loginGui = loginGui;
	}
	
	public void setNewLogGui(NewLogGUI newLogGui){
		this.newLogGui = newLogGui;
	}
	
	public void setEditLogGui(EditLogGUI editLogGui){
		this.editLogGui = editLogGui;
	}
	
	public void setSuperLogGui(SuperLogGUI superLogGui){
		this.superLogGui = superLogGui;
	}
	
	public void setMultipleCustomersForExcelDialog(MultipleCustomersForExcelDialog multipleCustomersForExcelDialog){
		this.multipleCustomersForExcelDialog = multipleCustomersForExcelDialog;
	}
	
	public void setProgressBarDialog(ProgressBarDialog progressBarDialog){
		this.progressBarDialog = progressBarDialog;
	}
	
	public void setSelectedCustomersForExcelTable(SelectedCustomersForExcelTable setSelectedCustomersForExcelTable){
		this.selectedCustomersForExcelTable = setSelectedCustomersForExcelTable;
	}
	
	public void setCustomersForExcelTable(CustomersForExcelTable customersForExcelTable){
		this.customersForExcelTable = customersForExcelTable;
	}
	
	public void setOptionsDialog(OptionsDialog optionsDialog) {
		this.optionsDialog = optionsDialog;
	}

	//Get methods for GUI objects
	
	public Go getGuiSequence(){
		return guiSequence;
	}
	
	public LogEntryTable getLogEntryTable(){
		return logEntryTable;
	}
	
	public CustomerTable getCustomerTable(){
		return customerTable;
	}
	
	public MainGUI getMainGui(){
		return mainGui;
	}
	
	public SuperLogGUI getSuperLogGui(){
		return superLogGui;
	}
	
	public NewLogGUI getNewLogGui(){
		return newLogGui;
	}
	
	public EditLogGUI getEditLogGui(){
		return editLogGui;
	}
	
	public MultipleCustomersForExcelDialog getMultipleCustomersForExcelDialog(){
		return multipleCustomersForExcelDialog;
	}
	
	public ProgressBarDialog getProgressBarDialog(){
		return progressBarDialog;
	}
	
	public SelectedCustomersForExcelTable getSelectedCustomersForExcelTable(){
		return selectedCustomersForExcelTable;
	}
	
	public CustomersForExcelTable getCustomersForExcelTable(){
		return customersForExcelTable;
	}
	
	public OptionsDialog getOptionsDialog() {
		return optionsDialog;
	}


	//handle(String state) sends the user from gui to gui
	public void handle(Go go)
	{
				switch (go) 
				{
				//open the login gui
				case LOGIN:
					loginGui = new LoginGUI();
					break;
				case LOGINGUI_MAINGUI:
					loginGui.dispose();
					mainGui = new MainGUI();
					break;
					//if another user will log in
				case MAINGUI_LOGINGUI:
					mainGui.dispose();
					loginGui = new LoginGUI();
				break;
				//Logging in
				case MAINGUI_NEWLOGGUI:
					newLogGui = new NewLogGUI();
				break;
				
				//Logging in
				case MAINGUI_EDITLOGGUI:
				try
				{
					editLogGui = new EditLogGUI();	
				}
				catch(NullPointerException npe)
				{
					npe.printStackTrace();
					JOptionPane.showMessageDialog(mainGui, "Det logentry du har klikket på findes ikke, på baggrund af en fejl. Prøv"
							+ " at genstarte din søgning for at genopfriske din logentry liste");
				}
				break;
				
				//Logging in
				case EDITLOGGUI_PROGRESSBARDIALOG:
					editLogGui.setVisible(false);
					new ProgressBarDialog(); //noter pr 04-08-2014, Hvis vi giver variablen et navn, beh�ver vi ikke
											//at bruge en setter til progressBarDialog over i dens constructor.
				break;
				
				//Logging in
				case NEWLOGGUI_PROGRESSBARDIALOG:
					newLogGui.setVisible(false);
					new ProgressBarDialog();
				break;
				
				//Logging in
				case NEWLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG:
					setGuiSequence(Go.NEWLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG_NEWLOGGUI);
					newLogGui.setVisible(false);
					new MultipleCustomersForExcelDialog();
				break;
				
				//multiple customers for newLog
				//setsVisible(false) since the user is returning to this gui after adding info to multipleCustomersForExcelDialog
				case MULTIPLECUSTOMERSFOREXCELDIALOG_NEWLOGGUI:
					multipleCustomersForExcelDialog.dispose();
					newLogGui.setVisible(true);
				break;
				//from editLog to MultipleCustomersForExcelDialog
				case EDITLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG:
					setGuiSequence(Go.EDITLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG_EDITLOGGUI);
					editLogGui.setVisible(false);
					new MultipleCustomersForExcelDialog();
					break;
				
				//multipleCustomers for editlog
				case MULTIPLECUSTOMERSFOREXCELDIALOG_EDITLOGGUI:
					multipleCustomersForExcelDialog.dispose();
					editLogGui.setVisible(true);
					break;

				//from main gui to OptionsGUI	
				case MAINGUI_OPTIONSGUI:
					new OptionsDialog();
					break;
				//from OptionsGUI to MainGUI	
				case OPTIONSGUI_MAINGUI:
					optionsDialog.dispose();
					break;

					
				case EDITLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG_EDITLOGGUI:
					multipleCustomersForExcelDialog.dispose();
					editLogGui.setVisible(true);
					break;
					
				case NEWLOGGUI_MULTIPLECUSTOMERSFOREXCELDIALOG_NEWLOGGUI:
					multipleCustomersForExcelDialog.dispose();
					newLogGui.setVisible(true);
					break;
				//Exit
				case EXIT:
					if(mainGui!=null)
					{
						mainGui.dispose();
					}
					System.exit(0);
					break;
				
				default:
					break;	
		}
	}

	
}

