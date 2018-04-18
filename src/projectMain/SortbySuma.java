
package projectMain;

import java.util.Comparator;

/**
 * Trieda zabezpe�uje spr�vne triedenie v�sledkov kurzu pod�a premennej <b>Suma</b>,
 * parametrom je ��slo kurzu a objekty <b>Students</b> 
 * @author Denisa Mensatorisov�
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
