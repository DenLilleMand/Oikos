package options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.IOUtils;

import tables.State;

public class OptionsLoader {
	private static final String optionsFileName = "options.ser";
	private static final String appName = "Olympus";
	private static final String excelFile = "excelark.xlsx";
	private static final String pdfFile = "NetbankenMedForms.pdf";
	private static final String ZERO = "";
	private static final String USER_DIR_PLUS_APPNAME = ZERO+ FileUtils.getUserDirectory() + File.separator + appName;
	private static final String userGuide = "OlympusBrugerVejledning.pdf";
	private static State state;

	private static void checkForUserDirectoryAndFiles() throws Exception 
	{
		System.out.println("User dir:" + FileUtils.getUserDirectory());
		File file = new File(USER_DIR_PLUS_APPNAME);
		if (!file.exists()) 
		{
			file = new File(file.getAbsolutePath() + File.separator);
			System.out.println("The directory we're making:" + file.toString());
			FileUtils.forceMkdir(file);
			
			
			file = new File(USER_DIR_PLUS_APPNAME + File.separator+ optionsFileName);
			System.out.println("The file we're going on to make:" + file.toString());
			file.createNewFile();
			copyStreamToFile(optionsFileName, file);

			file = new File(USER_DIR_PLUS_APPNAME + File.separator + excelFile);
			file.createNewFile();
			copyStreamToFile(excelFile, file);

			file = new File(USER_DIR_PLUS_APPNAME + File.separator + pdfFile);
			file.createNewFile();
			copyStreamToFile(pdfFile, file);


			file = new File(USER_DIR_PLUS_APPNAME + File.separator + userGuide);
			file.createNewFile();
			copyStreamToFile(userGuide, file);
		}
	}

	private static void copyStreamToFile(String filePath, File file) {
		InputStream inputStream = null;
		OutputStream output = null;
		try 
		{
			inputStream = OptionsLoader.class.getClassLoader().getResourceAsStream("ressources/"+ file.getName());
			System.out.println("this is the file name we're going for:  "+ "ressources"+File.separator + file.getName());
			output = new FileOutputStream(file);
			System.out.println("our inputStream:" + inputStream);
			IOUtils.copy(inputStream, output);
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void save(Options options) throws Exception {
		checkForUserDirectoryAndFiles();
		state = State.getInstance();
		ObjectOutputStream out = null;
		FileOutputStream fileOut = null;
		if (options == null) {
			options = new Options();
		}
		options.setCollectiveFilePath(state.getFilePath());
		try {
			fileOut = new FileOutputStream(USER_DIR_PLUS_APPNAME
					+ File.separator + optionsFileName, false);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(options);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Options load() throws Exception {

		Options options = null;
		FileInputStream fileIn = null;
		ObjectInputStream in = null;

		checkForUserDirectoryAndFiles();
		try {
			fileIn = new FileInputStream(USER_DIR_PLUS_APPNAME + File.separator
					+ optionsFileName);
			in = new ObjectInputStream(fileIn);
			options = (Options) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			fileIn.close();
		}
		return options;
	}
}
