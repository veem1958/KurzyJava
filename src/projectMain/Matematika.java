package projectMain;

import java.util.Random;

public class Matematika extends Subjects {
		
	double[] marksKurzy = new double[4];		//pole 4 double cisel = body pre kazdy kurz a kazdeho studenta
	double[] temy = new double []{ 1, 1, 1, 1, 2, 2, 2, 3, 3, 3 };
	private boolean maturita;
	
	
	//konstruktor pre studenta
	public Matematika(boolean matura) {
		setMaturita(matura);
	}
	
	//konstruktor pre ucitela
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
	
	
	//pridavanie bodov studentom
	public void addMarks(Students student, int kurz) {
		double sum;
		if (kurz == 1) {
			sum = (temy[0] + temy[1] + temy[2] + temy[3]);		 //* getMarks(Teachers lektor);	//???
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + sum)*100)/100;
		}
		
		if (kurz == 2) {
			sum = temy[4] + temy[5] + temy[6];
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + sum)*100)/100;
		}
		
		if (kurz == 3) {
			sum = temy[7] + temy[8] + temy[9];
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + sum)*100)/100;
		}
	
	}
	
	
	//nahodne pridavanie bodov podla aktivity
	public void randomMarks(Students student, int kurz) {
		this.marksKurzy[kurz] += (double) Math.round((int)(Math.random() * 2 + 1)*100)/100;
	}
	
}
