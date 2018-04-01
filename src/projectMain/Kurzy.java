package projectMain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	LinkedList<Students>[] studenti = new LinkedList[4];
	
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();
	
	
	public void inicializacia() {
		
		for (int i = 0; i < 4; i++) {
			 studenti[i] = new LinkedList<Students>();
		}
		
		//inicializacia studentov bez maturity z matematiky a informatiky
		for (int i = 0; i < 40; i++) {
			studenti[0].add(student[i] = new Students(new Matematika(false), new Informatika(false)));
			student[i].naplnBody();
		}
		
		//inicializacia studentov s maturitov z matematiky a informatiky
		for (int i = 40; i < 100; i++) {
			studenti[0].add(student[i] = new Students(new Matematika(false), new Informatika(false)));
			student[i].naplnBody();		
		}
				
		//inicializacia ucitelov
		for (int i = 0; i < 5; i++) {
			lektori.add(lektor[i] = new Teachers(new Matematika(), new Informatika()));
			lektor[i].naplnBody();
		}
	}
	
	
	
	public void beh(int kurz) {
		
		int mat = (int) (Math.random() * 5 + 0);		//nahodny vyber lektora na dany kurz
		int inf = (int) (Math.random() * 5 + 0);
		double bodyMat = lektor[mat].mat.getMarks(lektor[mat]);
		double bodyInf = lektor[inf].inf.getMarks(lektor[inf]);
		System.out.println("Mat: ucitel: " + mat + " - " + bodyMat);
		System.out.println("Inf: ucitel: " + inf + " - " + bodyInf + "\n\n");
				
		
		for (int i = 0; i < 100; i++) {
			student[i].addBody(kurz, bodyMat, bodyInf);
		}	
	}
	
	
	
	public void vypisStudent(int kurz) {
		
		int n = 0;
		
		Iterator<Students> iter = studenti[0].iterator();  
		
		System.out.println(kurz + ".kurz: ");	
		while(iter.hasNext()) {
			System.out.print("Student[" + n + "]	");
			iter.next().vypis(kurz); 
			n++;
		}
	}
	
	public void vypisLektor() {
		
		int n = 0;
		
		Iterator<Teachers> iter = lektori.iterator();  
		
		while(iter.hasNext()) {
			System.out.print("Lektor[" + n + "]	");
			iter.next().vypis(); 
			n++;
		}
		
	}

}
