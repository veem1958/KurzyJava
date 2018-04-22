package projectMain;

/**
 * Trieda definuje spoloèné metódy pre predmety kurzu Matematika a Informatika prípadne ïalšie predmety. 
 * Triedu využívajú triedy Students a Teachers.
 * @author Denisa Mensatorisová
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
