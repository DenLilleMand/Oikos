//Jon 12th august
package entity;

import java.io.File;

public class AttachedFile extends File{

	private static final long serialVersionUID = -8733418141835671328L;
	
	//extension as instance variable for quick access
	private String fileExtension;
	
	//constructor passing in a String representation of a File to super, thus saving the path in the object
	public AttachedFile(File file){
		super(file.toString());
	}
	
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
}
