package projectMain;

import java.io.Serializable;
import java.util.Observable;

import java.util.Observer;
import java.util.Random;

public class Students implements Passwords, Serializable {
	
	transient protected Subjects mat;
	transient protected Subjects inf;
	private double suma;		//suma bodov z mat aj info za kazdy kurz
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
	
		System.out.println(this.getMeno() + "\t Body spolu: "	+ this.getSuma(0) );
	}
	
	
	
	//vypis GUI
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

	
	
	//pridavanie bodov studentom po jednotlivych kurzoch
	public void addBody(int kurz, double bodyMat, double bodyInf) {
		mat.addMarks(this, kurz, bodyMat);
		inf.addMarks(this, kurz, bodyInf);
	}
	
	//suma bodov mat + info 
	public void setSuma(int kurz) {
		this.suma = (double) Math.round((mat.getMarks(this, kurz) + inf.getMarks(this, kurz))*100)/100;
	}
	
	public double getSuma(int kurz) {
		return this.suma;
	}
	
	// zapis meno studenta
	public void setMeno(int poradie) {
		this.meno = "MenoStud_" + poradie;
	}
	
	public String getMeno() {
		return this.meno;
	}	
	
	
	//**************************************
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
