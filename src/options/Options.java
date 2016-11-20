package options;

import java.io.Serializable;

public class Options implements Serializable
{
	private static final long serialVersionUID = 7655887814415405672L;

	private String collectiveFilePath;

	/**
	 * @comment public constructor 
	 */
	public Options()
	{
	}
	
	//getters / setters
	public String getCollectiveFilePath() {
		return collectiveFilePath;
	}

	public void setCollectiveFilePath(String collectiveFilePath) {
		this.collectiveFilePath = collectiveFilePath;
	}
	
	
}
