
package gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Pictures.java
 * responsible for all pictures non-java standard.
 * @author  Jon, Peter, Matti
 */
public abstract class Ressources
{	
	/**Static variables are run before the constructor
	 * final static variables, since they are constants
	 */
	protected final static ImageIcon PDF;
	protected final static ImageIcon DOC;
	protected final static ImageIcon XLSX;

	/**GENERAL is for future support of various file types
	 * not implemented at current stage of development 
	 */
	protected final static ImageIcon GENERAL;
	
	//our current background picture
	protected final static ImageIcon OIKOSLOGO;
	protected final static ImageIcon OIKOSCOLORS;
	
	
	//arrows
	protected final static ImageIcon RIGHTARROW;
	protected final static ImageIcon LEFTARROW;
	
	//mutiple selections
	protected static ImageIcon checkedIcon;
	protected static ImageIcon uncheckedIcon;
	
	//font
	protected static Font font;
	protected static Font fontForTables;
	
	//for dankort 2
	protected final static ImageIcon DANKORT;

	protected static int reloadPictures = 1;
	
	protected static ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
	
	/**The static block initializes the static variables of the class
	 * but without needing to run the constructor. The block is loaded when the class is accessed
	 * for the first time, but only then.
	 */
	static
	{
		//font
		try 
		{
			loadFont();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			font = new Font("Serif",16, 16); //setting this font if anything goes wrong loading the other font.
											 //has to be checked through - so they have the exact same font size.
			fontForTables =  new Font("Serif",16, 16);
		} 	
		
		try 
		{
			uncheckedIcon = new ImageIcon(Ressources.class.getResource("/ressources/unchecked.png"));
			checkedIcon = new ImageIcon(Ressources.class.getResource("/ressources/checked.png"));
			PDF = new ImageIcon(Ressources.class.getResource("/ressources/pdf.png"));
			DOC = new ImageIcon(Ressources.class.getResource("/ressources/doc.png"));
			XLSX = new ImageIcon(Ressources.class.getResource("/ressources/xlsx.png"));
			GENERAL = new ImageIcon(Ressources.class.getResource("/ressources/dokument.png"));
			RIGHTARROW = new ImageIcon(Ressources.class.getResource("/ressources/rightarrow.png"));
			LEFTARROW = new ImageIcon(Ressources.class.getResource("/ressources/leftarrow.png"));
			DANKORT = new ImageIcon(Ressources.class.getResource("/ressources/dankort.png"));
			OIKOSLOGO = new ImageIcon(Ressources.class.getResource("/ressources/backgroundLogo.png"));
			OIKOSCOLORS = new ImageIcon(Ressources.class.getResource("/ressources/backgroundColor.png"));
		
			icons.add(OIKOSLOGO);
			icons.add(OIKOSCOLORS);
		} 
		catch (Exception e) 
		{
			//To deal with Eclipse saying that static variables may not be instantiated.
			throw new RuntimeException(e);
		} 
	}
	
	//method to retrieve path independently of platform
	//this method takes in variable parameters, 
//	private static ImageIcon getIconFilePath(String...args) throws IOException{
//		StringBuilder sb = new StringBuilder(System.getProperty("user.dir"));
//		for (String s: args){
//			sb.append(FS).append(s);
//		}
//		return new ImageIcon(sb.toString());
//	}
	
	private static void loadFont() throws Exception
	{
		InputStream is = Ressources.class.getClassLoader().getResourceAsStream("ressources/archer.otf");
		font= Font.createFont(Font.TRUETYPE_FONT,is);
		font = font.deriveFont(Font.PLAIN,16);
		fontForTables = font.deriveFont(Font.PLAIN,16);
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		ge.registerFont(fontForTables);
	}

	public static ImageIcon getCheckedIcon() {
		return checkedIcon;
	}

	public static void setCheckedIcon(final ImageIcon checkedIcon) {
		Ressources.checkedIcon = checkedIcon;
	}

	public static ImageIcon getUncheckedIcon() {
		return uncheckedIcon;
	}

	public static void setUncheckedIcon(final ImageIcon uncheckedIcon) {
		Ressources.uncheckedIcon = uncheckedIcon;
	}

	/**
	 * for shifting the background icon
	 * @param newNumber
	 */
	public static void setPictureNumber(final int newNumber){
		reloadPictures = newNumber;
	}

	public static ImageIcon getPdf() {
		return PDF;
	}

	public static ImageIcon getDoc() {
		return DOC;
	}

	public static ImageIcon getXlsx() {
		return XLSX;
	}

	public static ImageIcon getGeneral() {
		return GENERAL;
	}

	public static ImageIcon getOikoslogo() {
		return OIKOSLOGO;
	}

	public static ImageIcon getOikoscolors() {
		return OIKOSCOLORS;
	}

	public static ImageIcon getRightarrow() {
		return RIGHTARROW;
	}

	public static ImageIcon getLeftarrow() {
		return LEFTARROW;
	}

	public static Font getFont() {
		return font;
	}

	public static Font getFontForTables() {
		return fontForTables;
	}

	public static ImageIcon getDankort() {
		return DANKORT;
	}

	public static int getReloadPictures() {
		return reloadPictures;
	}

	public static ArrayList<ImageIcon> getIcons() {
		return icons;
	}
}
