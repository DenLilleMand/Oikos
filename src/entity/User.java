package entity;

/**
 * User class
 * Resembles a User from our database 
 * Fix this class.
 * @author Mads
 *
 */
public final class User implements Entity{

	private int id;
	private String username;
	private String name;
	private UserType userType;

	/**
	 * Public constructor for User
	 * @param userId userid - from Database
	 * @param username The username of the user
	 * @param name The real name of the user
	 * @param userType The usertype of the user
	 */
	public User(int id, String username, String name, UserType userType){
		this.id = id;
		this.username = username;
		this.name = name;
		this.userType = userType;
	}


	public String getUserName(){
		return username;
	}

	public String getName(){
		return name;
	}

	public UserType getUserType(){
		return userType;
	}
	
	public void setUsername(String username){
		this.username = name;
	}
	
	public void setRealName(String name){
		this.name = name;
	}
	
	public void setUserType(UserType userType){
		this.userType = userType;
	}
	
	public String toString(){
		return "Username: " + username + "\nReal name: " + name + "\nUserType: " + userType;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
}
