package projectMain;

import java.util.Observable;

import java.util.Observer;
import java.util.Random;

public class Students implements Passwords {
	
	protected Subjects mat;
	protected Subjects inf;
	protected double suma;		//suma 
	
	
	public Students(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	public void vypis(int kurz) {
		System.out.println("Mat: " + mat.getMarks(this, kurz) + "	Inf: " + inf.getMarks(this, kurz)
								+ "	Maturita: " + mat.getMaturita(this) + ", " + inf.getMaturita(this));
	}

	//pridavanie bodov studentom po jednotlivych kurzoch
	public void addBody(int kurz, double bodyMat, double bodyInf) {
		mat.addMarks(this, kurz, bodyMat);
		inf.addMarks(this, kurz, bodyInf);
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
