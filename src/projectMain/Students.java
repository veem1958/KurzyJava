package projectMain;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Students {
	
	protected Subjects mat;
	protected Subjects inf;
	
	
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
	public void addBody(int kurz) {
		mat.addMarks(this, kurz);
		inf.addMarks(this, kurz);
	}
	
	public void randomBody (int kurz) {
		mat.randomMarks(this, kurz);
	}
	

}
