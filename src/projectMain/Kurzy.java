package projectMain;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Exception;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4];
	public LinkedList<Students> studentivysl = new LinkedList();
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

		// pokus o nacitanie vysledkov ako objektu zo suboru 		
		try {
			nacitajObjekt();
		}
		catch (Exception e) {
			e.printStackTrace();
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
			
		// zotriedi podla celkoveho poctu bodov Suma = Mat+Inf	
		Collections.sort(studenti[kurz], Collections.reverseOrder(new SortbySuma()));
		//vypisStudent(kurz);  // konzola
		
		// zapise do suboru celkove vysledky cez serializaciu objektu
		if (kurz == 3) {
			try {
				zapisObjekt(kurz);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	// zapise vysledky kurzu studentov do suboru cez serializaciu objektu
	public void zapisObjekt(int kurz) throws FileNotFoundException, IOException {
		try {
			DateFormat formatData = new SimpleDateFormat("yyyyMMddHHmm");
			Date datum = new Date();
			String datumtxt = formatData.format(datum);
			String menosub = "Vysledok" + "_" + datumtxt + ".txt";

			System.out.println("nazov suboru bude : " + menosub);

			ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(menosub));
			s.writeObject(studenti[kurz]);
			s.flush();
			System.out.println("Výsledok 3.kurzu uložený do súboru - serializácia.");
			s.close();
			System.out.println("Súbor zatvorený.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// nacita vysledky kurzu studentov zo suboru do objektu cez serializaciu
	public void nacitajObjekt() throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
			studentivysl = new LinkedList<Students>();
			String menosub = "Vysledok_201804081100.txt";
			ObjectInputStream v = new ObjectInputStream(new FileInputStream(menosub));
			studentivysl = (LinkedList<Students>) v.readObject();
			System.out.println("Objekt naèítaný.");
			v.close();
			System.out.println("Poèet študentov v naèítanom objekte : " + studentivysl.size());
			
			
			int n = 0;
			Iterator<Students> iter = studentivysl.iterator();  
			
			while(iter.hasNext()) {
				System.out.print("Student[" + n + "]	");
				iter.next().vypisvysl();
				n++;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
