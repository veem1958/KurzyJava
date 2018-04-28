package projectMain;

/**
*Trieda Teachers zabezpeèuje hlavné metódy pre zápis a naèítanie údajov o lektorovi. 
*@author Denisa Mensatorisová  
*/
public class Teachers {
	
	/** mat je agregovaná trieda Matematika */
	protected Subjects mat;
	/** inf je agregovaná trieda Informatika */
	protected Subjects inf;
	
	/** Konštruktor triedy Teachers s agregáciou tried Matematika a Informatika */
	public Teachers(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	//body, ktore maju jednotlivy ucitelia podla schopnosti naucit 
	/** Metóda zapisuje body lektorom z predmetov Matematika a Informatika, ktoré vyjadrujú ich schopnos nauèi a vysvetli tému z daného predmetu */
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	/** Metóda vypisuje body lektorov na konzolu z predmetov Matematika a Informatika */
	public void vypis() {
		System.out.println("Mat: " + mat.getMarks(this) + "	Inf: " + inf.getMarks(this));
	}
	
	

}
