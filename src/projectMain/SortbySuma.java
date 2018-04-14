
package projectMain;

import java.util.Comparator;

public class SortbySuma implements Comparator<Students> {

	private int k;
	
	public SortbySuma(int s) { 
		this.k = s;
	}
		
	// Used for sorting in ascending order of Suma
	public int compare(Students a, Students b) {
		return (int) (a.getSuma(k) * 100 - b.getSuma(k) * 100);
	}
}
