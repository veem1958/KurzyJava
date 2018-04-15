
package projectMain;

import java.util.Comparator;

/**
 * Trieda zabezpeèuje správne triedenie výsledkov kurzu pod¾a premennej <b>Suma</b>,
 * parametrom je èíslo kurzu a objekty <b>Students</b> 
 * @author Denisa Mensatorisová
 *
 */
public class SortbySuma implements Comparator<Students> {

	private int k;
	
	public SortbySuma(int s) { 
		this.k = s;
	}
		
	public int compare(Students a, Students b) {
		return (int) (a.getSuma(k) * 100 - b.getSuma(k) * 100);
	}
}
