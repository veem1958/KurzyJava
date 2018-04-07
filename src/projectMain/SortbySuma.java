
package projectMain;

import java.util.Comparator;

public class SortbySuma implements Comparator<Students> {

	// Used for sorting in ascending order of Suma

	public int compare(Students a, Students b) {
		return (int) (a.getSuma(3) * 100 - b.getSuma(3) * 100);
	}
}
