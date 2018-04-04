package projectMain;

public class Teachers {
	
	protected Subjects mat;
	protected Subjects inf;
	
	public Teachers(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	//body, ktore maju jednotlivy ucitelia podla schopnosti naucit 
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	//vypis konzola
	public void vypis() {
		System.out.println("Mat: " + mat.getMarks(this) + "	Inf: " + inf.getMarks(this));
	}
	
	

}
