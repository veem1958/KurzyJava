package projectMain;

/**
 * Trieda definuje spolo�n� met�dy pre predmety kurzu Matematika a Informatika pr�padne �al�ie predmety. 
 * Triedu vyu��vaj� triedy Students a Teachers.
 * @author Denisa Mensatorisov�
 *
 */
public abstract class Subjects {
	
	public Subjects() {
		
	}
	
	public abstract void setMarks(Students student);
	public abstract void setMarks(Teachers lektor);
	
	public abstract double getMarks(Students student, int kurz);
	public abstract double getMarks(Teachers lektor);
		
	public abstract boolean getMaturita(Students student);
	
	public abstract void addMarks(Students student, int kurz, double body);
		

}
