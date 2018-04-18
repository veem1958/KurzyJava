
package projectMain;

import java.util.Comparator;

/**
 * Trieda zabezpeèuje správne triedenie výsledkov kurzu pod¾a premennej <b>Suma</b>,
 * parametrom je èíslo kurzu a objekty <b>Students</b> 
 * @author Denisa Mensatorisová
 *
 */
public class SortbySuma implements Comparator<Students> {

	private int kurz;
	
	public SortbySuma(int s) { 
		this.kurz = s;
	}
		
	public int compare(Students a, Students b) {
		return (int) (a.getSuma(this.kurz) * 100 - b.getSuma(this.kurz) * 100);
	}
	
	
}
