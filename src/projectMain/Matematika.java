package projectMain;

/**
 * Trieda Matematika je podtriedou abstraktnej triedy Subjects. Ded� met�dy z nadtriedy Subjects.</br>
 * Zabezpe�uje z�pis a na��tanie �dajov �tudentov a lektorov z predmetu Matematika. Met�dy nap��aj� princ�p polymorfizmu.
 * @author Denisa Mensatorisov�
 */
public class Matematika extends Subjects {
		
	double[] marksKurzy = new double[4];		//pole 4 double cisel = body pre kazdy kurz a kazdeho studenta
	double[] temy = new double []{ 2, 4, 6 };
	private boolean maturita;
	
	
	/** Kon�truktor triedy Matematika pre �tudenta */
	public Matematika(boolean matura) {
		setMaturita(matura);
	}
	
	/** Kon�truktor triedy Matematika pre lektora */
	public Matematika() {	
	}
	
		
	public void setMaturita(boolean matura) {
		this.maturita = matura;
	}
	
	public boolean getMaturita(Students student) {
		return this.maturita;
	}
		
	
	public void setMarks(Students student) {
		
		if (this.maturita == false) {
			marksKurzy[0] = (double) Math.round((Math.random() * 3 + 0)*100)/100;
		}
		else {
			marksKurzy[0] = (double) Math.round((Math.random() * 2 + 3)*100)/100;
		}
	}
	
	public void setMarks(Teachers lektor) {
		marksKurzy[0] = (double) Math.round((Math.random() * 3 + 5)*100)/100;
	}
	
	
	public double getMarks(Students student, int kurz) {
		return (double) Math.round((this.marksKurzy[kurz])*100)/100;
	}
	
	public double getMarks(Teachers lektor) {
		return this.marksKurzy[0];
	}
	
	/**
	 * Met�da vypo��ta body (n�hodne sa generuj�) za aktu�lny kurz a pripo��ta ich k doteraz z�skan�m bodom.</br>
	 * Do v�po�tu vstupuje aj n�hodne generovan� hodnota premennej <i>"vnimanie"</i> v rozsahu od 0,7 do 1, ktor� vystihuje aktu�lnu schopnos� �tudenta s�stredi� sa.</br>
	 * Z�skan� body s� pren�soben� hodnotou premennej <i>"vnimanie"</i>. Met�da je pr�kladom dedenia a polymorfizmu.
	 * @param student - pole �tudentov
	 * @param kurz - poradie kurzu
	 * @param bodyMat - z�skan� nov� body z Matematiky
	 */
	public void addMarks(Students student, int kurz, double bodyMat) {
		double plus;
		double vnimanie = (double) Math.round((Math.random() * 0.4 + 0.7)*100)/100;
		
		if (kurz >= 1) {
			plus = (temy[kurz-1] + bodyMat) * vnimanie;		
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + plus)*100)/100;
			// (pocet bodov za danu temu + pocet bodov lektora z daneho predmetu) * schopnost studenta sustredit sa pocas daneho kurzu
		}

	}
	
		
}
