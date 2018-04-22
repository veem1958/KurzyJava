
package projectMain;

import java.util.Comparator;

/**
 * Trieda zabezpe�uje spr�vne triedenie v�sledkov kurzu pod�a atrib�tu <b>suma</b> triedy Students,
 * parametrom je ��slo kurzu a objekty <b>Students</b>.</br>
 * Met�da "compare" triedy SortbySuma je modifik�ciou met�dy "compare" triedy java.util.Comparator.</br> 
 * Vytvoren� bol kon�truktor triedy s parametrom.  
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
