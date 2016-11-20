
package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import components.Button;
import components.CustomTitledBorder;
import components.ProgressBar;
import mediator.FileCase;
import mediator.Mediator;
import tables.State;



/**
 * ProgressBarDialog.java
 * A custom implementation of JDialog, this is the class responsible for all the different use-cases relating
 * newLog & EditLog whenever they have a file attached, either excel or another. 
 * it controls the swingWorker which is a innerclass in this class. the SwingWorker is the class that controls our
 * progressBar together with the newLogEntry/EditLogEntry variations.
 * @author Matti, Peter
 */
public final class ProgressBarDialog extends JDialog implements ActionListener 
{		

	private static final long serialVersionUID = 1L;

	//the progressBar + the class that extends SwingWorker
	protected ProgressBar progressBar;
	protected ProgressWorker pw;

	//Panels
	protected JPanel backgroundPanel, contentPanel;

	//updatefield
	protected JTextField taskField;

	//Border
	protected CustomTitledBorder contentPanelBorder;

	//switch case number
	private int caseNumber;

	//Buttons to accept / open file after progressDialog is done
	private Button confirmButton,  openFileButton;

	private final String message =  "Processen er f√¶rdig";

	//mediator
	private Mediator mediator;


	//arraylist for buttons
	private ArrayList<Button> buttonList = new ArrayList<Button>();

	/**we NEED timers to start the whole thing, because if we do it inside the constructor the progressBar
	 * will be on 100% when we open it up ^^ . because all gui work is threadsafe until the setVisible(true).
	 * and even if we make the calls after the setVisible(true) - the methods called after is aparently still not shown
	 * until the constructor is done doing it's work .. thats why we have a timer to kick the whole thing
	 * into motion.
	 */

	private Timer timer;
	private int speed = 100 , initialPause = 0;


	private SuperLogGUI superLogGui;

	public ProgressBarDialog() 
	{
		setSize(400,220);
		setLocationRelativeTo(null);
		mediator = Mediator.getInstance();
		mediator.setProgressBarDialog(this);

		superLogGui = mediator.getSuperLogGui();

		//field
		taskField = new JTextField();
		taskField.setBorder(null);
		taskField.setEditable(false);

		//buttons
		confirmButton = new Button("Godkend");
		openFileButton = new Button("√Öben fil");

		buttonList.add(confirmButton);
		buttonList.add(openFileButton);

		//JPanels
		backgroundPanel = new JPanel(new GridBagLayout()); 
		contentPanel = new JPanel(new GridBagLayout());
		contentPanelBorder = new CustomTitledBorder("H√•ndterer Log Opgaver");
		contentPanelBorder.setTitleJustification(TitledBorder.CENTER);
		contentPanel.setBorder(contentPanelBorder);

		//progressBar
		progressBar = new ProgressBar();

		//inner class - progressWorker, extends SwingWorker, permit to do work in background
		pw = new ProgressWorker();




		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;

		//adding stuff to the openFilePane
		c.ipady = 25;
		c.ipadx = 308;
		c.insets = new Insets(0,0,50,0);
		contentPanel.add(progressBar,c);

		c.ipady = 15;
		c.ipadx = 320;
		c.insets = new Insets(25,0,-25,0);
		contentPanel.add(taskField,c);

		//setting components for backgroundPanel
		c.ipady = 60;
		c.ipadx = 30;
		c.insets = new Insets(-50,0,50,0);
		backgroundPanel.add(contentPanel,c);

		c.ipady = 15;
		c.ipadx = 65;
		c.insets = new Insets(50,-75,-75,75);
		backgroundPanel.add(confirmButton,c);

		c.ipady = 15;
		c.ipadx = 65;
		c.insets = new Insets(50,75,-75,-75);
		backgroundPanel.add(openFileButton,c);
		//int top, int left, int bottom, int right)
		add(backgroundPanel);

		//setting listeners to buttons
		for(Button b : buttonList)
		{
			b.addActionListener(this);
			b.setEnabled(false);
		}
		
		switch (State.fileCase)
		{
		case NONE:
			superLogGui.attachedFile = null;
			break;
		case EDITEXCEL:
			superLogGui.attachedFile = null;
			break;	
		case FILE:
			break;
		case NEWFILE:

			break;	
		case EDITFILE:

			break;
		default:
			break;
		}	

		//adding the component to the JDialog

		//Switch cases checking for which case we have to handle - 3 options are available, no file - excel file - other file.
		

		ActionListener timerListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				pw.execute();//executes the progressWorker
			}
		};
		//Creates a Timer and initializes both the initial delay and between-event delay to delay milliseconds
		timer = new Timer(speed, timerListener);//speed = 100; 
		timer.setInitialDelay(initialPause);//initialPause = 0;
		timer.setRepeats(false);//If flag is false, instructs the Timer to send only one action event to its listeners.
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true); 
		timer.start();
	}	

	/**
	 * doThreadSafeWork() - we try to keep the gui updates inside of the EDT.
	 */
	private void doThreadSafeWork() 
	{
		if(SwingUtilities.isEventDispatchThread())
		{
			if(caseNumber==5){
				progressBar.setString("Processen slog fejl");
				progressBar.setValue(100);
			}
			else if(caseNumber==6){
				progressBar.setValue(100);			     
			}
		}
		else{
			Runnable callDoThreadSafeWork = new Runnable(){
				public void run(){
					doThreadSafeWork();
				}
			};
			SwingUtilities.invokeLater(callDoThreadSafeWork);
		}
	}


	@Override
	public void actionPerformed(ActionEvent event){ 
		if(event.getSource() == confirmButton) {
			/**because of our setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); - we just dispose them 
			 * and as such, it ends the thread aswell.
			 */
			dispose();
		}
		else if(event.getSource() == openFileButton) {
			/**
			 * we set the selected log in table, so we can open the newly created file. 
			 * and at the same time, due to the fact that we set a selectedLog,
			 */
			mediator.getLogEntryTable().openFileInNativeApp();				
			dispose();
		}
	}
	/**
	 * ProgressWorker - a inner class that extends SwingWorker<Object, Object>
	 * the first declaration which is String in our case - is the return type of the method doInBackground(),
	 * while the 2nd declaration which is a Integer in this case, is the return type of the method in
	 * doInBackground() called publish(Object).
	 */
	//sets the returntype of two methods in the inner class
	class ProgressWorker extends SwingWorker<String, Integer> 
	{
		private final String[] excelPhases = {"Henter kunde data", "Skriver kunde data", "Skriver til fil"};
		private final String[] pdfPhases = {"Henter kunde data", "Udfylder pdf-formler med kundedata", "Skriver til fil"};
		
		Thread  logEntryThread = null;
		Thread counterThread = null;

		public ProgressWorker(){}

		/**
		 * @see javax.swing.SwingWorker#done()
		 * A method called whenever doInBackground() returns. 
		 */
		//after the return, up to -95
		protected void done(){
			counterThread.interrupt();	
			logEntryThread.interrupt();
			progressBar.setValue(100);
			caseNumber = 6;
			doThreadSafeWork();
			try 
			{
				//Waits if necessary for the computation to complete, and then retrieves its result.
				taskField.setText(get());
			} 
			catch (InterruptedException | ExecutionException e) 
			{
				taskField.setText(message);
			}
			for(Button b : buttonList)
			{
				b.setEnabled(true);
			}
		}	
		/**
		 * @see javax.swing.SwingWorker#doInBackground()
		 * doInBackground() - an overwritten method, that provides us a thread to do some work as long as it's
		 * not work related gui updateing. which is why it has outputs such as return and publish, in order
		 * to notify the methods being runned through the AWT(updated through EDT)
		 *  that they should grap this information given, and update the gui with it from within the EDT.
		 * In this case we've taken this method and split it up into 2 threads one thread calling newLogEntry
		 * and another counting from 1-100 meanwhile publishing new Integers.  we wait for both of the threads
		 * by calling join on both of them, when they're done we
		 * we return and hit the method done() with this return statement. 
		 */
		protected String doInBackground()
		{   
			try	
			{
				logEntryThread = new Thread() 
				{
					public void run() {
						try {
							Thread.sleep(10);
						} catch (Exception e)
						{
						}
						try {
							switchCase();
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						} catch (Exception e)
						{								
							e.printStackTrace();
						}
					}
				};
				counterThread = new Thread() 
				{
					public void run() 
					{
						for(int i = 0; i <= 95; i++)
						{
							publish(i);
							try {
								if(tables.State.fileCase == FileCase.NEWEXCEL || tables.State.fileCase == FileCase.EDITEXCEL)	
								{
									Thread.sleep(20);
								}
								else 
									Thread.sleep(10);
							} catch (InterruptedException e) 
							{
								Thread.currentThread().interrupt(); 
							}
						}
					}
				};
				counterThread.start();
				logEntryThread.start();
				logEntryThread.join();
				counterThread.join();

			}
			catch(Exception e)
			{
				return "Processen slog fejl";
			}
			return message; 
		}
		/**
		 * @see javax.swing.SwingWorker#process(java.util.List)
		 * process, another overwritten method from SwingWorker, 
		 * this method is being hit by the publish(Integer) method from doInBackground()
		 * and has a parameter List, due to the fact that publish(Integer) dont allways get run-time, so
		 * it has to be released in chuncks depending on how much time we give this method to run. 
		 * it allso contains methods that update the taskField.
		 */
		@Override
		protected void process(List<Integer> list) 
		{	        		
			//Returns the element at the specified position in this list.
			//so getting the 
			int size = list.get(list.size()-1);
			progressBar.setValue(size);
			int currentValue = progressBar.getValue();
			if(State.fileCase == FileCase.NEWEXCEL || State.fileCase == FileCase.EDITEXCEL)
			{
				doPhases(currentValue,excelPhases);
			}
			else
			{
				doPhases(currentValue,pdfPhases);
			}	
		}
		
		private void doPhases(int currentValue, String[] array)
		{
			if(currentValue >= 0 && currentValue <= 25)
			{
				taskField.setText(array[0].toString());
			}
			if(currentValue >= 26 && currentValue <= 50)
			{
				taskField.setText(array[1].toString());
			}
			else if(currentValue >= 50 && currentValue <= 99)
			{
				taskField.setText(array[2].toString());
			}
		}
		
		private void newLogEntryAndDispose() throws InterruptedException, Exception {
			mediator.getNewLogGui().newLogEntry();
			mediator.getNewLogGui().dispose();
		}

		private void editLogEntryAndDispose() throws InterruptedException, Exception {
			mediator.getEditLogGui().editLogEntry();
			mediator.getEditLogGui().dispose();
		}
		/**
		 * Kan smide alle cases med samme metode ind i den samme case. med en
		 * "eller" operator, kan bare ikke lige huske pÂ stÂende fod hvordan operatoren ser ud 
		 * 
		 * @throws InterruptedException
		 * @throws Exception
		 */
		private void switchCase() throws InterruptedException, Exception  
		{
			switch (State.fileCase) 
			{
			case NEWEXCEL:
				newLogEntryAndDispose();
				break;
			case EDITNEWEXCEL:
				editLogEntryAndDispose();
				break;
			case EDITNEWPDF:
				editLogEntryAndDispose();
				break;
			case EDITEXCEL:
				editLogEntryAndDispose();
				break;
			case EDITPDF:
				editLogEntryAndDispose();
				break;
			case FILE:
		
				break;
			case NEWFILE:
		
				break;
			case EDITFILE:
			
				break;
			case NEWPDF:
				newLogEntryAndDispose();
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
	}
}











