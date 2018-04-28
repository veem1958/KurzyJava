package projectMain;

/**
 * Trieda Informatika je podtriedou abstraktnej triedy Subjects. Ded� met�dy z nadtriedy Subjects.</br>
 * Zabezpe�uje z�pis a na��tanie �dajov �tudentov a lektorov z predmetu Informatika. Met�dy nap��aj� princ�p polymorfizmu.
 * @author Denisa Mensatorisov�
 */
public class Informatika extends Subjects {

	double[] marksKurzy = new double[4];		//pole 4 double cisel = body pre kazdy kurz a kazdeho studenta a ucitela
	double[] temy = new double[]{ 2, 4, 6 };
	private boolean maturita;
	
	
	//konstruktor pre studenta
	public Informatika(boolean matura) {
		setMaturita(matura);
	}
	
	//konstruktor pre ucitela
	public Informatika() {
	}
		
	
	public void setMaturita(boolean matura) {
		this.maturita = matura;
	}
	
	public boolean getMaturita(Students student) {
		return this.maturita;
	}
	
	//nastavenie pociatocnych bodov studentom pred kurzom
	public void setMarks(Students student) {
		
		if (this.maturita == false) {
			marksKurzy[0] = (double) Math.round((Math.random() * 3 + 0)*100)/100;
		}
		else {
			marksKurzy[0] = (double) Math.round((Math.random() * 2 + 3)*100)/100;
		}
	}
	
	//nastavenie bodov ucitelom
	public void setMarks(Teachers lektor) {
		this.marksKurzy[0] = (double) Math.round((Math.random() * 3 + 5)*100)/100;
	}
	
	
	public double getMarks(Students student, int kurz) {
		return (double) Math.round((this.marksKurzy[kurz])*100)/100;
	}
	
	public double getMarks(Teachers lektor) {
		return this.marksKurzy[0];
	}
	
	
	/**
	 * Met�da vypo��ta body (n�hodne sa generuj�) za aktu�lny kurz a pripo��ta ich k doteraz z�skan�m bodom pre predmet Informatika.</br>
	 * Do v�po�tu vstupuje aj n�hodne generovan� hodnota premennej <i>"vnimanie"</i> v rozsahu od 0,7 do 1, ktor� vystihuje aktu�lnu schopnos� �tudenta s�stredi� sa.</br>
	 * Z�skan� body s� pren�soben� hodnotou premennej <i>"vnimanie"</i>. 
	 * Met�da je pr�kladom dedenia a polymorfizmu. Ded� sa z nadradenej triedy s rovnak�m n�zvom ale je r�zna pre Matematiku a Informatiku.   
	 * @param student - pole �tudentov
	 * @param kurz - poradie kurzu
	 * @param bodyInf - z�skan� nov� body z Informatiky
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
