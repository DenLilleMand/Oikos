package wrapper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.ArrayList;

import entity.Customer;
import entity.LogEntry;
import entity.User;
import entity.UserType;
import tables.State;

/**
 * DBWrapper.java
 * Singleton pattern
 * Handles interactions between Java-application and MySQL
 * Only has private constructors, hence final
 * @author Jon, Mads, Matti, Peter 
 */
public final class DBWrapper implements IDBWrapper 
{
	/** The only instance of DBWrapper we allow to exist */
	private static DBWrapper instance = new DBWrapper();

	/** A boolean value used to determine whether using localhost or foreign host connection */
	private static boolean useOnlineDb = true; 
	// Static JDBC/MySQL objects used for DB-connectivity 
	
	/** DBWrappers only connection object */
	private static Connection con;
	/** DBWrappers only CallableStatement */
	private static CallableStatement cs;
	/** DBWrappers only ResultSet */
	private static ResultSet rs;
	/** State class from tables */
	private State state = State.getInstance();
	
	/**  final String used to avoid making so many String objects*/
	private final String ZERO = "";

	// constant strings for connecting to databases
	//  ideally should be read from a file, not hardcoded - in order to protect passwords 	
	
	/** Login string to use on online database AFTER logging the user in */
	private static final String ONLINEDATABASEANDPASSWORD = "jdbc:mysql://<server>/2nd_semesters?user=matti&password=vn4Nh4XAGXvFsZ";
	/** Login string to use if logging in to online DB - connects with loginuser NOT matti */
	private static final String ONLINEINITIALLOGIN = "jdbc:mysql://<server>/2nd_semesters?user=loginuser&password=pass&noAccessToProcedureBodies=true";

	/** Login string to use if connecting to localhost DB */
	private static final String LOCALHOSTDATABASEANDPASSWORD = "jdbc:mysql://localhost/2nd_semesters?user=matti&password=vn4Nh4XAGXvFsZ&noAccessToProcedureBodies=true";
	/** Login string to use if logging in to localhost DB - connects with loginuser NOT matti */ 
	private static final String LOCALHOSTINITIALLOGIN = "jdbc:mysql://localhost/2nd_semesters?user=loginuser&password=pass&noAccessToProcedureBodies=true";


	/**
	 * Private constructor because DBWrapper is singleton
	 *Checks if instance exists and throws an exception it it does (since we only want to construct 1 DBWrapper) 
	 */
	private DBWrapper(){
		if (instance != null){
			throw new IllegalStateException("Cannot make new instance of DBWrapper");
		}
	}


	/**
	 * return the instance of DBWrapper - creates it if it doesn't exist yet
	 * @return DBWrapper instance
	 */
	public static synchronized DBWrapper getInstance(){
		if (instance == null){
			instance = new DBWrapper();
		}
		return instance;
	}


	/**
	 * Search method for Customer objects. 
	 * Calls a Stored Procedure and puts result in an ArrayList of Customer objects to be returned
	 * @param customer Takes in Customer and not customerId to underline what kind of info the method is working with
	 * @return Customer
	 */
	public void additionalCustomerInfo(Customer customer)
	{
		try
		{
			cs = con.prepareCall("CALL `2nd_semesters`.`retrieveAdditionalCustomerInfo`(?)");
			cs.setInt(1, customer.getId());
			rs = cs.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData(); 	
			while (rs.next())
			{
				int count = rsmd.getColumnCount();
				for(int i = 1; i <= count; i++)
				{
					String col = rsmd.getColumnName(i);

					//col changes by for-loop
					switch(col)
					{

					//AccountNumber
					case "AccountNumber": if(rs.getInt(i)==0)
						customer.setAccountNumber(ZERO);
					else
						customer.setAccountNumber(rs.getString(i));
					break;

					//RegistrationNumber	
					case "RegistrationNumber":
						if(rs.getInt(i)==0)
							customer.setRegistrationNumber(ZERO);
						else
							customer.setRegistrationNumber(rs.getString(i));
						break;

						//AccountType
					case "AccountType":
						if(rs.getString(i) == null)
							customer.setAccountType(ZERO);
						else
							customer.setAccountType(rs.getString(i));
						break;

						//StreetName
					case "StreetName":
						if(rs.getString(i)==null)
							customer.setStreetName(ZERO);
						else
							customer.setStreetName(rs.getString(i));
						break;


						//StreetNumber
					case "StreetNumber":
						if(rs.getString(i)==null)
							customer.setStreetNumber(ZERO);
						else
							customer.setStreetNumber(rs.getString(i));
						break;


						//Floor
					case "Floor":
						if(rs.getString(i)==null)
							customer.setFloor(ZERO);
						else
							customer.setFloor(rs.getString(i));
						break;

						//ApartmentNumberOrSide
					case "ApartmentNumberOrSide":
						if(rs.getString(i)==null)
							customer.setApartmentNumberOrSide(ZERO);
						else
							customer.setApartmentNumberOrSide(rs.getString(i));
						break;

						//CO
					case "CO":
						if(rs.getString(i)==null)
							customer.setCo(ZERO);
						else
							customer.setCo(rs.getString(i));
						break;

						//PostalArea
					case "PostalArea":
						if(rs.getString(i)==null)
							customer.setPostalArea(ZERO);
						else
							customer.setPostalArea(rs.getString(i));
						break;

						//postalCode
					case "PostalCode":
						if(rs.getString(i)==null)
							customer.setZipcode(ZERO);
						else
							customer.setZipcode(rs.getString(i));
						break;

						//City
					case "City":		
						if(rs.getString(i)==null)
							customer.setCity(ZERO);
						else
							customer.setCity(rs.getString(i));
						break;

						//Country
					case "Country":
						if(rs.getString(i)==null)
							customer.setCountry(ZERO);
						else
							customer.setCountry(rs.getString(i));
						break;

						//CVR
					case "CVR":
						if(rs.getInt(i)==0)
							customer.setCVR(ZERO);
						else
							customer.setCVR(rs.getString(i));
						break;

						//CompanyName
					case "CompanyName":
						if(rs.getString(i)==null)
							customer.setCompanyName(ZERO);
						else
							customer.setCompanyName(rs.getString(i));
						break;

						//Email
					case "Email":
						if(rs.getString(i)==null)
							customer.setEmail(ZERO);
						else
							customer.setEmail(rs.getString(i));
						break;

						//Phone
					case "Phone":
						if(rs.getInt(i)==0)
							customer.setPhone(ZERO);
						else
							customer.setPhone(rs.getString(i));
						break;

						//MobilePhone
					case "MobilePhone":
						if(rs.getInt(i)==0)
							customer.setMobilePhone(ZERO);
						else 
							customer.setMobilePhone(rs.getString(i));
						break;												
					}
				}
			}
		}

		catch (SQLException ex){
			ex.printStackTrace();
			System.out.println("You got an SQL exception in searchCustomerForExcel(String search)");
		}
	}
	/**
	 * Takes in a search string, and using the StoredProcedure finds all customers with Name, customerId or Cpr
	 * That match or partially match this search string. Is only called on initial search, the rest of the sorting
	 * is handled in utilControl.search(), As you might notice, this method only brings up about 
	 * 5 variables, thats because we only need the additional customer information if we're going to make an
	 * excel sheet or pdf document, so we avoid bringing up too much information until the point-of-no-return
	 * in either of those directions.
	 * @param search String containing search parameters
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> searchCustomer(String search){
		ArrayList <Customer> returnList = new ArrayList<Customer>();
		try{
			cs = con.prepareCall("CALL `2nd_semesters`.`searchCustomer`(?)");
			cs.setString(1, search);
			rs = cs.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData(); 

			while (rs.next()){
				Customer customer = Customer.newInstance();
				int count = rsmd.getColumnCount();

				for(int i = 1; i <= count; i++)
				{
					String col = rsmd.getColumnName(i);

					switch(col){

					case "PK_CustomerId":
						customer.setId(rs.getInt(i));
						break;

						//firstName
					case "FirstName":
						if(rs.getString(i)==null)
							customer.setFirstName(ZERO);
						else
							customer.setFirstName(rs.getString(i));
						break;


						//lastName
					case "LastName":
						if(rs.getString(i) == null)
							customer.setLastName(ZERO);
						else
							customer.setLastName(rs.getString(i));
						break;


						//CPR
					case "CPR":
						if(rs.getInt(i) == 0)
							customer.setCpr(ZERO);
						else
							customer.setCpr(rs.getString(i));
						break;
					}
				}	
				returnList.add(customer);
			}	
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Something went wrong in DBWrapper.searchCustomer()");
		}
		return returnList;
	}

	
	/**
	 * Method to get LogEntries for a selected Customer
	 * @param customerId Id for the customer who has the logentries
	 * @return ArrayList<LogEntry> 
	 */
	public ArrayList<LogEntry> getLogEntryList(int customerId){
		ArrayList <LogEntry> returnList = new ArrayList<LogEntry>();
		try{
			cs = con.prepareCall("Call `2nd_semesters`.`getLogEntryList`(?)");
			cs.setInt(1, customerId);
			rs = cs.executeQuery();

			//we don't work with metadata here, since two columns in db have the same names: 'name',
			//this is because there are two joins into the same table. Could be done more flexible
			//but other priorities at current stage of development

			//So: important not to change order of the following
			while (rs.next()){
				LogEntry log = new LogEntry();

				//LogEntryId -- Cannot be null in db, so no null check
				log.setId(rs.getInt(1));

				//CreatedDate -- similar
				log.setCreateDate(new java.util.Date(rs.getTimestamp(2).getTime()));

				//CreatedBy -- similar
				log.setCreatedBy(rs.getString(3));

				//LastEdidDate -- similar
				log.setLastEditDate(new java.util.Date(rs.getTimestamp(4).getTime()));

				//LastEditBy -- similar
				log.setLastEditBy(rs.getString(5));

				//Description -- can be null, therefore null check
				if(rs.getString(6) != null)
					log.setDescription(rs.getString(6));
				else
					log.setDescription("");

				//Comment
				if(rs.getString(7) != null)
					log.setComment(rs.getString(7));
				else
					log.setComment("");	

				//File reference
				if(rs.getString(8) == null)
					log.setAttachedFile(new File(""));
				else
					log.setAttachedFile(new File(rs.getString(8)));

				returnList.add(log);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			System.out.println("SQL exception in getLogEntryList(int customerId) caught");
		}

		return returnList;
	}

	/**
	 * Method for saving a given logEntry to the DB using storedprocedure "newLogEntry"
	 * which makes a INSERT with the given variables as parameters for the new row
	 * returns the logEntryId for the new log if successful - 0 if not 
	 * @param inputLogEntry The logentry to save in database
	 * @return int 
	 */
	public int newLogEntry(LogEntry inputLogEntry){
		try{	
			cs = con.prepareCall("CALL `2nd_semesters`.`newLogEntry`(?,?,?,?,?,?,?,?)");

			cs.setInt(1, inputLogEntry.getCustomerId());
			cs.setTimestamp(2, new java.sql.Timestamp(inputLogEntry.getCreateDate().getTime()));
			cs.setString(3, inputLogEntry.getComment());
			cs.setInt(4, state.getCurrentUser().getId());//the current User's Id acts as FK in db
			cs.setTimestamp(5, new java.sql.Timestamp(inputLogEntry.getLastEditDate().getTime()));
			cs.setInt(6, state.getCurrentUser().getId());//the current User's Id acts as FK in db
			cs.setString(7,inputLogEntry.getDescription());

			rs = cs.executeQuery();

			//places the cursor at first row
			rs.next();
			return rs.getInt(1);
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("A SQL error has occured in DBWrapper.newLogEntry(), better check it out");
			return 0;
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	/**
	 * Method for changing the contents of a given logEntry in the Database using the storedprocedure
	 * "editLogEntry" which calls a UPDATE on the rows where he logId is the same.
	 * @param inputLog The edited logentry to be saved in the database
	 */
	public void editLogEntry(LogEntry inputLog){
		try{
			cs = con.prepareCall("CALL `2nd_semesters`.`editLogEntry`(?,?,?,?,?)");
			cs.setInt(1, inputLog.getId());
			cs.setString(2, inputLog.getComment());
			cs.setTimestamp(3, new java.sql.Timestamp(inputLog.getLastEditDate().getTime()));
			cs.setInt(4, state.getCurrentUser().getId());//the current User's Id acts as FK in db. This is the last Edited By
			cs.setString(5, inputLog.getDescription());
			try{
				cs.execute();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception caught");
		}
	}

	/**
	 * Method for deleting a logEntry from the DB using the storedprocedure "delteLogEntry"
	 * which deletes the LogEntry (Should probably also delete all rows affiliated with this logEntry)
	 * @param inputLog The logentry to be deleted
	 */
	public void deleteLogEntry(LogEntry inputLog){
		try{
			cs = con.prepareCall("CALL `2nd_semesters`.`deleteLogEntry`(?)");
			cs.setInt(1, inputLog.getId());
			cs.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Exception caught");
		}
	}

	/**
	 * Method for saving a given fileReference to the Database with a logEntryId as ForeignKey
	 * 
	 * @param fileReference The full path to the file
	 * @param logEntryId The id of the logentry
	 */
	public void saveFileToDB(String fileReference, int logEntryId)
	{
		try
		{
			cs = con.prepareCall("CALL `2nd_semesters`.`saveFileReference`(?,?)");
			cs.setString(1, fileReference);
			cs.setInt(2, logEntryId);
			cs.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception caught");
		}
	}

	/**
	 * Method for editing a given fileReference in the DB with a logEntryId as ForeignKey
	 * @param fileReference The full path to the file
	 * @param logEntryId The id of the logentry to edit
	 */
	public void editFileReference(String fileReference, int logEntryId){
		try{
			cs = con.prepareCall("CALL `2nd_semesters`.`editFileReference`(?,?)");
			cs.setString(1, fileReference);
			cs.setInt(2, logEntryId);
			cs.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
			System.out.println(e.getMessage());

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}



	/**
	 * Method for deleting a Filereference in the DB. Does not interfere with the logEntry itself.
	 * @param logEntryId Id of the logentry to delete filereference for
	 */
	public void deleteFileReference(int logEntryId){
		try{
			cs = con.prepareCall("CALL `2nd_semesters`.`deleteFileReference`(?)");
			cs.setInt(1, logEntryId);
			cs.execute();
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	/** 
	 * Takes input from LoginGui and tries to login as 'loginuser' in database by calling the other attemptLogin method
	 * loginuser only has SELECT on User, Insert on login_attempt, successful_login and execute privileges for the login-stored procedure
	 * Creates a user object, set as currentUser in State 
	 *  
	 * A boolean value is returned
	 * @param attemptedUsername the username entered
	 * @param attemptedPassword the password entered
	 */
	public boolean attemptLogin(String attemptedUsername, String attemptedPassword){
		try{
			if (useOnlineDb){
				try{
					con = DriverManager.getConnection(ONLINEINITIALLOGIN);
				}
				catch (SQLException e){
					System.out.println("no loginuser - trying to connect as matti from catch block");
					connectToDB();
				}
			}
			else {
				con = DriverManager.getConnection(LOCALHOSTINITIALLOGIN);
			}
			
			if (useOnlineDb){
				try{
					con = DriverManager.getConnection(ONLINEINITIALLOGIN);
				}
				catch (Exception e){
					System.out.println("no loginuser - trying to connect as matti from catch block");
					connectToDB();
				}
			}
			else {
				con = DriverManager.getConnection(LOCALHOSTINITIALLOGIN);		
			}	
			cs = con.prepareCall("CALL `2nd_semesters`.`attemptLogin`(?, ?)");
			cs.setString(1, attemptedUsername);
			cs.setString(2, attemptedPassword);
			
			rs = cs.executeQuery();
			if (rs.isBeforeFirst())
			{
				if (rs.next())
				{
					int uId = rs.getInt("PK_UserId");
					String newUsername = rs.getString("username");
					String realName = rs.getString("Name");
					int userType = rs.getInt("UserType");
					// Convert the int of userType to our enum
					UserType ut;
					if (userType == 1)
					{
						ut = UserType.ADMINISTRATOR;
					}
					else 
					{
						ut = UserType.EMPLOYEE;
					}

					state.setCurrentUser(new User(uId, newUsername, realName, ut));
					System.out.println("Created user!");
					System.out.println("Logged " + newUsername + " in!");
				}

				// Reconnect to DB to login as user matti instead of loginuser

				connectToDB();
				System.out.println("Reconnected");

				return true;
			}
			else{
				System.out.println("Didn't login!");
				return false;
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL exception thrown in attemptlogin: " + e.getMessage());
		}
		catch (NullPointerException e)
		{
			System.out.println("nullpointer in user creation: " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Connects to whichever DB user wants to connect to
	 */
	private void connectToDB()
	{
		closeConnection();
		try
		{
			con = DriverManager.getConnection(whichDB());
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Couldn't create connection!");
		}
	}

	/**
	 * Checks if con is null. If not, attempts to close the connection
	 * Is called by connectToDb to ensure the connection is closed before connecting.
	 */
	private void closeConnection()
	{
		try
		{
			if (con != null)
			{
				con.close();
				System.out.println("Con closed!");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Couldn't close connection!");
		}
	}


	
	/**
	 * Calls stored procedure createUser
	 * @param username The wanted username
	 * @param password The wanted password
	 * @param salt The salt obtained from UserControl - 24 bits
	 * @param realName The real name of the user
	 * @param userType The usertype of the user
	 */
	public void callCreateUser(String username, String password, String salt, String realName, int userType)
	{
		try
		{
			cs = con.prepareCall("CALL `2nd_semesters`.`createUser`(?, ?, ?, ?, ?)");
		
			cs.setString(1, username);
			cs.setString(2, password);
			cs.setString(3, salt);
			cs.setString(4, realName);
			cs.setInt(5, userType);
			try{
				cs.executeQuery();
			}
			catch (SQLException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			catch (NullPointerException e){
				System.out.println("NullPointer Caught: " + e.getMessage());
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			System.out.println("Exception caught");
		}
	}

	/**
	 *  Returns the login-string to the database being used 
	 * @return String 
	 */
	private String whichDB(){
		if (useOnlineDb){
			return ONLINEDATABASEANDPASSWORD;
		}
		else{
			return LOCALHOSTDATABASEANDPASSWORD;
		}
	}

	/**
	 * 
	 * methods to control what db to use
	 * made this way to ensure an explicit call to useLocalHostDB()/useOnlineDB() is made before changing the variable
	 * This way it doesn't get set to the wrong state by mistake
	 */

	/**
	 * sets useOnlineDb = true
	 */
	public void useOnlineDb(){
		useOnlineDb = true;
	}
	/**
	 * sets useOnlineDb = false
	 */
	public void useLocalhostDb(){
		useOnlineDb = false;
	}

	/**
	 * Returns useOnlineDb
	 * @return boolean
	 */
	public boolean getUseOnlineDb(){
		return useOnlineDb;
	}
}
