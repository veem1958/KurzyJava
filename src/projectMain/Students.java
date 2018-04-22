package projectMain;

import java.io.Serializable;
//import java.util.Observable;
//import java.util.Observer;
import java.util.Random;


/**
*Trieda Students zabezpeèuje hlavné metódy pre zápis, naèítanie a vıpis údajov o študentovi. 
*@author Denisa Mensatorisová  
*/
public class Students implements Passwords, Serializable {
	
	private static final long serialVersionUID = 1L;
	transient protected Subjects mat;
	transient protected Subjects inf;
	private double[] suma =  new double[4];		//suma bodov z mat aj info za kazdy kurz
	private String meno;
	String text = "";
	
	public Students(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	//prve pridelenie bodom studentom na zaklade vysledkov z maturity 
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	//vypis konzola
	public void vypis(int kurz) {
		String maturitaM;
		String maturitaI;
		
		if (mat.getMaturita(this) == true) 
			maturitaM = "ano";
		else maturitaM = "nie";
		
		if (inf.getMaturita(this) == true) 
			maturitaI = "ano";
		else maturitaI = "nie";
		
		System.out.println(this.getMeno() + "\t Mat: " + mat.getMarks(this, kurz) + "	Inf: " + inf.getMarks(this, kurz)
								+ "\t  Spolu: "	+ this.getSuma(kurz) + "	  Maturita: " + maturitaM + ", " + maturitaI);
	}
	

	public void vypisvysl() {
		System.out.println(this.getMeno() + "\t Body spolu: "	+ this.getSuma(3) );
	}
	

	/**
	 * Metóda sformátuje vybrané hlavné údaje (meno, celkové body) študenta do reazca vhodného pre vıpis v GUI,
	 * vyuíva sa pre zobrazenie starších celkovıch vısledkov uloenıch v súbore. 
	 * @return vracia sformátovanı reazec
	 */
	public String vypisvyslGUI() {
		
		String pommeno = this.getMeno();
		
		text = (String.format("%-16s", pommeno) + "\t Spolu:  "	+ String.format("%-6.2f", (this.getSuma(3))) + "\n");
		
		return this.text;
	}
	
	
		
	/**
	 * Metóda sformátuje údaje študenta do reazca vhodného pre vıpis v GUI.
	 * @param kurz - vıber údajov pod¾a poadovaného èísla kurzu
	 * @return  vracia sformátovanı reazec
	 */
	public String vypisGUI(int kurz) {
		
		String maturitaM;
		String maturitaI;
	
		if (mat.getMaturita(this) == true) 
			maturitaM = "ano";
		else maturitaM = "nie";
		
		if (inf.getMaturita(this) == true) 
			maturitaI = "ano";
		else maturitaI = "nie";
		
		String pommeno = this.getMeno();
						
		text = (String.format("%-16s", pommeno) + "\t Mat:  " + String.format("%-6.2f", (mat.getMarks(this, kurz)))
					+ "\t Inf:  " + String.format("%-6.2f", (inf.getMarks(this, kurz)))
					+ "\t Spolu:  "	+ String.format("%-6.2f", (this.getSuma(kurz)))
					+ "		Maturita:  " + maturitaM + ",  " + maturitaI + "\n");
		
		return this.text;
	}

	
	/**
	 * Metóda umoòuje vypoèíta a zapísa body študentom po jednotlivıch kurzoch 
	 * @param kurz - poradie kurzu
	 * @param bodyMat - hodnota bodov získanıch v danom kurze pre Matematiku
	 * @param bodyInf - hodnota bodov získanıch v danom kurze pre Informatiku
	 */
	//pridavanie bodov studentom po jednotlivych kurzoch
	public void addBody(int kurz, double bodyMat, double bodyInf) {
		mat.addMarks(this, kurz, bodyMat);
		inf.addMarks(this, kurz, bodyInf);
	}
	
	/**
	 * Metóda zapisuje celkové body získané v danom kurze ako súèet bodov z Matematiky a Informatiky.
	 * @param kurz - poradie kurzu
	 */
	//suma bodov mat + info 
	public void setSuma(int kurz) {
		this.suma[kurz] = (double) Math.round((mat.getMarks(this, kurz) + inf.getMarks(this, kurz))*100)/100;
	}
	
	
	/**
	 * Metóda naèíta uloenú hodnotu celkovıch bodov študenta za danı kurz.
	 * @param kurz - poradie kurzu 
	 * @return hodnotu celkovıch bodov študenta ako double
	 */
	public double getSuma(int kurz) {
		return this.suma[kurz];
	}
	
	
	/**
	 * Metóda pri prvotnom plnení údajov o študentoch vygeneruje pre kadého študenta meno a uloí ho.</br>
	 * Meno je generované v tvare "MenoStud_XX" , kde XX je poradové èíslo študenta.
	 * @param poradie - poradové èíslo študenta od 1 do 100
	 */
	// zapis meno studenta
	public void setMeno(int poradie) {
		this.meno = "MenoStud_" + poradie;
	}
	
	
	/**
	 * Metóda zisuje meno študenta.
	 * @return meno študenta
	 */
	public String getMeno() {
		return this.meno;
	}	
	
	
	//*********** nevyuíva sa ***************************
	//hesla
	
	private String password = "";
	
	public void setPassword() {
		
		String alphabet= "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		
		for (int i = 0; i < 8; i++) {
		    char c = alphabet.charAt(random.nextInt(26));
		    this.password += c;
		}
	}
	
	public String getPassword() {		
		return this.password;
	}

	
	
}
