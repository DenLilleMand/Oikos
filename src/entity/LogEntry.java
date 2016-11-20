
package entity;

import java.io.File;
import java.util.Date;

/**
 * 
 * **OBS! Har vi ikke glemt at kunne s�ge p� CVR? ligesom vi kan s�ge p� cpr?
 * 
 * 
 * LogEntry class - resembling a LogEntry in our database,
 * this should truly be a Builder pattern
 * with this many parameters, get rid of all the getters
 * and setters, find a way to make the fields private and final
 * if possible, if a getter is needed, make sure it's a clone()
 * and remember that it's a clone when writeing the client code.
 * 
 * Jeg tror desuden at vores Client kode kender alt alt for meget
 * til Logentries interne implementation, alle klasser importere jo
 * n�rmest det her, det eneste de burde kende til, ville v�re
 * vores interface Entity(eller hvad BURDE v�re et interface),
 * og s� hvis de er s�de s� kan de f� en kopi af det logentry
 * de har brug for, imens de rigtige Logentrys kun burde ligge 1 sted,
 * og det burde v�re i den kode der beskytter vores LogEntry klasse,
 * der kunne der ligge en private liste, og wupti fucking doo hvis
 * folk vil have noget s� kan de f� en Iterator og have en generic metode,
 * til at kunne g� dem igennem, eller bedre - vi s�ger det igennem for dem,
 * og s� kan de f� en kopi af det ene logEntry de leder efter. 
 * 
 * 
 * @author Jon, Matti, Peter, Mads
 */
public final class LogEntry implements Entity
{	
	private int id;
	private String comment;
	private int customerId;

	private String description;
	
	private AttachedFile attachedFile;
	
	private String createdBy;//retrieved from db by join, stored as FK _UserId in db
	private String lastEditBy;//retrieved from db by join, stored as FK _UserId in db
	private Date lastEditDate;
	private Date createDate;
	
	private String checked;

	public LogEntry()
	{
		
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	
	//Public constructor
	//Peter: Unsure if LogId shouldn't be assigned in the DB instead.
	public LogEntry(String inComment, int logId, Date date, int inCustomerId, File file){
		setComment(inComment);
		setId(logId);
		setCreateDate(date);
		setCustomerId(customerId);
		setAttachedFile(file);
	}
	
	public void setComment(String inComment){
		this.comment = inComment;
	}
	
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLastEditBy(String lastEditBy) {
		this.lastEditBy = lastEditBy;
	}
	

	public void setChecked(String checked) {
		this.checked = checked;
	}

	//Get methods for the attributes
	
	public String getComment(){
		return comment;
	}

	public int getCustomerId(){
		return customerId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getDescription() {
		return description;
	}

	public String getLastEditBy() {
		return lastEditBy;
	}


	public String getChecked() {
		return checked;
	}

	@Override
	public int getId() 
	{
		return id;
	}

	@Override
	public void setId(int id) 
	{
		this.id=id;	
	}

	public AttachedFile getAttachedFile() 
	{
		return attachedFile;
	}
	
	//set-method using the super constructor in AttachedFile, so its still the object File in the process, untill its being set with this method
	public void setAttachedFile(File file) 
	{
		this.attachedFile = new AttachedFile(file);
	}
}
