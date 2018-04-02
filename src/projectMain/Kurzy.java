package projectMain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4];
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();
	
	
	public void inicializacia() {
		
		for (int i = 0; i < 4; i++) {
			 studenti[i] = new LinkedList<Students>();
		}
		
		//inicializacia studentov bez maturity z matematiky a informatiky
		for (int i = 0; i < 40; i++) {
			student[i] = new Students(new Matematika(false), new Informatika(false));
			student[i].naplnBody();
			student[i].setSuma(0);
			studenti[0].add(student[i]);
		}
		
		//inicializacia studentov s maturitov z matematiky a informatiky
		for (int i = 40; i < 100; i++) {
			student[i] = new Students(new Matematika(true), new Informatika(true));
			student[i].naplnBody();		
			student[i].setSuma(0);
			studenti[0].add(student[i]);
		}
				
		//inicializacia ucitelov
		for (int i = 0; i < 5; i++) {
			lektor[i] = new Teachers(new Matematika(), new Informatika());
			lektor[i].naplnBody();
			lektori.add(lektor[i]);
		}
	}
	
	
	
	public void beh(int kurz) {
		
		int mat = (int) (Math.random() * 5 + 0);		//nahodny vyber lektora na dany kurz
		int inf = (int) (Math.random() * 5 + 0);
		double bodyMat = lektor[mat].mat.getMarks(lektor[mat]);	//zistenie bodov lektora z daneho predmetu
		double bodyInf = lektor[inf].inf.getMarks(lektor[inf]);
		System.out.println("Mat: ucitel: " + mat + " - " + bodyMat);
		System.out.println("Inf: ucitel: " + inf + " - " + bodyInf + "\n\n");
				
		
		for (int i = 0; i < 100; i++) {
			student[i].addBody(kurz, bodyMat, bodyInf);
			student[i].setSuma(kurz);
			studenti[kurz].add(student[i]);
		}	
				
	}
	
	
	//vypis konzola
	public void vypisStudent(int kurz) {
		
			int n = 0;
			
			Iterator<Students> iter = studenti[kurz].iterator();  
			
			System.out.println(kurz + ".kurz: ");	
			while(iter.hasNext()) {
				System.out.print("Student[" + n + "]	");
				iter.next().vypis(kurz);
				n++;
			}
	}
	
	
	//vypis konzola
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
