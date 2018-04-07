package projectMain;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.Exception;

public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4];
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();
	
	
	public void inicializacia() throws Exception {
		
		for (int i = 0; i < 4; i++) {
			 studenti[i] = new LinkedList<Students>();
		}
		
		//inicializacia studentov bez maturity z matematiky a informatiky
		for (int i = 0; i < 40; i++) {
			student[i] = new Students(new Matematika(false), new Informatika(false));
			student[i].naplnBody();
			student[i].setSuma(0);
			student[i].setMeno(i+1);
			studenti[0].add(student[i]);
		}
		
		//inicializacia studentov s maturitov z matematiky a informatiky
		for (int i = 40; i < 100; i++) {
			student[i] = new Students(new Matematika(true), new Informatika(true));
			student[i].naplnBody();		
			student[i].setSuma(0);
			student[i].setMeno(i+1);
			studenti[0].add(student[i]);
		}
				
		//inicializacia ucitelov
		for (int i = 0; i < 5; i++) {
			lektor[i] = new Teachers(new Matematika(), new Informatika());
			lektor[i].naplnBody();
			lektori.add(lektor[i]);
		}
		
		//lektor[8].naplnBody();      // --- test na vynimku !!! index mimo rozsahu pola
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
			
		// zotriedi podla celkoveho poctu bodov Mat+Inf	
		Collections.sort(studenti[kurz], Collections.reverseOrder(new SortbySuma()));
		//vypisStudent(kurz);  // konzola
		
		if (kurz == 3) {
			try {
				zapisObjekt(kurz);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void zapisObjekt(int kurz) throws FileNotFoundException, IOException {
	
		File sub = new File("object.txt");
		sub.exists();
		
		ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream("object.txt"));
		s.writeObject(studenti[kurz]);
		s.flush();
		System.out.println("objekt 3.kurz zapisany");
		s.close();
		System.out.println("subor zatvoreny");
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
	
	//hesla
	public void hesla() {
		
		Iterator<Students> iter = studenti[0].iterator();  
		
		while(iter.hasNext()) {			
			iter.next().setPassword(); 
		}
	}
	
	public void vypisHesla() {
		
		int n = 0;
		Iterator<Students> iter = studenti[0].iterator();  
		
		while(iter.hasNext()) {
			System.out.print("Heslo[" + n + "]	"); 
			System.out.println(iter.next().getPassword());
			n++;
		}		
	}
	
}
