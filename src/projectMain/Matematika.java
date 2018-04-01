package projectMain;

import java.util.Random;

public class Matematika extends Subjects {
		
	double[] marksKurzy = new double[4];		//pole 4 double cisel = body pre kazdy kurz a kazdeho studenta
	double[] temy = new double []{ 2, 4, 6 };
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
	public void addMarks(Students student, int kurz, double bodyMat) {
		double plus;
		double vnimanie = (double) Math.round((Math.random() * 0.4 + 0.7)*100)/100;
		
		if (kurz == 1) {
			plus = (temy[0] + bodyMat) * vnimanie;		// (pocet bodov za danu temu + pocet bodov lektora z daneho predmetu) * schopnost studenta sustredit sa pocas daneho kurzu
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + plus)*100)/100;
		}
		
		if (kurz == 2) {
			plus = (temy[1] + bodyMat) * vnimanie;
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + plus)*100)/100;
		}
		
		if (kurz == 3) {
			plus = (temy[2] + bodyMat) * vnimanie;
			this.marksKurzy[kurz] = (double) Math.round((marksKurzy[kurz-1] + plus)*100)/100;
		}
	}
	
		
}
