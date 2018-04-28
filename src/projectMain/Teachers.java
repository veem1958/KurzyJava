package projectMain;

/**
*Trieda Teachers zabezpe�uje hlavn� met�dy pre z�pis a na��tanie �dajov o lektorovi. 
*@author Denisa Mensatorisov�  
*/
public class Teachers {
	
	/** mat je agregovan� trieda Matematika */
	protected Subjects mat;
	/** inf je agregovan� trieda Informatika */
	protected Subjects inf;
	
	/** Kon�truktor triedy Teachers s agreg�ciou tried Matematika a Informatika */
	public Teachers(Subjects mat, Subjects info) {
		this.mat = mat;
		this.inf = info;
	}
	
	//body, ktore maju jednotlivy ucitelia podla schopnosti naucit 
	/** Met�da zapisuje body lektorom z predmetov Matematika a Informatika, ktor� vyjadruj� ich schopnos� nau�i� a vysvetli� t�mu z dan�ho predmetu */
	public void naplnBody() {
		mat.setMarks(this);
		inf.setMarks(this);
	}
	
	/** Met�da vypisuje body lektorov na konzolu z predmetov Matematika a Informatika */
	public void vypis() {
		System.out.println("Mat: " + mat.getMarks(this) + "	Inf: " + inf.getMarks(this));
	}
	
	

}
