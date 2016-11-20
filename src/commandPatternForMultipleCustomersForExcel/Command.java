package commandPatternForMultipleCustomersForExcel;

import java.util.ArrayList;

import entity.Customer;

/**
 * Command interface, indeholder execute(), undo(),
 * det betyder at vi kan give noget client kode et objekt af typen Command,
 * og den behøver ikke at kende til den specifikke implementation af disse 2 metoder,
 * det eneste den har brug for er et objekt den kan kalde metoderne på. 
 * 
 * @author DenLilleMand
 */
public interface Command 
{
	public void undo(ArrayList<Customer> list);
	public void execute(ArrayList<Customer> list, ArrayList<Customer> list1);
}
