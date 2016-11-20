package run;

import java.io.File;


public class Test 
{
	
	public static void main(String [] args)
	{
		File file = new File("");
		System.out.println(see(file));
		
	}
	
	
	public static boolean see(File file)
	{
		if(file.toString().equalsIgnoreCase(""))
		{
			return true;
		}
		return false;
	}
}
