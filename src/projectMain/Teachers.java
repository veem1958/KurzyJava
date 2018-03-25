package projectMain;

public class Teachers {
	
	protected Subjects mat;
	protected Subjects inf;
	
	public Teachers(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	public void vypis() {
		System.out.println("Mat: " + mat.getMarks(this) + "	Inf: " + inf.getMarks(this));
	}
	
	

}
