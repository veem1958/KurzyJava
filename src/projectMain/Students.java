package projectMain;

import java.io.Serializable;
//import java.util.Observable;
//import java.util.Observer;
import java.util.Random;


/**
*Trieda Students zabezpe�uje hlavn� met�dy pre z�pis, na��tanie a v�pis �dajov o �tudentovi. 
*@author Denisa Mensatorisov�  
*/
public class Students implements Passwords, Serializable {
	
	/** Pridelenie pevn�ho intern�ho serial ��sla pre objekt typu Students. */
	private static final long serialVersionUID = 1L;
	transient protected Subjects mat;
	transient protected Subjects inf;
	/** Celkov� s��et bodov z Matematiky a Informatiky na za�iatku a po absolvovan� ka�d�ho z 3 kurzov.  */
	private double[] suma =  new double[4];		//suma bodov z mat aj info za kazdy kurz
	private String meno;
	String text = "";
	
	/** Kon�truktor triedy Students s agreg�ciou tried Matematika a Informatika */
	public Students(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	/** Met�da umo��uje prideli� prv� body �tudentom na z�klade v�sledkov zo strednej �koly a maturity z predmetov Matematika a Informatika. */ 
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	/** Met�da vyp�e na konzolu dosiahnut� v�sledky z Matematiky a Informatiky ako aj celkov� v�sledky po absolvovan� zadan�ho ��sla kurzu. */
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
	
	/** Met�da vyp�e na konzolu celkov� v�sledky �tudentov vo form�te meno �tudenta a celkov� body. */
	public void vypisvysl() {
		System.out.println(this.getMeno() + "\t Body spolu: "	+ this.getSuma(3) );
	}
	

	/**
	 * Met�da sform�tuje vybran� hlavn� �daje (meno, celkov� body) �tudenta do re�azca vhodn�ho pre v�pis v GUI,
	 * vyu��va sa pre zobrazenie star��ch celkov�ch v�sledkov ulo�en�ch v s�bore. 
	 * @return vracia sform�tovan� re�azec
	 */
	public String vypisvyslGUI() {
		
		String pommeno = this.getMeno();
		
		text = (String.format("%-16s", pommeno) + "\t Spolu:  "	+ String.format("%-6.2f", (this.getSuma(3))) + "\n");
		
		return this.text;
	}
	
			
	/**
	 * Met�da sform�tuje �daje �tudenta do re�azca vhodn�ho pre v�pis v GUI.
	 * @param kurz  v�ber �dajov pod�a po�adovan�ho ��sla kurzu
	 * @return  vracia sform�tovan� re�azec
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
	 * Met�da umo��uje vypo��ta� a zap�sa� body �tudentom po jednotliv�ch kurzoch. 
	 * @param kurz  poradie kurzu
	 * @param bodyMat  hodnota bodov z�skan�ch v danom kurze pre Matematiku
	 * @param bodyInf  hodnota bodov z�skan�ch v danom kurze pre Informatiku
	 */
	public void addBody(int kurz, double bodyMat, double bodyInf) {
		mat.addMarks(this, kurz, bodyMat);
		inf.addMarks(this, kurz, bodyInf);
	}
	
	/**
	 * Met�da zapisuje celkov� body z�skan� v danom kurze ako s��et bodov z Matematiky a Informatiky.
	 * @param kurz  poradie kurzu
	 */
	public void setSuma(int kurz) {
		this.suma[kurz] = (double) Math.round((mat.getMarks(this, kurz) + inf.getMarks(this, kurz))*100)/100;
	}
	
	
	/**
	 * Met�da na��ta ulo�en� hodnotu celkov�ch bodov �tudenta za dan� kurz.
	 * @param kurz  poradie kurzu 
	 * @return hodnotu celkov�ch bodov �tudenta ako double
	 */
	public double getSuma(int kurz) {
		return this.suma[kurz];
	}
	
	
	/**
	 * Met�da pri prvotnom plnen� �dajov o �tudentoch vygeneruje pre ka�d�ho �tudenta meno a ulo�� ho.</br>
	 * Meno je generovan� v tvare "MenoStud_XX" , kde XX je poradov� ��slo �tudenta.
	 * @param poradie  poradov� ��slo �tudenta od 1 do 100
	 */
	public void setMeno(int poradie) {
		this.meno = "MenoStud_" + poradie;
	}
	
	
	/**
	 * Met�da zis�uje meno �tudenta.
	 * @return meno �tudenta
	 */
	public String getMeno() {
		return this.meno;
	}	
	
	
	//*********** nevyu��va sa ***************************
	
	private String password = "";
	
	/** Met�da generuje n�hodne zo zadanej sady znakov heslo pre ka�d�ho �tudenta pri prvom naplnen� �dajmi.
	 * Vygenerovan� heslo malo by� doru�en� ka�d�mu �tudentovi a na z�klade toho sa potom mohol prihl�si� do aplik�cie.
	 * Vzh�adom na demon�tra�n� povahu programu t�to verzia sa obi�la t�m, �e v�etci �tudenti maj� 1 spolo�n� heslo a login pre prihl�senie.  
	 */
	public void setPassword() {
		
		String alphabet= "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		
		for (int i = 0; i < 8; i++) {
		    char c = alphabet.charAt(random.nextInt(26));
		    this.password += c;
		}
	}
	
	/** Met�da umo��uje zisti� heslo pre �tudenta. Nepou��va sa. Pre �tudentov je napevno v aplik�cii pridelen� 1 spolo�n� heslo aj login. */ 
	public String getPassword() {		
		return this.password;
	}

	
	
}
