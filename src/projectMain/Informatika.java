package projectMain;

/**
 * Trieda Informatika je podtriedou abstraktnej triedy Subjects. Dedí metódy z nadtriedy Subjects.</br>
 * Zabezpeèuje zápis a naèítanie údajov študentov a lektorov z predmetu Informatika. Metódy napåòajú princíp polymorfizmu.
 * @author Denisa Mensatorisová
 */
public class Informatika extends Subjects {

	/** Pole typu double pre uloenie hodnoty bodov študenta z daného predmetu na zaèiatku a pre kadı kurz. */
	double[] marksKurzy = new double[4];		
	/** Pole typu double obsahuje body za kadú tému z predmetu pre kadı kurz 1 a 3. */
	double[] temy = new double[]{ 2, 4, 6 };
	private boolean maturita;
	
	
	/** Konštruktor triedy Informatika pre študenta. */
	public Informatika(boolean matura) {
		setMaturita(matura);
	}
	
	/** Konštruktor triedy Informatika pre lektora. */
	public Informatika() {
	}
		
	
	/** 
	 * Metóda nastavuje atribút maturita študentovi na true ak maturoval z Informatiky inak je hodnota false. 
	 * Ak študent maturoval z daného predmetu získa pri prvom hodnotení za to viac bodov.	
	 * @param matura  hodnota true/false
	 */
	public void setMaturita(boolean matura) {
		this.maturita = matura;
	}
	
	/** Metóda zisuje hodnotu atribútu maturita pre študenta. */
	public boolean getMaturita(Students student) {
		return this.maturita;
	}
	
	/** Metóda generuje náhodne body pre študenta a danı predmet pri prvom plnení údajmi. */
	public void setMarks(Students student) {
		
		if (this.maturita == false) {
			marksKurzy[0] = (double) Math.round((Math.random() * 3 + 0)*100)/100;
		}
		else {
			marksKurzy[0] = (double) Math.round((Math.random() * 2 + 3)*100)/100;
		}
	}
	
	/** Metóda generuje náhodne body pre lektora a danı predmet pri prvom plnení údajmi. */
	public void setMarks(Teachers lektor) {
		this.marksKurzy[0] = (double) Math.round((Math.random() * 3 + 5)*100)/100;
	}
	
	
	/** Metóda zistí body pre študenta a danı predmet pre zadanı kurz.
	 * @param kurz  poradie kurzu 1 a 3.
	 */	
	public double getMarks(Students student, int kurz) {
		return (double) Math.round((this.marksKurzy[kurz])*100)/100;
	}
	
	/** Metóda zistí body pre lektora a danı predmet. */
	public double getMarks(Teachers lektor) {
		return this.marksKurzy[0];
	}
	
	
	/**
	 * Metóda vypoèíta body (náhodne sa generujú) za danı kurz a pripoèíta ich k doteraz získanım bodom pre predmet Informatika.</br>
	 * Do vıpoètu vstupuje aj náhodne generovaná hodnota premennej <i>"vnimanie"</i> v rozsahu od 0,7 do 1, ktorá vystihuje aktuálnu schopnos študenta sústredi sa.</br>
	 * Získané body sú prenásobené hodnotou premennej <i>"vnimanie"</i>. 
	 * Metóda je príkladom dedenia a polymorfizmu. Dedí sa z nadradenej triedy s rovnakım názvom ale je rôzna pre Matematiku a Informatiku.   
	 * @param student  pole študentov
	 * @param kurz  poradie kurzu
	 * @param bodyInf  získané nové body z Informatiky
	 */
	public void addMarks(Students student, int kurz, double bodyInf) {
		double plus;
		double vnimanie = (double) Math.round((Math.random() * 0.3 + 0.7)*100)/100;
		
		if (kurz >= 1) {
			plus = (temy[kurz-1] + bodyInf) * vnimanie;
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + plus)*100)/100;
		}
	}
			
}
