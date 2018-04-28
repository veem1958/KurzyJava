package projectMain;

/**
 * Trieda Matematika je podtriedou abstraktnej triedy Subjects. Dedí metódy z nadtriedy Subjects.</br>
 * Zabezpeèuje zápis a naèítanie údajov študentov a lektorov z predmetu Matematika. Metódy napåòajú princíp polymorfizmu.
 * @author Denisa Mensatorisová
 */
public class Matematika extends Subjects {
		
	double[] marksKurzy = new double[4];		//pole 4 double cisel = body pre kazdy kurz a kazdeho studenta
	double[] temy = new double []{ 2, 4, 6 };
	private boolean maturita;
	
	
	/** Konštruktor triedy Matematika pre študenta */
	public Matematika(boolean matura) {
		setMaturita(matura);
	}
	
	/** Konštruktor triedy Matematika pre lektora */
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
	 * Metóda vypoèíta body (náhodne sa generujú) za aktuálny kurz a pripoèíta ich k doteraz získanım bodom.</br>
	 * Do vıpoètu vstupuje aj náhodne generovaná hodnota premennej <i>"vnimanie"</i> v rozsahu od 0,7 do 1, ktorá vystihuje aktuálnu schopnos študenta sústredi sa.</br>
	 * Získané body sú prenásobené hodnotou premennej <i>"vnimanie"</i>. Metóda je príkladom dedenia a polymorfizmu.
	 * @param student - pole študentov
	 * @param kurz - poradie kurzu
	 * @param bodyMat - získané nové body z Matematiky
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
