package projectMain;

public abstract class Subjects {
	
	public Subjects() {
		
	}
	
	public abstract void setMarks(Students student);
	public abstract void setMarks(Teachers lektor);
	
	public abstract double getMarks(Students student, int kurz);
	public abstract double getMarks(Teachers lektor);
		
	public abstract boolean getMaturita(Students student);
	
	//
	public abstract void addMarks(Students student, int kurz);
	public abstract void randomMarks(Students student, int kurz);
	
	

}
