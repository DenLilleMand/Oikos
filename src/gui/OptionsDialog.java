package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import tables.State;
import mediator.Go;
import mediator.Mediator;
import mediator.UpdateLogTable;
import components.Button;
import components.CustomTitledBorder;
import components.Label;
import components.TextField;

public class OptionsDialog extends JDialog implements DocumentListener
{	
	private static final long serialVersionUID = -6932390639476272697L;
	private JPanel backgroundPanel,contentPanel;
	private TextField pathField;
	private Button confirmButton, declineButton, findDirButton;
	private Mediator mediator = Mediator.getInstance();
	private ActionListener buttonListener;
	private GridBagConstraints c;
	private CustomTitledBorder border;
	private Label verifyLabel,fileLabel ;
	private final String ZERO = "";
	private JFileChooser dirChooser;
	private State state;
	
	
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	
	public OptionsDialog()
	{
		state = State.getInstance();
		mediator.setOptionsDialog(this);
		init();
		addingComponentsToGivenLists();
		modalSecurity();
		
		buttonListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if(event.getSource() == confirmButton)
				{
					state.setFilePath(pathField.getText());
					if(mediator.getLogEntryTable() != null)
					{
						mediator.getLogEntryTable().dynamicallyUpdateLogModel(UpdateLogTable.NOTHING);
					}
					mediator.handle(Go.OPTIONSGUI_MAINGUI);
				}
				else if(event.getSource() == declineButton)
				{
					mediator.handle(Go.OPTIONSGUI_MAINGUI);
				}
				else if(event.getSource() == findDirButton)
				{
					doThreadSafeWork(3);
				}
			}
		};
		
		for(Button button : buttonList)
		{
			button.addActionListener(buttonListener);
		}
	    
	    settingComponents();
	    settings();
	}
	
	private void init()
	{
		confirmButton = new Button("Godkend");
		declineButton = new Button("Annuller");
		findDirButton = new Button("...");
		border = new CustomTitledBorder("Indstillinger");
		
		border.setTitleJustification(TitledBorder.CENTER);
		fileLabel = new Label("Sti til fællesdrevet:");
		verifyLabel = new Label(ZERO);
		pathField = new TextField(ZERO,true);
		// makes a document to register changes
		Document pathFieldDoc = pathField.getDocument();
		pathFieldDoc.addDocumentListener(this);
		backgroundPanel = new JPanel(new GridBagLayout());
		contentPanel = new JPanel(new GridBagLayout());
		contentPanel.setBorder(border);
		dirChooser = new JFileChooser();
		c = new GridBagConstraints();
	}
	
	private void addingComponentsToGivenLists()
	{
		buttonList.add(confirmButton);
		buttonList.add(declineButton);
		buttonList.add(findDirButton);
	}
	
	private void settingComponents()
	{
		setComponent(new Insets(0,0,0,0), 1,1,backgroundPanel,contentPanel, 430,200);	
		setComponent(new Insets(60,-100,-60,100), 1,1,contentPanel,confirmButton, 65,15);
		setComponent(new Insets(60,100,-60,-100), 1,1,contentPanel,declineButton, 65,15);
		setComponent(new Insets(-110,0,70,-40), 1,1,contentPanel,pathField, 150,15);
		setComponent(new Insets(-110,-200,70,240), 1,1,contentPanel,fileLabel, 65,25);
		setComponent(new Insets(-110,340,70,-310), 1,1,contentPanel,verifyLabel, 65,25);
		setComponent(new Insets(-110,250,70,-250), 1, 1, contentPanel, findDirButton, 20, 10);
	 	this.getContentPane().add(backgroundPanel);
	}
	
	
	private void checkIfValidPath(String filePath)
	{
		File f = new File(filePath);
		if(f.exists())
		{
			doThreadSafeWork(1);
		}
		else
			doThreadSafeWork(2);
	}
	
	private void openFileChooser() 
	{
		try 
		{
			dirChooser = new JFileChooser();
			dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File directory = new File("");
			final int returnVal = dirChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				directory = dirChooser.getSelectedFile();
				pathField.setText(directory.toString());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * doThreadSafeWork() - we try to keep the gui updates inside of the EDT.
	 */
	private void doThreadSafeWork(final int caseNumber) 
	{
		if(SwingUtilities.isEventDispatchThread())
		{
			switch(caseNumber)
			{
			case 1:
				verifyLabel.setIcon(Ressources.checkedIcon);
				break;
			case 2:
				verifyLabel.setIcon(Ressources.uncheckedIcon);
				break;
			case 3:
				openFileChooser();
				break;
			case 4:
				checkIfValidPath(pathField.getText());
				break;
			}
		}
		else
		{
			Runnable callDoThreadSafeWork = new Runnable()
			{
				public void run()
				{
					doThreadSafeWork(caseNumber);
				}
			};
			SwingUtilities.invokeLater(callDoThreadSafeWork);
		}
	}
	
	private void setComponent(Insets insets, int gridx, int gridy, Container parent, Component component, int xSize, int ySize)
	{
		c.insets = insets;
		c.gridx = gridx;
		c.gridy = gridy;
		c.ipady = ySize;
		c.ipadx = xSize;
		parent.add(component,c);
	}
	
	private void modalSecurity()
	{
		setModal(true );
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
	}
	
	private void settings()
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	   	this.pack();
	   	this.setSize(750,300);
	   	this.setLocationRelativeTo(mediator.getMainGui());     
	   	this.setResizable(false);
	   	pathField.setText(state.getFilePath());
		checkIfValidPath(pathField.getText());
	   	this.setVisible(true);
	}

	@Override
	public void changedUpdate(DocumentEvent event) {}

	@Override
	public void insertUpdate(DocumentEvent event) 
	{
		doThreadSafeWork(4);
	}

	@Override
	public void removeUpdate(DocumentEvent event) 
	{
		doThreadSafeWork(4);
	}
}
