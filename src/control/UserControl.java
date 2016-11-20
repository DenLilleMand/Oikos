package control;

import java.security.SecureRandom;

import wrapper.DBWrapper;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * Singleton class for creating new users
 * For handling tasks related to user administration acting
 * as the link between GUI-layer and DBWrapper.
 * 
 * Holds a SecureRandom object we don't want to instantiate unless necessary
 * 
 * @author Mads
 */
public final class UserControl{
	/** The only instance of UserControl we allow to exist */
	private static UserControl instance;
	/** used for generating random salts */
	private SecureRandom random;


	/** private constructor - only to be accessed from within this class */
	private UserControl(){
		if(instance!=null){
			throw new IllegalStateException("Cannot make new instance of singleton object");
		}
	}


	/** 
	 * Returns the only instance of UserControl we allow to exist - creates it if it doesn't exist
	 * @return UserControl
	 */
	public static synchronized UserControl getInstance(){
		if(instance==null){
			instance = new UserControl();
		}
		return instance;
	}

	/**
	 * Creates a user for storing in database
	 * 
	 * @param username The username the new user will get 
	 * @param password The password the new user will get
	 * @param realName The real name of the new user
	 * @param userType The userType of the new user
	 */
	public void createUser(final String username, final String password, final String realName, final int userType){
		String salt = getSalt();
		DBWrapper.getInstance().callCreateUser(username, password, salt, realName, userType);
	}

	/**
	 * Only initialize a SecureRandom object if it doesn't exist.
	 * Private method, called upon user creation
	 * 
	 * Generates a 32 characters long (24 bytes) cryptographically secure salt 
	 * returns it as a Base64 encoded String to ensure alphanumeric characters instead of bytes
	 */
	private String getSalt(){
		try{
			if (random == null){
				random = new SecureRandom();
			}
			
			byte salt[] = new byte[24];

			random.nextBytes(salt);
			// Encode the 24 bytes with base 64
			// Ensures we have a salt of alphanumeric characters
			//Base64 is to make sure that the database can handle whatever input, in collision with ASCII.
			return Base64.encode(salt);
		}
		catch (NullPointerException e){
			System.err.println("[ Couldn't generate salt ]\n[ Probably means DBWrapper isn't instantiated ]\nError: " + e.getMessage());
			e.printStackTrace();
			return "";
		}
		catch (Exception e){
			System.out.println("[ Couldn't generate salt ]\nError: " + e.getMessage());
			return "";
		}
	}
}

