package entity;

import java.util.ArrayList;
import static constants.StringConstant.ZERO;


/**
 * Customer.java
 * The class for the creating Customer objects.
 * These getters and setters should have our eyes bleed, 
 * same notes as LogEntry goes for this class
 * @author Jon, Mads, Peter, Matti
 */
public final class Customer implements Entity
{

//All attributes which will be written to excel are in Strings in order to reduce checks
//and create "clean" Customer-objects in the DBWrapper class
//////////////////////////////////////////Customer ///////////////////////////////
	private int id;
	private String firstName;
	private String lastName;
	
	private String cpr;
//////////////////////////////////////////Account ///////////////////////////////
	private String accountNumber;
	private String registrationNumber;
	private String accountType;
//////////////////////////////////////////Adddress ///////////////////////////////
	private String streetName;
	private String streetNumber;
	private String floor;
	private String apartmentNumberOrSide;
	private String co;
	private String postalArea;
	private String city;
	private String country;
	private String zipcode;
	
//////////////////////////////////////////Phone///////////////////////////////
	private String phone;
	private String mobilePhone;
//////////////////////////////////////////Company///////////////////////////////
	private String cvr;
	private String companyName;
//////////////////////////////////////////email///////////////////////////////
	private String email;
//////////////////////////////////////////log////////////////////////////////////
	private ArrayList<LogEntry> logEntryList = new ArrayList<LogEntry>();
	
////////////////////////////////////////for Excel ////////////////////////////////
	private String dankort = ZERO;
	
	private Customer()
	{
		
	}
	
	private Customer(Builder builder)
	{
		id  		 = builder.id;
		firstName 	 = builder.firstName;
		lastName   	 = builder.lastName;
		cpr 		 = builder.cpr;
		dankort 	 = builder.dankort;
	}

	public static Customer newInstance()
	{
		return new Customer();
	}
	
	public static Customer makeClone(Customer customer)
	{
		return new Customer.Builder().id(customer.getId()).
				firstName(customer.getFirstName()).lastName(customer.getLastName()).
				dankort(customer.getDankort()).cpr(customer.getCpr()).build();
	}
	
	
	public static class Builder
	{
				//Optional parameters - initialized 
				private int id = 0;
				private String firstName = ZERO;
				private String lastName = ZERO;
				private String cpr = ZERO;
				private String dankort = ZERO;
				
				public Builder()
				{
				}
				
				public Builder id(int id)
				{	this.id= id; return this; }
				
				public Builder firstName(String firstName)
				{ this.firstName = firstName; return this;	}
				
				public Builder lastName(String lastName)
				{	this.lastName = lastName; return this; }
				
				public Builder cpr(String cpr)
				{ this.cpr = cpr; return this;}
				
				public Builder dankort(String dankort)
				{ this.dankort = dankort;  return this; }
				
				public Customer build()
				{ return new Customer(this); }
		
		
	}

	

	//set methods
	public void setDankort(String dankort) {
		this.dankort = dankort;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	public void setApartmentNumberOrSide(String apartmentNumberOrSide) {
		this.apartmentNumberOrSide = apartmentNumberOrSide;
	}
	
	public void setCo(String co) {
		this.co = co;
	}
	
	public void setPostalArea(String postalArea) {
		this.postalArea = postalArea;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCVR(String cvr) {
		this.cvr = cvr;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public void setLogEntryList(ArrayList<LogEntry> inputList){
		this.logEntryList = inputList;
	}
	/*
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	*/
	public void addLogEntryToList(LogEntry inputLog){
		this.logEntryList.add(inputLog);
	}
	
	//Get methods
	public String getDankort() {
		return dankort;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getFloor() {
		return floor;
	}

	public String getApartmentNumberOrSide() {
		return apartmentNumberOrSide;
	}

	public String getCo() {
		return co;
	}

	public String getPostalArea() {
		return postalArea;
	}
	
	public ArrayList<LogEntry> getLogEntryList(){
		return logEntryList;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getCvr() {
		return cvr;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCpr() {
		return cpr;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getZipcode() {
		return zipcode;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id=id;
		
	}

}
