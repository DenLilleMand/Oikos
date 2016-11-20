package tables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import entity.LogEntry;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import components.CheckRenderer;
import components.GlobalCellRenderer;
import components.JLabelRenderer;
import mediator.Go;
import mediator.UpdateLogTable;


/**
 * LogEntryTable.java
 * A table for logs, that extends the SuperTable. 
 * @author Jon, Matti
 */
public class LogEntryTable extends SuperTable{
	
	private static final long serialVersionUID = 4839264727318141392L;
	
	private JMenuItem viewEntryItem;
	private JMenuItem deleteEntryItem;
	private JMenuItem openFileItem;
	private int column;
	private int row;
	private DefaultTableModel logModel;
	private final String ZERO = "";
	
	public LogEntryTable(){
		
		//for mediator reference
		mediator.setLogEntryTable(this);
		
		//model init
		logModel = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;
			
			@Override
		    public boolean isCellEditable(int row, int column) 
			{
		       return false;
		    }
			
		};
		logModel.setColumnIdentifiers(new String[]{"","Oprettet Dato", "Oprettet Af", "Beskrivelse", "Redigeret Dato", "Sidst Redigeret Af", "Fil Type", "Invisible"});
		setModel(logModel);	
		
		//width of columns in ColumnModel
		getColumnModel().getColumn(0).setPreferredWidth(34);
		getColumnModel().getColumn(1).setPreferredWidth(135);
		getColumnModel().getColumn(2).setPreferredWidth(120);
		getColumnModel().getColumn(3).setPreferredWidth(132);
		getColumnModel().getColumn(4).setPreferredWidth(130);
		getColumnModel().getColumn(5).setPreferredWidth(120);
		getColumnModel().getColumn(6).setPreferredWidth(65);
		
		GlobalCellRenderer gcr = new GlobalCellRenderer();
		getColumnModel().getColumn(1).setCellRenderer(gcr);
		getColumnModel().getColumn(2).setCellRenderer(gcr);
		getColumnModel().getColumn(3).setCellRenderer(gcr);
		getColumnModel().getColumn(4).setCellRenderer(gcr);
		getColumnModel().getColumn(5).setCellRenderer(gcr);
		
		getColumnModel().getColumn(0).setCellRenderer(new CheckRenderer());
		//the JLabelRenderer is used to render the JLables that draws them
		getColumnModel().getColumn(6).setCellRenderer(new JLabelRenderer());
		
		
		final TableColumn hiddenColumn = getColumnModel().getColumn(7);//has to be final since its referred to in an inner class later
		hiddenColumn.setPreferredWidth(0);//lower the chance of the user seeing it before its removed
		removeColumn(hiddenColumn);//removes the almost invisible column
		
		//ListSelectionModel is an interface that represents the current state of the selection for any of the
		//components that display a list of values with stable indices.
		ListSelectionModel listSelectionModel = getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listSelectionModel.addListSelectionListener(new ListSelectionListener()
		{	
			public void valueChanged(ListSelectionEvent e)
			{
				//getValueIsAdjusting() checks whether an event is part of a chain and returns false only if its the final event in a chain
				if (e.getValueIsAdjusting()) return;
				if(logModel.getRowCount() == 0)
				{
					state.selectedLogEntry = null;
					return;
				}
	            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            	state.selectedLogRow = lsm.getMinSelectionIndex();
            	if(state.selectedLogRow < 0)
            	{
            		return;
            	}
            	//retrieving the hidden column
            	addColumn(hiddenColumn);
            	//setting the id of the selectedLogEntry
            	int selectedLogEntryId = ((int)getValueAt(state.selectedLogRow, 7));
            	removeColumn(hiddenColumn);
            	state.selectedLogEntry = utilControl.getSpecificElement(state.selectedCustomer.getLogEntryList(), selectedLogEntryId);
            
	        }
	    });
		
		final JPopupMenu popupMenu = new JPopupMenu();
		ActionListener popupActionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				if(event.getSource().equals(deleteEntryItem))
				{
					deleteLogEntry();//no need to make the check from mainGui, since selectedLog cannot be null in JPopupMenu
				}
				else if(event.getSource().equals(viewEntryItem))
				{
					mediator.handle(Go.MAINGUI_EDITLOGGUI);
				}	
				else if(event.getSource().equals(openFileItem))
				{
					openFileInNativeApp();
				}

			}
		 };
		 deleteEntryItem = new JMenuItem("Slet log");
	     viewEntryItem = new JMenuItem("Se log");
	     openFileItem = new JMenuItem("Åben fil");
	     
	     deleteEntryItem.addActionListener(popupActionListener);
	     viewEntryItem.addActionListener(popupActionListener);
	     openFileItem.addActionListener(popupActionListener);
	 
	     popupMenu.add(viewEntryItem);
	     popupMenu.addSeparator();
	     popupMenu.add(openFileItem);
	     popupMenu.addSeparator();
	     popupMenu.add(deleteEntryItem);
	     
	     addMouseListener( new MouseAdapter()
	     {
	         public void mouseClicked(MouseEvent e)
	         {
	        	 if(SwingUtilities.isRightMouseButton(e))
	        	 {
	                 row = rowAtPoint(e.getPoint());
	                 column = 1+ columnAtPoint(e.getPoint()); 
	                 changeSelection(row, column, false, false);
	                 popupMenu.show(e.getComponent(), e.getX(), e.getY()); 
	                 return;
	             }
	        	 column = columnAtPoint(e.getPoint());
	        	 if(SwingUtilities.isLeftMouseButton(e) && column == 0)
	        	 {
	        			addColumn(hiddenColumn);
	                 	int selectedLogEntryId = ((int)getValueAt(state.selectedLogRow, 7));
	                 	System.out.println(selectedLogEntryId);
	                 	removeColumn(hiddenColumn);
	                 	try
	                 	{
	                 		utilControl.setLogentryCheck(state.selectedCustomer.getLogEntryList(), selectedLogEntryId);	
	                 	}
	                 	catch(Exception exception)
	                 	{
	                 		exception.printStackTrace();
	                 		//handle this
	                 	}
	                 	updateLogTable(state.selectedCustomer.getLogEntryList());
	        	}
	        	 else if(SwingUtilities.isLeftMouseButton(e) && column == 6 && e.getClickCount() >= 2)
	        	 {
	        		 clearSelection();
	        		 openFileInNativeApp();
	        	 }
	        	else if(SwingUtilities.isLeftMouseButton(e))
	        	{
	        		if(e.getClickCount() == 2)
	        		{
	        			mediator.handle(Go.MAINGUI_EDITLOGGUI);
	        		}
	        	}
	         }
	     });
	}
	
	
	
	
	/**
	 * Method used to update the log table, takes an arraylist as parameter and uses it to update 
	 * without setting any global variable.
	 */    
	public void updateLogTable(ArrayList<LogEntry> entryList)
	{
		logModel.setRowCount(entryList.size());
		for(int i = 0; i < logModel.getRowCount(); i++)
		{
			setRowHeight(i, 40);
		}
		int row = 0;
		for(LogEntry log : entryList) 
		{
			logModel.setValueAt(log.getChecked(), row, 0);
			logModel.setValueAt(utilControl.dateToString(log.getCreateDate()), row, 1);
			logModel.setValueAt(log.getCreatedBy(), row, 2);
			logModel.setValueAt(log.getDescription(), row, 3);
			logModel.setValueAt(utilControl.dateToString(log.getLastEditDate()), row, 4);
			logModel.setValueAt(log.getLastEditBy(), row, 5);	
			logModel.setValueAt(log.getAttachedFile().getFileExtension(), row, 6);	
			logModel.setValueAt(log.getId(), row, 7);
			row++;
         }
		if(logModel.getRowCount() > 0)
		{}
 	}
	/**
	 * Delete a logEntry, creates a local variable of the state.getSelectedLogEntry, deletes the log,
	 * then checks whether the fileExtension is anything we allow/checks if a file exsist - 
	 * if thats the case - we go for a deletion on the file as well
	 * 
	 */
	 public void deleteLogEntry()
	 {
		 ArrayList<LogEntry> logEntryList = state.getSelectedCustomer().getLogEntryList();
		 if(state.selectedLogEntry != null)
		 {
			 	LogEntry l = state.selectedLogEntry;
				
				if(!l.getAttachedFile().toString().equalsIgnoreCase(ZERO))
				{
					utilControl.deleteSavedFile(l);
				}
				utilControl.deleteLogEntry(l);//deletes the logEntry in the db and in the selectedCustomer object
				logEntryList.remove(l); 
				//since a deleted LogEntry cannot be selected
				state.selectedLogEntry = null;	
		 }
		 ArrayList<LogEntry> tempList = new ArrayList<LogEntry>();
		 
			for (int i = 0; i < logEntryList.size();i++)
			{
				/**
				 * Make checked/notchecked an enum maybe, enums can contain a String value 
				 * if it's really intentionel to make a String comparison somehow. -matti
				 */
				if(logEntryList.get(i).getChecked().equalsIgnoreCase("checked"))
				{
						if(!logEntryList.get(i).getAttachedFile().toString().equalsIgnoreCase(ZERO))
			 			{
			 				utilControl.deleteSavedFile(logEntryList.get(i));
			 			}
						utilControl.deleteLogEntry(logEntryList.get(i));
						tempList.add(logEntryList.get(i));
				}
			}
		logEntryList.removeAll(tempList);
 		updateLogTable(logEntryList);
 		clearSelection();
	 }
	

	 /**
	  * The method we use to update our logtable, it calls the utilControl.getLogEntryList and sets it with
	  * a selectedCustomer from state. After that it updates the logtable with the newly updated information.
	  * in the end- it sets the last log as selected.
	  */
	public void dynamicallyUpdateLogModel(UpdateLogTable selection)
	{
		//this method is made because we're allso able to try and update the log table now, w/o a customer 
		//selected, due to the fact that when we choose a new directory, we need to update the logTable,
		//in order to add the newFileReference to the files.
		if(state.selectedCustomer == null)
		{
			return;
		}
		
		state.selectedCustomer.setLogEntryList(utilControl.getLogEntryList(state.selectedCustomer.getId()));
		mediator.getLogEntryTable().updateLogTable(state.selectedCustomer.getLogEntryList());
		if(selection == UpdateLogTable.EDIT)
		{
			final int inter = state.selectedLogRow;
			setRowSelectionInterval(inter,inter);
		}
		//for readability we make another else if statement
		else if(selection == UpdateLogTable.NEW) 
		{
			setRowSelectionInterval(getModel().getRowCount()-1,getModel().getRowCount()-1);
		}
		else if(selection == UpdateLogTable.NOTHING)
		{
			clearSelection();
		}
	}
	
	/**
	 * method responsible for opening a file, it just takes the selectedLogEntry and passes on the fileReference
	 * to another method in utilControl.
	 */
	public void openFileInNativeApp()
	{
		try
		{
			utilControl.openFileInNativeApp(state.selectedLogEntry.getAttachedFile());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("could not open the file, because  the file doesn't exsist");
		}
	}
	/**
	 *method that returns this specific tablemodel
	 */
	public DefaultTableModel getLogTableModel()
	{
		return logModel;
	}
}


