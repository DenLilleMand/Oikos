
package gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import components.Button;
import components.CustomTitledBorder;
import components.Label;
import components.TextField;

import mediator.Go;
import wrapper.DBWrapper;

/**
 * 
 * LoginGUI - extends SuperGUI and is responsible for login. 
 * @author Mads, Matti
 */
public final class LoginGUI extends SuperGUI implements ActionListener{


	private static final long serialVersionUID = 2842053797443804808L;

	private JPanel loginPanel;

	//fields
	//a JPasswordField does not show the characters that the user types
	private JPasswordField passwordField;
	private JTextField usernameField;

	private Label usernameLabel, passwordLabel,dbToUseLabel;

	// Temporary selection of database for development
	private String[] dbToUseOptions = {"<someRelevantString>", "localhost"};
	private JComboBox<String> dbToUse;

	private Button loginButton;

	private CustomTitledBorder title;
	private DBWrapper dbWrapper = DBWrapper.getInstance();
	
	public LoginGUI()
	{
		mediator.setLoginGui(this);

		setTitle("Olympus");


		passwordField = new JPasswordField();
		usernameField = new TextField("", true);

		usernameLabel = new Label("Brugernavn:");
		passwordLabel = new Label("Kodeord:");

		dbToUse = new JComboBox<String>(dbToUseOptions);
		dbToUse.setSelectedIndex(0);
		dbToUse.addActionListener(this);
		dbToUseLabel = new Label("Connect to Database:");

		title = new CustomTitledBorder("Log Ind");
		loginButton = new Button("Log Ind");
		loginButton.addActionListener(this);
		loginButton.setForeground(Color.GREEN);

		loginPanel = new JPanel(new GridBagLayout());

		loginPanel.setOpaque(false);

		loginPanel.setBorder(title);

		//set components in loginPanel
		c.ipady = 5;
		c.ipadx = 20;
		c.insets = new Insets(200, 0, 45, 0);
		loginPanel.add(loginButton, c);

		c.ipady = 10;
		c.ipadx = 100;
		c.insets = new Insets(0, 100, 0, 0);
		loginPanel.add(passwordField, c);

		c.ipady = 25;
		c.ipadx = 100;
		c.insets = new Insets(0, 47, 0, 100);
		loginPanel.add(passwordLabel, c);

		c.ipady = 25;
		c.ipadx = 100;
		c.insets = new Insets(0, 30, 100, 100);
		loginPanel.add(usernameLabel, c);

		c.ipady = 10;
		c.ipadx = 100;
		c.insets = new Insets(0, 100, 100, 0);
		loginPanel.add(usernameField, c);


		// Adding the temporary label
		c.ipady = 25;
		c.ipadx = 100;
		c.insets = new Insets(0, 35, -80, 100);
		loginPanel.add(dbToUseLabel, c);

		// Adding the temporary combobox
		c.ipady = 10;
		c.ipadx = 10;
		c.insets = new Insets(0, 100, -80, 0);
		loginPanel.add(dbToUse, c);

		//set components in backgroundPanel:
		c.ipady = 1;
		c.ipadx = 25;
		c.insets = new Insets(0, 0, 100, 0);
		backgroundPanel.add(loginPanel, c);

		/**Returns the rootPane object for this frame and sets default button.
		 * The default button is the button which will be activated when a UI-defined
		 * activation event (typically the Enter key) occurs in the root pane regardless of whether
		 * or not the button has keyboard focus
		 */
		getRootPane().setDefaultButton(loginButton);

		menu.remove(logoutItem);
		menu.remove(optionsItem);

		setVisible(true);
	}


	public void actionPerformed(ActionEvent e){

		if(e.getSource() == loginButton){
			/*
			 * takes input username and password and call utilcontrol's attemptlogin method.
			 * utilcontrol.attemptLogin will call DBControl.attemptlogin, which will in turn call
			 * DBWrapper.attemptlogin.
			 * DBWrapper.attemptLogin will call the stored procedure attemptLogin on the database, which
			 * handles salting and hashing of password, checking that value against the stored hashed password
			 * in the database. If it evaluates to true, it will return a user as a resultset, create that user and set it as 
			 * the current user. It will then return true or false all the way back through the chain of calls. 
			 */
			if (utilControl.attemptLogin(usernameField.getText(), new String(passwordField.getPassword())))
			{
				Ressources.setPictureNumber(2);
				mediator.handle(Go.LOGINGUI_MAINGUI);
			}
		}


		if (e.getSource() == dbToUse){
			if (dbToUse.getSelectedIndex() == 0){
				System.out.println("Using online db");
				dbWrapper.useOnlineDb();
			}
			else if (dbToUse.getSelectedIndex() == 1){
				System.out.println("Using localhost db");
				dbWrapper.useLocalhostDb();
			}
			else {
				System.out.println("No db selected. Will use online");
				dbWrapper.useOnlineDb();
			}

		}
	}
}
