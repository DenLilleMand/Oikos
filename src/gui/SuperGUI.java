package gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import options.Options;
import options.OptionsLoader;
import mediator.Mediator;
import tables.State;
import components.Button;
import components.Menu;
import components.MenuItem;
import control.UtilControl;
import mediator.Go;

/**
 * SuperGUI is never instantiated, hence abstract 
 * @author Matti, Jon
 */
//SuperGUI is never instantiated, hence abstract
public abstract class SuperGUI extends JFrame 
{
	private static final long serialVersionUID = 6462103388502504424L;

	//objects
	protected Mediator mediator;
	protected UtilControl utilControl;
	protected State state;
	
	//til gridbag layout
	protected static GridBagConstraints c;
	//JPanels
	protected JPanel backgroundPanel;
	
	protected String picture;

	//JMenuBar
	protected JMenuBar menuBar;
	protected MenuItem exitItem, logoutItem, optionsItem, guideItem;
	protected Menu menu, guideMenu;
	
	//options 
	private Options options;
	
	//arraylists for componenter
	protected ArrayList<JMenuItem> itemList = new ArrayList<JMenuItem>();
	protected ArrayList<Button> buttonList = new ArrayList<Button>();
	
	protected final String ZERO ="";
	
	protected ActionListener listener;

	public SuperGUI() 
	{
		/**init
		 * singelton class are set here so all classes that extend this class have access to them
		 */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mediator = Mediator.getInstance();
		utilControl = UtilControl.getInstance();
		state = State.getInstance();
		try {
			this.options = OptionsLoader.load();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "Der var et problem da vi prøvede at loade din sti til fællesdrevet,"
					+ " inde i din 'User' folder kan du prøve at slette vores Olympus mappe, og loade programmet igen.");
			e1.printStackTrace();
		}
		state.setFilePath(options.getCollectiveFilePath());
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
		{
			public void run() 
			{
				try
				{
					options.setCollectiveFilePath(state.getFilePath());
					OptionsLoader.save(options);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}));
		
		setSize(1280, 800);
		setTitle("extend");
		picture = "login";
		
		//JMenuBar
		exitItem = new MenuItem("Exit");
		logoutItem = new MenuItem("Logout");
		optionsItem = new MenuItem("Indstillinger");
		guideItem = new MenuItem("Brugsanvisning");
		
		itemList.add(optionsItem);
		itemList.add(exitItem);
		itemList.add(logoutItem);
		itemList.add(guideItem);
		
		menuBar = new JMenuBar();
		menu = new Menu("Olympus");
		guideMenu = new Menu("Hjælp");
		
		menuBar.add(menu);
		menuBar.add(guideMenu);
		
		guideMenu.add(guideItem);	
		menu.add(optionsItem);
		menu.add(logoutItem);
		menu.add(exitItem);
				
		listener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if (event.getSource() == exitItem) 
				{
					mediator.handle(Go.EXIT);
				}
				else if(event.getSource() == logoutItem){

					Ressources.setPictureNumber(2);
					mediator.handle(Go.MAINGUI_LOGINGUI);
				}
				else if(event.getSource() == optionsItem)
				{
					mediator.handle(Go.MAINGUI_OPTIONSGUI);
				}
				else if(event.getSource() == guideItem)
				{
					try
					{
						utilControl.openGuide();	
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Programmet kunne ikke finde filen 'OlympusBrugerVejledning.pdf'");
					}
				}
			}
			
		};
		
		for(JMenuItem i : itemList){
			i.addActionListener(listener);
		}
		

		backgroundPanel = new JPanel(new GridBagLayout()) {
			protected static final long serialVersionUID = 1L;
			
			@Override //overriding built-in inherited method

			public void paintComponent(Graphics g)
			{
				if(picture == "login")
				{
					Ressources.OIKOSLOGO.paintIcon(this, g,310,100);
				}
				else if (picture =="maingui")
				{
					Ressources.OIKOSCOLORS.paintIcon(this, g,0,0);
				}
			}
		};		

//		int top, int left, int bottom, int right)
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
	
		//frame settings
		setJMenuBar(menuBar);
		add(backgroundPanel);
		setLocationRelativeTo(null);
		setResizable(false); 
	}

	//for generating JOptionPanes
	protected void makeJOptionPane(String message){
		JOptionPane.showMessageDialog(null, message);
	}
}

