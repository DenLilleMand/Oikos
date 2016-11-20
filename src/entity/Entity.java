package entity;
/**
 * Denne her klasse burde v�re et interface
 * vi bruger denne type lige nu til den generic metode 
 * ovre i vores utilKontrol, men der er ingen grund til at subclasse
 * LogEntry og Customer pga. dette, kan bare bruge generics til
 * det yderste og implementere et interface.
 *
 */

public interface Entity {
		
	public int getId();
	
	public void setId(int id);

}
